package com.secret.santa;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    
    public List<Assignation> performOne(long seed) {
        App app = new App();

        boolean reload = true;
        int i = 0;
        while ( reload && i < 10000) {
            try {
                i++;
                app = new App();
                app.init(seed);
                reload = false;
            } catch ( IllegalArgumentException e) {
                reload = true;
            }
        }
        if (i >= 10000) {
            return null;
        }
        return app.assignations;
    }

    
    public void testPrintRepartition()
    {
        
        Map<String, Map<String, Integer>> repartitions = new HashMap<>();
        int success = 0;
        int planned = 5000;
        for (int i = 0; i < planned; i++) {
            
            List<Assignation> getOneResults = performOne(i*2901);
            
            if (getOneResults == null) {
                continue;
            }
            
            success++;
            
            for (Assignation element: getOneResults) {
                if (repartitions.get(element.name_buyer) != null) {
                    Map<String, Integer> current = repartitions.get(element.name_buyer);
                    if (current.get(element.name_receiver) == null) {
                        current.put(element.name_receiver, 1);
                    } else {
                        current.put(element.name_receiver, current.get(element.name_receiver) + 1);
                    }
                }
                else {
                    Map<String, Integer> oneRepartition = new HashMap<>();
                    oneRepartition.put(element.name_receiver, 1);
                    repartitions.put(element.name_buyer, oneRepartition);
                }
            }
        }

        
        System.out.println("success: " + success + "/" + planned);

        // print headers
        String header = "BUYER\t";
        for (String buyer : repartitions.keySet()) {
            header += buyer + "\t";
        }
        System.out.println(header);
        
        
        for (String buyer : repartitions.keySet()) {
            String percents = buyer;
            for (String receiver : repartitions.keySet()) {
                percents += "\t";
                if (repartitions.get(buyer).get(receiver) != null) {
                    percents += "" + (repartitions.get(buyer).get(receiver) * 100 / success) + "%";
                }
                else {
                    percents += " 0%";
                }
            }
            System.out.println(percents);
        }
        
    }
}

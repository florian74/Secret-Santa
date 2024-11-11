package com.secret.santa;

import java.util.*;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class App 
{
	
	
	public List<Assignation> assignations = new ArrayList<Assignation>();
	public String budget;
	public String from;
	
    public static void main( String[] args )
    {
        
    	App app = null;
    	
    	
    	//Do Matching
        boolean reload = true;
        int i = 0;
        while ( reload && i < 10000) {
	        try {
	        	i++;
	        	app = new App();
	        	app.init(new Date().getTime());
	        	reload = false;
	        } catch ( IllegalArgumentException e) {
	        	reload = true;
	        	e.printStackTrace();
	        	System.out.println(i);
	        } 
        }
        
        if ( i < 10000 ) {
        	
	        //Send All mail
	        for ( Assignation asign : app.assignations) {
	        	//for debug
        		//System.out.println("buyer: " + asign.name_buyer + " , receiver: " + asign.name_receiver);


	        	try {
					asign.sendTo(app.budget, app.from);
	        	} catch (SendFailedException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	
        	
        }
        else {
        	System.out.println("system does not have solution");
        }
        
    }
    


    public void init(long seed) {
    	
    	
    	//GMail sender config
    	Mail.GMAIL_ADRESS = "my-gmail-sender@gmail.com";
    	Mail.GMAIL_PWD = "my-gmail-secure-token";

    	from = Mail.GMAIL_ADRESS;

    	budget = "50 Euros";
    	
    	//People definition
    	String Roger="Roger";
    	String Rafael="Rafael";
    	String Novak="Novak";
		String Richard="Richard";
		String Gael="Gael";
    	
    	//Rules
    	assignations.add(  new Assignation(Roger, "roger.federer.1248995@gmail.com")
    											.except(Rafael));
    	assignations.add(  new Assignation(Rafael, "rafael.nadal.144875556@gmail.com"));
    	assignations.add(  new Assignation(Novak, "novak-giocovic-123@sfr.fr")
    											.except(Rafael));
    	assignations.add(  new Assignation(Richard, "richard.G.4567@gmail.com")
    											.except(Roger)
    											.except(Gael));
		assignations.add(  new Assignation(Gael, "gael.MFS67@gmail.com"));
		
		assignations.sort((a, b) -> b.exceptions.size() - a.exceptions.size() );
		
    	//init
    	List<String> destinataires = new ArrayList<String>();
    	for ( Assignation asign : assignations) {
    		destinataires.add(asign.name_buyer);
    	}

		//random number generator
		Random r = new Random(seed);
    	
    	//assign
    	for ( Assignation assign : assignations) {
    		
    		for ( int i=0 ; i < assignations.size() ; i++) {
    			
    			assign.assign(destinataires, r);
    			
    			destinataires.remove(assign.name_receiver);
    			
    		}
    		
    	}
    	
    	
    }
}

# Secret-Santa
Secret Santa with constraint - Complete init function code and run

#build

this is built under java 15 using Maven
Please use an ide to run the App java main class with your config.

#configure

In the App file look for the init function the configure the script

###Mail configuration

Fill the two fields below
```
//GMail sender config
Mail.GMAIL_ADRESS = "my-gmail-sender@gmail.com";
Mail.GMAIL_PWD = "my-gmail-secure-token";
```
I recomend to use a gmail account with an app password
https://www.getmailbird.com/gmail-app-password/


### People configuration

Follow the logic, adda variable per person with their name.
Then add the rules in the list "assignations".

````
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

````

When you configure the assignation object you can add the exceptions rules
In this exemple:
- Roger cannot buy a gift to Rafael
- Rafael can potentially buy a gift to anyone
- Novak cannot buy a gift to Rafael
- Richard cannot buy a gift to Roger and Gael
- Rafael can potentially buy a gift to anyone


### Budget configuration
In the init function look for budget variable
and set the value and currency.
````
budget = "50 Euros";
````


### Algorithm Logic: Brute force
The system will assign randomly a solution, test it and retry if not possible.
It does it up to 10000 times and return the solution.


### Text
Mail text is in Assignation class in SendTo function
Basic text is in English, but you can change it
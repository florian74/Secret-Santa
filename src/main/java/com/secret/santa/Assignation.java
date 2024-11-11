package com.secret.santa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;


//Link between the present buyer and the present receiver
public class Assignation {

	
	//person who will receive the present
	public String name_receiver = null;
	
	//person who will buy the present
	public String name_buyer = "";
	
	//email of the person who will buy
	public String email = "";
	
	//The Person who will buy cannot be assigned to the people listed in this property
	public List<String> exceptions = new ArrayList<String>();
	
	public Assignation(String name, String mail ) {
		this.name_buyer = name;
		this.name_receiver = null;
		this.email = mail;
		this.exceptions.add(name_buyer);
	}
	
	public Assignation except(String name) {
		exceptions.add(name);
		return this;
	}
	
	
	public Assignation assign(List<String> remainingAssignation, Random r ) {
		
		Assignation result = this;
		
		// continue until we find someone
		while ( result.name_receiver == null && remainingAssignation.size() > 0) {
			
			if ( 
				//test if the person can be assign to someone that is not himself or people he doesn't want
				 ! checkArgument(remainingAssignation, exceptions)
				) {
				throw new IllegalArgumentException();
			}
			
			//asign to a random person
			int index = r.nextInt(remainingAssignation.size());
			this.name_receiver = remainingAssignation.get(index);
			
			
			//test if we match a valid person, else reset receiver name to continue the loop
			if ( result.name_receiver != null && exceptions.contains(result.name_receiver)) {
				result.name_receiver = null;
			}
			
			
		}
		return result;
		
	}
	
	
	// send information to the person
	public void sendTo(String budget, String from) throws SendFailedException, MessagingException {

		String dest = this.email;

		// Build Destination Mail
		List<String> bcc = new ArrayList<String>();
		bcc.add(dest);

		// Build Title
		String title = "Assignation"  ;

		// Build Body
		StringBuilder body = new StringBuilder("Hello " + name_buyer +"<br>" );
		body.append("<br>The person to whom you will buy a present is : " +  name_receiver + "<br>");
		body.append("<br>Target budget is: " + budget + ".<br>");
		body.append("<br>Bye,<br>Your Beloved Santa Claus<br>");

		Mail mail = new Mail();

		mail.setFrom(from);
		mail.setCc(bcc.toArray(new String[bcc.size()]));
		mail.setTitle(title);
		mail.setBody(body.toString());

		//System.out.println(mail.toString());
		mail.send();
		
		
	}
	
	
	public Boolean checkArgument( List<String> possibleAsignations, List<String> exceptions) {
		
		for ( String possibleAsignation : possibleAsignations) {
			if ( ! exceptions.contains(possibleAsignation)) {
				return true;
			}
		}
		
		return false;
	}
	
}

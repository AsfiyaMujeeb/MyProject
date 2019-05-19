package com.db.messageBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class MessageBean {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageBean.class);
	
	private static final List<String> nameList = new ArrayList<String>(Arrays.asList("Mark Imaginary","Govind Real","Shakil Maybe","Chang Imagine"));
	private static final String restrictedCountryCode = "AT";
	private static final String restrictedServiceCode = "ATZ";
	private static String restrictedMessage = "Sharpnel metal cutting Grade C";
	
	@Autowired
	private MessageConfirmation msg;
	
	@Autowired
	public RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
	   return builder.build();
	}
	
	@Handler
	public void processMessage(Exchange exchange){
		
		 logger.info("\n Received the message from activeMQ \n");
		
		  String body = exchange.getIn().getBody(String.class);
		   // change the message to say Hello
		  
		  String refLine = "";
		  String name = "";
		  String sourceCountryCode = "";
		  String servicCode = "";
		  String strInMessage = "";
		  String strTRN = "";
		  String finalMessage = "";
		  int lineNum = 0;
		  
		  
		try {  
			Scanner scanner = new Scanner(body);
			
		  while(scanner.hasNextLine()) {
			  
			 lineNum++;
			 refLine = scanner.nextLine();
			 
			 if(lineNum==1 && refLine.length()<72) {
				 strTRN = refLine.substring(1, 22);
			 	 name = refLine.substring(23, 43).trim();
				 sourceCountryCode = refLine.substring(69, 71);
				 servicCode = refLine.substring(66, 69);
				 
			 }else if (lineNum==1 && refLine.length()>=72) {
				 throw new StringIndexOutOfBoundsException();
			 }
			 else if(lineNum==3) {
				 
				 strInMessage = refLine.trim();
				 
				 //System.out.println("Message in 32A...***********"+strInMessage);
			 }
			/* System.out.println("Name..."+name+
					 "..Country Code.."+sourceCountryCode+
					 "...service code.."+servicCode+
					 "..:32A:.."+strInMessage+
					 "TRN..:"+strTRN);*/
			 
		  }
		  
		  if(scanner != null) scanner.close();
		  
		
		// 

		logger.info("\n ######### Required checks on the Message started ########## \n");
		
		if(checkNames(name) && checkCountryCode(sourceCountryCode) && checkServiceCode(servicCode) && checkMessage(strInMessage)) {
			  finalMessage = "Suspicious shipment;"+sourceCountryCode+";"+servicCode+";"+strTRN;
			  
		  }else {
			  finalMessage = "Nothing found, all Okay !!";
		  }
		
		logger.info("\n Results of the checks : \n"+finalMessage);
		  
		msg.setMessageDetails(finalMessage);
		msg.setServiceCode(servicCode);
		msg.setSourceCountry(sourceCountryCode);
		msg.setTrnNum(strTRN);
		logger.info("\n Message built after checking \n"+msg.toString());
		logger.info("\n Invoking the Rest service \n");
		MessageConfirmation recMsg = restTemplate.postForObject("http://localhost:8080/message", msg, MessageConfirmation.class);
		  
		logger.info("\n ############################### Response received from REST service ################################### \n \t\t"+recMsg.getMessageDetails());  
		}catch (StringIndexOutOfBoundsException e) {
			logger.error("\n Message Format not correct \n");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		   exchange.getOut().setBody(body);
	}
	
	
	
	
	
	
	private boolean checkNames(String name) {
		
		for (String strName : nameList) {
			if(strName.equalsIgnoreCase(name)) return true;
			
		}
		
		return false;
	}
	
	
	private boolean checkCountryCode(String strCountryCode) {
		
			if(restrictedCountryCode.equalsIgnoreCase(strCountryCode)) return true;
			else return false;
	}
	
	private boolean checkServiceCode(String strServiceCode) {
		
		if(restrictedServiceCode.equalsIgnoreCase(strServiceCode)) return true;
		else return false;
	}
	
	private boolean checkMessage(String strMessage) {
		
		restrictedMessage = restrictedMessage.toLowerCase();
		int index = strMessage.toLowerCase().indexOf("sharpnel");
		strMessage = strMessage.substring(index, index+30);
		
		if(strMessage.equalsIgnoreCase(restrictedMessage)) return true;
		else return false;
	}

}

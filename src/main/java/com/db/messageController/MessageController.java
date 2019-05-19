package com.db.messageController;

import java.io.IOException;
import java.util.logging.FileHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.messageBean.MessageConfirmation;

@RestController
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	
	
	@RequestMapping(value ="/message", consumes="application/json")
	public MessageConfirmation getMessageInformation(@RequestBody MessageConfirmation msg) throws SecurityException, IOException {
		logger.info("\n***********************************************************************\n"
				+ "\n*******************REST Service logging begins **************************\n" +
				"***********************************************************************\n"
				+"\n         Incoming request received by REST        \n" +msg.toString());
		
		msg.setMessageDetails("Got it.. Thanks!!");
		
		logger.info("\n Response sent back from REST services \n" +
				"\n***********************************************************************\n"+
				"\n********************REST Service logging ends****************************\n" +
				"\n***********************************************************************\n");
	   return msg; 
	}
	
	
}

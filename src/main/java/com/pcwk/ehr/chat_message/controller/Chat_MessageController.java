package com.pcwk.ehr.chat_message.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller
public class Chat_MessageController {

	final Logger log = LogManager.getLogger(getClass());

	public Chat_MessageController() {
		super();
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│    **product_BoardService() 생성**     │");
		log.debug("└───────────────────────────────────────┘");
	}


}

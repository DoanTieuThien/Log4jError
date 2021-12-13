package com.its.ldap.server.main;

import com.its.ldap.server.manager.LDAPServer;

/**
 * @author ITShare
 *
 */
public class LDAPServerManager {
	public static void main(String[] args) {
		LDAPServer ldapServer = new LDAPServer();

		try {
			System.out.println("starting listen on 8888 port");
			ldapServer.startServer(8888);
			System.out.println("sussecced listen on 8888 port");
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}

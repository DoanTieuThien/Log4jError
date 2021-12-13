package com.its.ldap.server.manager;

import java.net.InetAddress;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;

public class LDAPServer {
	private static final String LDAP_BASE = "dc=example,dc=com";
	private InMemoryDirectoryServer server = null;

	public void startServer(int port) throws Exception {
		InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
		config.setListenerConfigs(new InMemoryListenerConfig("listen", InetAddress.getByName("0.0.0.0"), port,
				ServerSocketFactory.getDefault(), SocketFactory.getDefault(),
				(SSLSocketFactory) SSLSocketFactory.getDefault()));
		config.addInMemoryOperationInterceptor(new OperationInterceptor());
		this.server = new InMemoryDirectoryServer(config);
		server.startListening();
	}

	public void stopServer() {
		if (this.server == null)
			return;

		try {
			this.server.shutDown(true);
		} catch (Exception exp) {
		}
		this.server = null;
	}
}

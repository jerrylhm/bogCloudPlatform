package com.ilongli.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

public class MySessionDAO extends EnterpriseCacheSessionDAO {

	@Override
	protected Serializable doCreate(Session session) {
		
		Serializable sessionId = super.doCreate(session);
		
		System.out.println("doCreate : " + sessionId);
		
		return sessionId;
	}
	
}

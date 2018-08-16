package com.ilongli.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

public class MySessionDAO extends EnterpriseCacheSessionDAO {

	@Override
	protected Serializable doCreate(Session session) {
		System.out.println("doCreate!");
		return super.doCreate(session);
	}
	
	@Override
	protected void doDelete(Session session) {
		System.out.println("doDelete!");
		super.doDelete(session);
	}
	
	@Override
	protected void doUpdate(Session session) {
		System.out.println("doUpdate!");
		super.doUpdate(session);
	}
	
	@Override
	protected Session doReadSession(Serializable sessionId) {
		System.out.println("doReadSession!");
		return super.doReadSession(sessionId);
	}
	
	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
		// TODO Auto-generated method stub
		return super.readSession(sessionId);
	}
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		// TODO Auto-generated method stub
		
		super.update(session);
	}
}

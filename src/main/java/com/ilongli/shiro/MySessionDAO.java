package com.ilongli.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import com.ilongli.utils.RedisUtil;

/**
 * 自定义sessionDao，实现session的CRUD
 * 存储在redis中，实现集群的session共享
 * @author ilongli
 * 参考：https://blog.csdn.net/zcyhappy1314/article/details/71270937
 */
public class MySessionDAO extends AbstractSessionDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(MySessionDAO.class);
	
    /** 
     * 在Redis保存的key值的前缀 
     */  
    private static final String KEY_PREFIX = "shiro_redis_session:"; 
    
    /**
     * session过期时间(30分钟)
     */
    private static final long EXPIRE_TIME = 1800000L;
	
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}

	@Override
	public void delete(Session session) {
        if (session == null || session.getId() == null) {
        	LOGGER.error("session or session id is null");
            return;
        }
        RedisUtil.delete(KEY_PREFIX+session.getId());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();
		Set<String> keys = RedisUtil.keys(KEY_PREFIX + "*");
		if(keys != null && keys.size() > 0) {
			for(String key : keys) {
				Session s = (Session) RedisUtil.valueGet(key);
				sessions.add(s);
			}
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		System.out.println("doCreate the session: " + sessionId);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){  
            LOGGER.error("session id is null");  
            return null;  
        } 
		return (Session) RedisUtil.valueGet(KEY_PREFIX + sessionId);
	}
	
	/**
	 * 保存session
	 */
	private void saveSession(Session session) {
		if(session == null || session.getId() == null) {
			LOGGER.error("session or session id is null");
			return;
		}
		session.setTimeout(EXPIRE_TIME);
		RedisUtil.valueSet(KEY_PREFIX+session.getId(), session, EXPIRE_TIME, TimeUnit.MILLISECONDS);
	}
}

	

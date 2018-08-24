package com.ilongli.jcaptcha;

import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.ilongli.utils.RedisUtil;
import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaAndLocale;
import com.octo.captcha.service.captchastore.CaptchaStore;

public class RedisCaptchaStore implements CaptchaStore {
	
    /** 
     * 在Redis保存的key值的前缀 
     */  
    private static final String KEY_PREFIX = "jcaptcha_redis_session:"; 
    
    /**
     * 验证码过期时间(30分钟)
     */
    private static final long EXPIRE_TIME = 1800000L;

	@Override
	public void cleanAndShutdown() {
		// TODO Auto-generated method stub
	}

	@Override
	public void empty() {
		// TODO Auto-generated method stub
	}

	@Override
	public Captcha getCaptcha(String id) throws CaptchaServiceException {
        Object captchaAndLocale = RedisUtil.valueGet(KEY_PREFIX +id);
        return captchaAndLocale!=null?((CaptchaAndLocale) captchaAndLocale).getCaptcha():null;
	}

	@Override
	public Collection<?> getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale(String id) throws CaptchaServiceException {
		Object captchaAndLocale = RedisUtil.valueGet(KEY_PREFIX + id);
        return captchaAndLocale!=null?((CaptchaAndLocale) captchaAndLocale).getLocale():null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasCaptcha(String id) {
		return RedisUtil.valueGet(KEY_PREFIX + id)==null?false:true;
	}

	@Override
	public void initAndStart() {
		//Nothing to do
	}

	@Override
	public boolean removeCaptcha(String id) {
		if(RedisUtil.valueGet(KEY_PREFIX + id) != null) {
			RedisUtil.delete(KEY_PREFIX + id);
			return true;
		}
		return false;
	}

	@Override
	public void storeCaptcha(String id, Captcha captcha) throws CaptchaServiceException {
		RedisUtil.valueSet(KEY_PREFIX + id, new CaptchaAndLocale(captcha), EXPIRE_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public void storeCaptcha(String id, Captcha captcha, Locale locale) throws CaptchaServiceException {
		RedisUtil.valueSet(KEY_PREFIX + id, new CaptchaAndLocale(captcha,locale), EXPIRE_TIME, TimeUnit.MILLISECONDS);
	}

}

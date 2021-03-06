package com.ilongli.jcaptcha;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * 提供了判断仓库中是否有相应的验证码存在。
 * @author ilongli
 *
 */
public class MyManageableImageCaptchaService extends DefaultManageableImageCaptchaService {

    public MyManageableImageCaptchaService(
    		com.octo.captcha.service.captchastore.CaptchaStore captchaStore, 
    		com.octo.captcha.engine.CaptchaEngine captchaEngine, 
    		int minGuarantedStorageDelayInSeconds, 
    		int maxCaptchaStoreSize, 
    		int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    public boolean hasCaptcha(String id, String userCaptchaResponse) {
        return store.getCaptcha(id).validateResponse(userCaptchaResponse);
    }
    
    public void removeCaptcha(String id) {
    	store.removeCaptcha(id);
    }
}

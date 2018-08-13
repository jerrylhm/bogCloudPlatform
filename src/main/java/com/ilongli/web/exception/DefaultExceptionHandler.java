package com.ilongli.web.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 配置Controller异常(返回页面)
 * @author ilongli
 *
 */
@ControllerAdvice
public class DefaultExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(DefaultExceptionHandler.class);

	/**
	 * 处理所有没有权限的异常
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler({UnauthorizedException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
		LOGGER.error(e.getMessage(), e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("unauthorized");
        return mv;
	}
	
	
	

}

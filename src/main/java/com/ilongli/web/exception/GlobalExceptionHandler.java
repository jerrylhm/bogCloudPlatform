package com.ilongli.web.exception;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilongli.entity.JSONResult;

/**
 * 配置Controller全局异常(返回json信息)
 * @author ilongli
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);


    //运行时异常  
    @ExceptionHandler(RuntimeException.class)
    public JSONResult runtimeExceptionHandler(RuntimeException ex) {
        return resultFormat(1, ex);
    }
 
    //空指针异常  
    @ExceptionHandler(NullPointerException.class)
    public JSONResult nullPointerExceptionHandler(NullPointerException ex) {
        return resultFormat(2, ex);
    }
 
    //类型转换异常  
    @ExceptionHandler(ClassCastException.class)
    public JSONResult classCastExceptionHandler(ClassCastException ex) {
        return resultFormat(3, ex);
    }
 
    //IO异常  
    @ExceptionHandler(IOException.class)
    public JSONResult iOExceptionHandler(IOException ex) {
        return resultFormat(4, ex);
    }
 
    //未知方法异常  
    @ExceptionHandler(NoSuchMethodException.class)
    public JSONResult noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return resultFormat(5, ex);
    }
 
    //数组越界异常  
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public JSONResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return resultFormat(6, ex);
    }
 
    //400错误  
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public JSONResult requestNotReadable(HttpMessageNotReadableException ex) {
        System.out.println("400..requestNotReadable");
        return resultFormat(7, ex);
    }
 
    //400错误  
    @ExceptionHandler({TypeMismatchException.class})
    public JSONResult requestTypeMismatch(TypeMismatchException ex) {
        System.out.println("400..TypeMismatchException");
        return resultFormat(8, ex);
    }
 
    //400错误  
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public JSONResult requestMissingServletRequest(MissingServletRequestParameterException ex) {
        System.out.println("400..MissingServletRequest");
        return resultFormat(9, ex);
    }
 
    //405错误  
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public JSONResult request405(HttpRequestMethodNotSupportedException ex) {
        return resultFormat(10, ex);
    }
 
    //406错误  
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public JSONResult request406(HttpMediaTypeNotAcceptableException ex) {
        System.out.println("406...");
        return resultFormat(11, ex);
    }
 
    //500错误  
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public JSONResult server500(RuntimeException ex) {
        System.out.println("500...");
        return resultFormat(12, ex);
    }
 
    //栈溢出
    @ExceptionHandler({StackOverflowError.class})
    public JSONResult requestStackOverflow(StackOverflowError ex) {
        return resultFormat(13, ex);
    }
 
    //其他错误
    @ExceptionHandler({Exception.class})
    public JSONResult exception(Exception ex) {
        return resultFormat(14, ex);
    }
 
    private <T extends Throwable> JSONResult resultFormat(Integer code, T ex) {
    	LOGGER.error(ex.getMessage(), ex);
        return JSONResult.failed(code, ex.getMessage());
    }
}

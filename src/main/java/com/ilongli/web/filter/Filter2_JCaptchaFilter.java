package com.ilongli.web.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.ilongli.jcaptcha.JCaptcha;

/**
 * JCaptcha获取验证码(验证码方式)
 * @author ilongli
 *
 */
@WebFilter(filterName = "JCaptchaFilter", urlPatterns = "/jcaptcha", asyncSupported=true)
public class Filter2_JCaptchaFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        
        String sessionId = request.getSession().getId();
        
        JCaptcha.captchaService.removeCaptcha(sessionId);
        
        BufferedImage bi = JCaptcha.captchaService.getImageChallengeForID(sessionId);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}

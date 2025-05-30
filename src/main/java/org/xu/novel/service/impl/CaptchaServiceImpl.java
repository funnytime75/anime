package org.xu.novel.service.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.xu.novel.service.CaptchaService;
/*
创建服务处理验证码验证
 */

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Override
    public void validateCaptcha(String captchaCode, HttpSession session) {
        String realCaptcha = (String) session.getAttribute("captcha");
        if(realCaptcha != null && realCaptcha.equalsIgnoreCase(captchaCode))
            System.out.println("验证码正确");
    }
}

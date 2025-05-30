package org.xu.novel.service;

import jakarta.servlet.http.HttpSession;

public interface CaptchaService {
    void validateCaptcha(String captcha, HttpSession session);
}

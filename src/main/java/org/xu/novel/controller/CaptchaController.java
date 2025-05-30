package org.xu.novel.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xu.novel.utils.CodeGeneratorImpl;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final CodeGeneratorImpl codeGenerator;

    /*
    图片验证码输出流
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, codeGenerator,100);
        session.setAttribute("captcha", lineCaptcha.getCode());
        response.setContentType("image/png");
        lineCaptcha.write(response.getOutputStream());
    }

}

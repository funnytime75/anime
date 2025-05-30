package org.xu.novel.utils;

import cn.hutool.captcha.LineCaptcha;

public class Captcha {
    public static void main(String[] args) {
        LineCaptcha lineCaptcha = new LineCaptcha(200, 100,4,20);
        System.out.println("验证码类容："+lineCaptcha.getCode());
        lineCaptcha.write("C:\\Users\\32589\\Desktop\\captcha.png");
    }
}

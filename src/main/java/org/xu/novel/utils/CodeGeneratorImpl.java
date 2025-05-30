package org.xu.novel.utils;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CodeGeneratorImpl implements CodeGenerator {

    static String baseCode="23456789qwertyupasdfghjkzxcvbnm";

    static int length = 4;
    @Override
    public String generate() {
        StringBuilder sb=new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(baseCode.charAt(random.nextInt(baseCode.length())));
        }
        return sb.toString();
    }

    @Override
    public boolean verify(String code, String userInputCode) {
        return StrUtil.isNotBlank(userInputCode) && StrUtil.equalsIgnoreCase(code, userInputCode);
    }
}

package org.xu.novel.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public enum UserEnum {
    ACCOUNT("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$","账号格式不正确"),
    PASSWORD("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$","密码必须包含数字和字符，且最小为八位");
    private final String pattern;
    private final String message;

    public boolean isValid(String input) {
        return Pattern.matches(pattern, input);
    }
}

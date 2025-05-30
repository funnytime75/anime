package org.xu.novel.domain.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String account;
    private String password;
    private String captcha;
}

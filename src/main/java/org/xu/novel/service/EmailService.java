package org.xu.novel.service;

import org.xu.novel.domain.po.Result;

public interface EmailService {
    Result<Void> sendVerificationCode(String email);

    Result<Boolean> verifyCode(String email, String code);
}
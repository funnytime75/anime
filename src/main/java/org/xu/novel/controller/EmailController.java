package org.xu.novel.controller;

import org.springframework.web.bind.annotation.*;
import org.xu.novel.domain.po.Result;
import org.xu.novel.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    /*
    发送验证码
     */
    @PostMapping("/send-code")
    public Result<Void> sendVerificationCode(@RequestParam String email) {
        return emailService.sendVerificationCode(email);
    }
    /*
    验证验证码
     */
    @PostMapping("/verify-code")
    public Result<Boolean> verifyCode(@RequestParam String email,
                                      @RequestParam String code) {
        return emailService.verifyCode(email, code);
    }
}
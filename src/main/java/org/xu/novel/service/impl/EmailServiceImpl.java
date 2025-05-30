package org.xu.novel.service.impl;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.xu.novel.domain.po.Result;
import org.xu.novel.service.EmailService;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    // 验证码有效期（分钟）
    private static final long CODE_EXPIRE_MINUTES = 5;
    // Redis key前缀
    private static final String REDIS_KEY_PREFIX = "verification_code:";

    @Override
    public Result<Void> sendVerificationCode(String email) {
        // 生成6位随机验证码
        String verificationCode = generateRandomCode();

        // 存储验证码，5分钟过期
        String redisKey = REDIS_KEY_PREFIX + email;
        redisTemplate.opsForValue().set(redisKey, verificationCode, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        // 发送邮件
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    new InternetAddress[]{new InternetAddress(email)});
            mimeMessage.setFrom(new InternetAddress("funnytimeday@163.com"));
            mimeMessage.setSubject("您的验证码");
            mimeMessage.setText("您的验证码是: " + verificationCode + "，有效期为5分钟。");
        };

        try {
            mailSender.send(preparator);
            return Result.success();
        } catch (Exception e) {
            // 发送失败时删除Redis中的验证码
            redisTemplate.delete(redisKey);
            return Result.error("验证码发送失败: " + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> verifyCode(String email, String code) {
        String redisKey = REDIS_KEY_PREFIX + email;
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode == null) {
            return Result.success(false); // 验证码不存在或已过期
        }

        // 验证码比对（不过是纯数字）
        boolean isValid = storedCode.equalsIgnoreCase(code);

        if (isValid) {
            redisTemplate.delete(redisKey);
        }

        return Result.success(isValid);
    }

    /*
    生成四位数的密码
     */
    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
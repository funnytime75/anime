package org.xu.novel.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reader {
    private Integer id;
    private String account;
    private String username;
    private String password;
    private Integer registrationDuration;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

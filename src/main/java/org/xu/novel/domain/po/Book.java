package org.xu.novel.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Book {
    private Integer id;
    private String title;
    private String summary;
    private String genre;
    private Integer wordCount;
    private String authorUsername;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String status;
    private String cover;
}
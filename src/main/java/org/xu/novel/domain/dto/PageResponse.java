package org.xu.novel.domain.dto;

import lombok.Data;

import java.util.List;
@Data
public class PageResponse<T> {
    private List<T> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}

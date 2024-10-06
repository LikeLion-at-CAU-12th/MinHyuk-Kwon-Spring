package com.example.demo.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleUpdateRequestDto {
    // memberId는 필요없음
    private String title;
    private String content;
    private List<Long> categoryIds;
}

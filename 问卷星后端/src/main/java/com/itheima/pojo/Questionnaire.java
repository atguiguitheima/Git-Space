package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Questionnaire {
    private Integer id;
    private String qnTitle;
    private Integer qnId;
    private Integer userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer id;
    private Short qnQtType;
    private String qnQtTitle;
    private Integer qnQtId;
    private Integer qnId;
    private Integer userId;
    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

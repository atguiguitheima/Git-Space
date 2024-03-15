package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Integer id;
    private Integer answerUserId;
    private Integer qnId;
    private Integer qnQtId;
    private Integer userId;
    private String answer;
    private String sign;
    private LocalDateTime createTime;
}

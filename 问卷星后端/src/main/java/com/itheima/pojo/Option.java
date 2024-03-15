package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    private Integer id;
    private String otTitle;
    private Integer qtId;
    private Integer qnId;
    private Integer userId;
    private Integer otId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

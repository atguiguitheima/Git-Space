package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utile {
    private Integer id;
    private  Integer userId;
    private Integer qnNumber;
    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

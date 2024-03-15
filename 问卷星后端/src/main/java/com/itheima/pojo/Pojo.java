package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pojo {
    private Questionnaire qn;

    private List<Question> qtList;

    private List<Option> otList;
}

package com.itheima.service;

import com.itheima.pojo.*;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    List<Questionnaire> getQnTitle();
    Result login(Login login);

    Result register(User user);

    void addQuestionnaire(Pojo pojo);

    Pojo getQn(Integer qnId);

    void deleteQn(Integer qnId);

    void updateQuestionnaire(Pojo pojo);

    List<Questionnaire> getConditionalQns(String qnTitle);

    Pojo getAnswer(Integer userId, Integer qnId);

    LocalDateTime addAnswer(List<Answer> answerList);

    void setDeadline(QnManage qnManage);

    Result getDeadline(QnManage qnManage);

    AutoLogin parseJwt();

    String logout(Boolean autoLogin);

    void modifyPassword(String newPassword);

}

package com.itheima.mapper;

import com.itheima.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserMapper {

    List<Questionnaire> selectQns(Integer userId,String qnTitle);

    User selectUser(User user);

    void insertUser(User user);

    @Select("select qn_number from questionnaire_star.tb_utile where user_id=#{userId}")
    Utile selectQnNumber(Integer userId);

    @Update("update questionnaire_star.tb_utile set qn_number=#{qnNumber},update_time=#{updateTime} where user_id=#{userId}")
    void updateQnNumber(Utile utile);

    @Insert("insert into questionnaire_star.tb_questionnaire (qn_title, qn_id, user_id, create_time, update_time) VALUES (" +
            "#{qnTitle},#{qnId},#{userId},#{createTime},#{updateTime})")
    void insertQn(Questionnaire qn);

    @Insert("insert into questionnaire_star.tb_utile(user_id, qn_number, create_time, update_time) VALUES " +
            "(#{userId},#{qnNumber},#{createTime},#{updateTime})")
    void insertUtile(Utile utile);

    @Insert("insert into questionnaire_star.tb_qn_question ( qn_qt_type, qn_qt_title, qn_qt_id, qn_id, user_id, create_time, update_time) VALUES " +
            "(#{qnQtType},#{qnQtTitle},#{qnQtId},#{qnId},#{userId},#{createTime},#{updateTime})")
    void insertQt(Question question);

    @Insert("insert into questionnaire_star.tb_question_options( ot_title, qt_id, qn_id, user_id, ot_id, create_time, update_time) VALUES" +
            "(#{otTitle},#{qtId},#{qnId},#{userId},#{otId},#{createTime},#{updateTime}) ")
    void insertOt(Option option);

    @Select("select * from questionnaire_star.tb_qn_question where qn_id=#{qnId} and user_id=#{userId}")
   List<Question> selectQt(Integer qnId,Integer userId);

    @Select("select * from questionnaire_star.tb_question_options where qn_id=#{qnId} and user_id=#{userId}")
    List<Option> selectOt(Integer qnId,Integer userId);

    @Delete("delete from questionnaire_star.tb_questionnaire where qn_id=#{qnId} and user_id=#{userId}")
    void deleteQn(Integer qnId, Integer userId);

    @Delete("delete from questionnaire_star.tb_qn_question where qn_id=#{qnId} and user_id=#{userId}")
    void deleteQt(Integer qnId, Integer userId);

    @Delete("delete from questionnaire_star.tb_question_options where qn_id=#{qnId} and user_id=#{userId}")
    void deleteOt(Integer qnId, Integer userId);

    @Select("select id, qn_title, qn_id, user_id, create_time, update_time from questionnaire_star.tb_questionnaire where user_id=#{userId} and qn_id=#{qnId}")
    Questionnaire selectQn(Integer userId, Integer qnId);

    @Insert("insert into questionnaire_star.tb_answer (answer_user_id, qn_qt_id, qn_id, user_id,  create_time, answer) VALUES " +
            "(#{answerUserId},#{qnQtId},#{qnId},#{userId},#{createTime},#{answer})")
    void insertAnswer(Answer answer);

    @Select("select id, user_id, qn_id, deadline, create_time, update_time from questionnaire_star.tb_qn_manage where user_id=#{userId} and qn_id=#{qnId}")
    QnManage selectQnManager(QnManage qnManage);

    @Insert("insert into questionnaire_star.tb_qn_manage (user_id, qn_id, deadline, create_time, update_time)values" +
            " (#{userId},#{qnId},#{deadline},#{createTime},#{updateTime})")
    void insertQnManager(QnManage qnManage);

    @Update("update questionnaire_star.tb_qn_manage set deadline=#{deadline} where user_id=#{userId} and qn_id=#{qnId}")
    void updateQnManager(QnManage qnManage);

    @Update("update questionnaire_star.user set password=#{newPassword} where id=#{userId}")
    void updatePassword(Object userId, String newPassword);

}

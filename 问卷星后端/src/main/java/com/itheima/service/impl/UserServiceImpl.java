package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.*;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;



    @Override
    public List<Questionnaire> getQnTitle() {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer userId=(Integer)claims.get("id");
        return userMapper.selectQns(userId,null);
    }

    @Override
    public Result login(Login login) {
        User usr = userMapper.selectUser(login.getUser());
        if (usr!=null){
            Map map=new HashMap();
            map.put("username",usr.getUsername());
            map.put("password",usr.getPassword());
            map.put("id",usr.getId());
            map.put("autoLogin",login.getAutoLogin()?1:0);
            map.put("rememberPassword",login.getRememberPassword()?1:0);
            String jwt = JwtUtils.generateJWT(map);
            return Result.success(jwt);
        }else{return Result.error("登入失败,不存在此账号或密码错误!");}
    }

    @Override
    public Result register(User user) {
        String password = user.getPassword();
        user.setPassword(null);
        User usr = userMapper.selectUser(user);
        if (usr!=null){
            return Result.error("注册失败,用户名已存在!");
        }else{
            user.setPassword(password);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            userMapper.insertUser(user);
            Utile utile=new Utile();
            utile.setUserId(user.getId());
            utile.setQnNumber(1);
            utile.setCreateTime(LocalDateTime.now());
            utile.setUpdateTime(LocalDateTime.now());
            userMapper.insertUtile(utile);
            return Result.success();
        }
    }

    @Override
    public void addQuestionnaire(Pojo pojo) {
       String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);

        Questionnaire qn = pojo.getQn();
        Integer userId=(Integer)claims.get("id");
        qn.setUserId(userId);
        Utile utile = userMapper.selectQnNumber(userId);
        Integer qnId = utile.getQnNumber();
        qn.setQnId(qnId);
        utile.setUserId(userId);
        utile.setQnNumber(qnId+1);
        utile.setUpdateTime(LocalDateTime.now());
        userMapper.updateQnNumber(utile);
        qn.setCreateTime(LocalDateTime.now());
        qn.setUpdateTime(LocalDateTime.now());
        userMapper.insertQn(qn);

        List<Question> qtList = pojo.getQtList();
        for (Question question : qtList) {
            question.setQnId(qnId);
            question.setUserId(userId);
            question.setCreateTime(LocalDateTime.now());
            question.setUpdateTime(LocalDateTime.now());
            userMapper.insertQt(question);
        }

        List<Option> otList = pojo.getOtList();
        for (Option option : otList) {
            option.setQnId(qnId);
            option.setUserId(userId);
            if (option.getOtTitle()==null){
                option.setOtTitle("无");
            }
            option.setCreateTime(LocalDateTime.now());
            option.setUpdateTime(LocalDateTime.now());
            userMapper.insertOt(option);
        }
    }

    @Override
    public Pojo getQn(Integer qnId) {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer userId=(Integer)claims.get("id");

        Questionnaire qn=new Questionnaire();
        qn.setQnId(qnId);
        List<Question> qtList=userMapper.selectQt(qnId,userId);
        List<Option> otList=userMapper.selectOt(qnId,userId);
        return new Pojo(qn,qtList,otList);
    }

    @Override
    public void deleteQn(Integer qnId) {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer userId=(Integer)claims.get("id");

        userMapper.deleteQn(qnId,userId);
        userMapper.deleteQt(qnId,userId);
        userMapper.deleteOt(qnId,userId);
    }

    @Override
    public void updateQuestionnaire(Pojo pojo) {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);

        Questionnaire qn = pojo.getQn();
        Integer qnId = qn.getQnId();
        Integer userId=(Integer)claims.get("id");
        qn.setUserId(userId);
        if(qn.getCreateTime()==null){
            qn.setCreateTime(LocalDateTime.now());
        }
        qn.setUpdateTime(LocalDateTime.now());
        userMapper.deleteQn(qnId,userId);
        userMapper.insertQn(qn);

        List<Question> qtList = pojo.getQtList();
        userMapper.deleteQt(qnId,userId);
        for (Question question : qtList) {
            question.setQnId(qnId);
            question.setUserId(userId);
            if(question.getCreateTime()==null){
                question.setCreateTime(LocalDateTime.now());
            }
            question.setUpdateTime(LocalDateTime.now());

            userMapper.insertQt(question);
        }


        List<Option> otList = pojo.getOtList();
        userMapper.deleteOt(qnId,userId);
        for (Option option : otList) {
            option.setQnId(qnId);
            option.setUserId(userId);
            if (option.getOtTitle()==null){
                option.setOtTitle("无");
            }
            if(option.getCreateTime()==null){
                option.setCreateTime(LocalDateTime.now());
            }
            option.setUpdateTime(LocalDateTime.now());
            userMapper.insertOt(option);
        }

    }

    @Override
    public List<Questionnaire> getConditionalQns(String qnTitle) {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer userId=(Integer)claims.get("id");
        return userMapper.selectQns(userId,qnTitle);

    }

    @Override
    public Pojo getAnswer(Integer userId, Integer qnId) {
        Questionnaire qn= userMapper.selectQn(userId,qnId);
        List<Question> qtList=userMapper.selectQt(qnId,userId);
        List<Option> otList=userMapper.selectOt(qnId,userId);
        return new Pojo(qn,qtList,otList);
    }

    @Override
    public LocalDateTime addAnswer(List<Answer> answerList) {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer answerUserId=(Integer)claims.get("id");

        for (Answer answer : answerList) {
            if ("是".equals(answer.getSign())){
                answer.setAnswerUserId(answerUserId);
                answer.setCreateTime(LocalDateTime.now());
                userMapper.insertAnswer(answer);
            }
        }
        return LocalDateTime.now();
    }

    @Override
    public void setDeadline(QnManage qnManage) {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer userId=(Integer)claims.get("id");

        qnManage.setUserId(userId);

        qnManage.setUpdateTime(LocalDateTime.now());
        QnManage qM=userMapper.selectQnManager(qnManage);

        if (qM==null){
            qnManage.setCreateTime(LocalDateTime.now());
            userMapper.insertQnManager(qnManage);
        }else{
            userMapper.updateQnManager(qnManage);
        }
    }

    @Override
    public Result getDeadline(QnManage qnManage) {
        QnManage qM = userMapper.selectQnManager(qnManage);
        if (qM!=null){
        return Result.success(qM.getDeadline());
        }else{
            return Result.error("未设置截止时间");
        }
    }

    @Override
    public AutoLogin parseJwt() {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer autoLogin=(Integer)claims.get("autoLogin");
        Integer rememberPassword=(Integer)claims.get("rememberPassword");
        String username=(String)claims.get("username");
        String password=(String)claims.get("password");
        log.info("返回的信息 : {},{},{},{}",autoLogin,rememberPassword,username,password);
        return new AutoLogin(autoLogin,rememberPassword,username,password);
    }

    @Override
    public String logout(Boolean autoLogin) {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);

        claims.put("autoLogin",autoLogin?1:0);
        String newJwt = JwtUtils.generateJWT(claims);
        return newJwt;
    }

    @Override
    public void modifyPassword(String newPassword) {
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Object userId = claims.get("id");

        userMapper.updatePassword(userId,newPassword.substring(1,newPassword.length()-1));
    }


}

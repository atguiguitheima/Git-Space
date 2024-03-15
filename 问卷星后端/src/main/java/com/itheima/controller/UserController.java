package com.itheima.controller;

import com.itheima.pojo.*;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public Result getQuestionnaireTitle(){
        List<Questionnaire> questionnaireList=userService.getQnTitle();
        log.info("查询所有问卷的标题: {}",questionnaireList);
        return Result.success(questionnaireList);
    }

    @GetMapping("/qn")
    public Result getQn( Integer qnId){
        Pojo pojo=userService.getQn(qnId);
        return Result.success(pojo);
    }

    @PostMapping
    public Result addQuestionnaire(@RequestBody Pojo pojo){
        log.info("问卷: {}",pojo);
        userService.addQuestionnaire(pojo);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteQuestionnaire(Integer qnId){
        log.info("删除的问卷id: {}",qnId);
        userService.deleteQn(qnId);
        return Result.success();
    }

    @PutMapping
    public Result updateQuestionnaire(@RequestBody Pojo pojo){
        log.info("修改后的信息: {}",pojo);
        userService.updateQuestionnaire(pojo);
        return Result.success();
    }

    @GetMapping("/conditionQns")
    public Result getConditionalQns(String qnTitle){
        List<Questionnaire> qnList=userService.getConditionalQns(qnTitle);
        return  Result.success(qnList);
    }

    @GetMapping("/answer")
    public Result getAnswer(Integer userId ,Integer qnId){
        Pojo pojo=userService.getAnswer(userId,qnId);
        return Result.success(pojo);
    }

    @PostMapping("/answer")
    public Result addAnswer(@RequestBody List<Answer> answerList){
        log.info("答卷信息: {}",answerList);
        LocalDateTime deadline=userService.addAnswer(answerList);
        return  Result.success(deadline);
    }

    @PostMapping("/deadline")
     public Result setDeadline(@RequestBody @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")QnManage qnManage){
        log.info("全部信息: {},截止时间: {}",qnManage,qnManage.getDeadline());
        userService.setDeadline(qnManage);
        return Result.success();
     }

    @GetMapping("/deadline")
    public Result getDeadLine(QnManage qnManage){
        return userService.getDeadline(qnManage);
     }

     @GetMapping("/parseJwt")
    public Result parseJwt(){
        return  Result.success(userService.parseJwt());
     }

    @PutMapping("/modify")
    public Result modifyPassword(@RequestBody String newPassword){
        log.info("新的密码: {}",newPassword);
        userService.modifyPassword(newPassword);
        return Result.success();
    }
}

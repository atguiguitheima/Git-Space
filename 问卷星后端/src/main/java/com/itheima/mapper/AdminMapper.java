package com.itheima.mapper;

import com.itheima.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select id from questionnaire_star.admin where admin_name=#{adminName} and password=#{password}")
    Admin selectAdmin(Admin admin);
}

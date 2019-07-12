package com.cm.dao;

import com.cm.model.User;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

public interface UserDao {
    Integer addUser(User user);
    /**
     * 根据id查找用户信息
     * @param userId 登录用户id
     * @return
     */
    User findUserById(String userId);
    /**
     * 根据类型查找用户信息
     * @param userType 登录用户id
     * @return
     */
    User findUsersByUserType(Integer userType);
    /**
     * 查询全部用户信息
     * @return
     */
    List<User> findAll(Map<String, Object> map) throws Exception;

    /**
     * 修改密码
     */
    Integer updatePwd (User user);
    /**
     * 重置密码
     */
    Integer resetPwd (User user);
    /**
     * 修改手机号
     */
    Integer updatePhone (User user);
    Integer updateTeacher (User user);
    Integer deleteUser (User user);
    Integer uploadPhoto (User user);
}
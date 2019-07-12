package com.cm.service;

import com.cm.config.ServerResponse;
import com.cm.model.PageBean;
import com.cm.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    ServerResponse addUser(String userId, String userName, String password, String sex, Integer userType, String classId);
    ServerResponse findUserById(String userId);
    ServerResponse findUsersByUserType(Integer userType);
    List<User> findAll(Map<String, Object> map);
    ServerResponse<Integer> updatePwd(String userId, String oldPwd, String newPwd);
    ServerResponse<Integer> resetPwd(String userId);
    ServerResponse<Integer> updatePhone(String userId, String newPhone);
    ServerResponse<Integer> uploadPhoto(String userId, String photo);
    ServerResponse<Integer> updateTeacher(String userId,String userName, String sex);
    ServerResponse deleteUser(String userId);

//    boolean deleteUser(Integer id);
//

//
//    void saveUser(User user);

}
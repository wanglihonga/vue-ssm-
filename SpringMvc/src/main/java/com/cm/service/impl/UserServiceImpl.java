package com.cm.service.impl;
import com.cm.config.ServerResponse;
import com.cm.dao.UserDao;
import com.cm.model.PageBean;
import com.cm.model.User;
import com.cm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    public ServerResponse addUser(String userId, String userName, String password, String sex, Integer userType, String classId) {
        User addU = new User();
        addU.setUserId(userId);
        addU.setUserName(userName);
        addU.setPassword(password);
        addU.setSex(sex);
        addU.setUserType(userType);
        addU.setClassId(classId);
        Integer number=userDao.addUser(addU);
        if (number > 0){
            return ServerResponse.createBySuccess("新增成功",number);
        }
        return ServerResponse.createByError("新增失败");
    }
    public ServerResponse findUserById(String userId) {
        User users = userDao.findUserById(userId);
        if(users != null) {
            return ServerResponse.createBySuccess("查询成功",users);
        }
        return ServerResponse.createByError("查询记录为空");
    }
    public ServerResponse findUsersByUserType(Integer userType) {
        User users = userDao.findUsersByUserType(userType);
        if(users != null) {
            return ServerResponse.createBySuccess("查询成功",users);
        }
        return ServerResponse.createByError("查询记录为空");
    }

    @Override
    public List<User> findAll(Map<String, Object> map) {
        try {
            return userDao.findAll(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public List<User> findAll() {
//
//        return userDao.findAll();
//    }
//    public ServerResponse<List<User>> findAll() {
//        //DAO层查询数据
//        List<User> userList = userDao.findAll();
//        if (userList.size()>0){
//            return ServerResponse.createBySuccess("查询成功",userList);
//        }
//        return ServerResponse.createByError("查询记录为空");
//    }

    public ServerResponse<Integer> updatePwd(String userId, String oldPwd, String newPwd) {
        User user=new User();
        user.setUserId(userId);
        user.setOldPwd(oldPwd);
        user.setNewPwd(newPwd);
        Integer number=userDao.updatePwd(user);
        if (number > 0){
            return ServerResponse.createBySuccess("修改成功",number);
        }
        return ServerResponse.createByError("修改失败");
    }
    public ServerResponse<Integer> resetPwd(String userId) {
        User user=new User();
        user.setUserId(userId);
        Integer number=userDao.resetPwd(user);
        if (number > 0){
            return ServerResponse.createBySuccess("修改成功",number);
        }
        return ServerResponse.createByError("修改失败");
    }
    public ServerResponse<Integer> updateTeacher(String userId,String userName, String sex) {
        User user=new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setSex(sex);
        Integer number=userDao.updateTeacher(user);
        if (number > 0){
            return ServerResponse.createBySuccess("修改成功",number);
        }
        return ServerResponse.createByError("修改失败");
    }
    public ServerResponse<Integer> updatePhone(String userId, String newPhone) {
        User user=new User();
        user.setUserId(userId);
        user.setNewPhone(newPhone);
        Integer number=userDao.updatePhone(user);
        if (number > 0){
            return ServerResponse.createBySuccess("修改成功",number);
        }
        return ServerResponse.createByError("修改失败");
    }
    public ServerResponse<Integer> uploadPhoto(String userId, String photo) {
        User user=new User();
        user.setUserId(userId);
        user.setPhoto(photo);
        Integer number=userDao.uploadPhoto(user);
        if (number > 0){
            return ServerResponse.createBySuccess("上传成功",number);
        }
        return ServerResponse.createByError("上传失败");
    }
    public ServerResponse deleteUser(String userId) {
        User user=new User();
        user.setUserId(userId);
        Integer deletenum=userDao.deleteUser(user);
        if (deletenum > 0){
            return ServerResponse.createBySuccess("删除成功",deletenum);
        }
        return ServerResponse.createByError("删除失败");
    }
//
//    public Integer updateUser(User user) {
//        return userDao.updateUser(user);
//    }
//
//    public void saveUser(User user) {
//        userDao.saveUser(user);
//    }
}

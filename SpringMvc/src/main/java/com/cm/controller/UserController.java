package com.cm.controller;
import com.cm.config.ServerResponse;
import com.cm.model.User;
import com.cm.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(value="findByUserId",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findByUserId(String userId) {
        System.out.println(userId);
        return userService.findUserById(userId);
    }
    @RequestMapping(value="findUsersByUserType",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findUsersByUserType(Integer userType) {
        return userService.findUsersByUserType(userType);
    }
//    @RequestMapping(value="getAllUsers",method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse showAllStudent(HttpSession session){
//        return userService.findAll();
//    }
    /**
            * 分页查询
     */
    @RequestMapping(value="findAll",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getPages(Integer current, Integer limit, Integer userType, String userName) {
        PageHelper.startPage(current, limit);
//        PageInfo<User> pageInfo = new PageInfo<User>(userService.findAll());
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userType",userType);
        map.put("userName",userName);
        List<User> docs = userService.findAll(map);
        PageInfo<User> pageInfo = new PageInfo<>(docs);
        ServerResponse serverResponse=new ServerResponse(100,pageInfo,"");
        return serverResponse;
    }
    @RequestMapping(value="updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatePwd(String userId, String oldPwd, String newPwd) {
        return userService.updatePwd(userId,oldPwd,newPwd);
    }
    @RequestMapping(value="resetPwd",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse resetPwd(String userId) {
        return userService.resetPwd(userId);
    }
    @RequestMapping(value="updatePhone",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatePhone(String userId, String newPhone) {
        return userService.updatePhone(userId,newPhone);
    }
    @RequestMapping(value="uploadPhoto",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadPhoto(String userId, String photo) {
        return userService.uploadPhoto(userId,photo);
    }
    @RequestMapping(value="updateTeacher",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateTeacher(String userId,String userName, String sex) {
        return userService.updateTeacher(userId,userName,sex);
    }
    @RequestMapping(value="addUser",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addUser(String userId, String userName, String password, String sex, Integer userType, String classId) {
        return userService.addUser(userId, userName, password, sex, userType, classId);
    }
    @RequestMapping(value="deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteUser(String userId) {
        return userService.deleteUser(userId);
    }
}
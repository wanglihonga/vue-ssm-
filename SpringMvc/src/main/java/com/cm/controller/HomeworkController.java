package com.cm.controller;
import com.cm.config.ServerResponse;
import com.cm.model.Homework;
import com.cm.service.HomeworkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@Controller
@RequestMapping("/homework")
public class HomeworkController {
    private HomeworkService homeworkService;
    @Autowired
    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }
    /**
     * 分页查询
     */
    @RequestMapping(value="findAllHomework",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getPages(Integer current, Integer limit, String className, String teacherId) {
        PageHelper.startPage(current, limit);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("className",className);
        map.put("teacherId", teacherId);
        List<Homework> docs = homeworkService.findAllHomework(map);
        PageInfo<Homework> pageInfo = new PageInfo<>(docs);
        ServerResponse serverResponse=new ServerResponse(100,pageInfo,"");
        return serverResponse;
    }
    @RequestMapping(value="addHomework",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addHomework(String teacherId, String subId, String pushTime, String judgeState,String fileSrc, String endTime, String classId) {
        return homeworkService.addHomework(teacherId,subId,pushTime,fileSrc,endTime,classId);
    }
    @RequestMapping(value="deleteHomework",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteUser(Integer id) {
        return homeworkService.deleteHomework(id);
    }
    @RequestMapping(value="updateHomework",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateHomework(Integer id, String endTime) {
        return homeworkService.updateHomework(id, endTime);
    }
    @RequestMapping(value="findHomeworkById",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findHomeworkById(Integer id) {
        return homeworkService.findHomeworkById(id);
    }
    @RequestMapping(value="selectAllHomework",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectAllHomework() {
        return homeworkService.selectAllHomework();
    }
}
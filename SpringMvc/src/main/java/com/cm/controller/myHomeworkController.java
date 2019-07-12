package com.cm.controller;
import com.cm.config.ServerResponse;
import com.cm.model.myHomework;
import com.cm.service.myHomeworkSrevice;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
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
@RequestMapping("/myHomework")
public class myHomeworkController {
    private myHomeworkSrevice myhomeworkService;
    @Autowired
    public myHomeworkController(myHomeworkSrevice myhomeworkService) {
        this.myhomeworkService = myhomeworkService;
    }
    /**
     * 分页查询
     */
    @RequestMapping(value="getMyHomeworkList",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getPages(Integer current, Integer limit, String classId, String subName) {
        PageHelper.startPage(current, limit);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("classId",classId);
        map.put("subName",subName);
        List<myHomework> docs = myhomeworkService.getMyHomeworkList(map);
        PageInfo<myHomework> pageInfo = new PageInfo<>(docs);
        ServerResponse serverResponse=new ServerResponse(100,pageInfo,"");
        return serverResponse;
    }
    @RequestMapping(value="addmyHomework",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addmyHomework(String stuId, String stuName,String teacherName, Integer homeworkId, String subName,String className,String pushTime, String judgeState,String endTime, String commitTime,String uploadFile,String downloadFile,String classId,String commitState, Integer score, String teacherId) {
        return myhomeworkService.addmyHomework(stuId,stuName,teacherName,homeworkId,subName,className,pushTime,judgeState,endTime,commitTime,uploadFile,downloadFile,classId,commitState,score, teacherId);
    }
    @RequestMapping(value="giveScore",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateSubject(Integer score, Integer homeworkId) {
        return myhomeworkService.giveScore(score, homeworkId);
    }
    @RequestMapping(value="findmyHomeworkById",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findmyHomeworkById(Integer homeworkId) {
        return myhomeworkService.findmyHomeworkById(homeworkId);
    }
    @RequestMapping(value="findWorkByTeaId",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getPagess(Integer current, Integer limit, String teacherId, String subName) {
        PageHelper.startPage(current, limit);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("teacherId",teacherId);
        map.put("subName",subName);
        List<myHomework> docs = myhomeworkService.findWorkByTeaId(map);
        PageInfo<myHomework> pageInfo = new PageInfo<>(docs);
        ServerResponse serverResponse=new ServerResponse(100,pageInfo,"");
        return serverResponse;
    }
}
package com.cm.controller;

import com.cm.config.ServerResponse;
import com.cm.model.Classes;
import com.cm.service.ClassesService;
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
@RequestMapping("/classes")
public class ClassesController {
    private ClassesService classesService;
    @Autowired
    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
    }
    /**
     * 分页查询
     */
    @RequestMapping(value="findAllClass",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getPages(Integer current, Integer limit, String className) {
        PageHelper.startPage(current, limit);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("className",className);
        List<Classes> docs = classesService.findAllClass(map);
        PageInfo<Classes> pageInfo = new PageInfo<>(docs);
        ServerResponse serverResponse=new ServerResponse(100,pageInfo,"");
        return serverResponse;
    }
    @RequestMapping(value="addClass",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addClass(String classId, String className) {
        return classesService.addClass(classId, className);
    }
    @RequestMapping(value="deleteClass",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteUser(String classId) {
        return classesService.deleteClass(classId);
    }
    @RequestMapping(value="updateClass",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateClass(String classId, String className) {
        return classesService.updateClass(classId, className);
    }
    @RequestMapping(value="findClassById",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findClassById(String classId) {
        return classesService.findClassById(classId);
    }
    @RequestMapping(value="selectAllClass",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectAllClass() {
        return classesService.selectAllClass();
    }
}

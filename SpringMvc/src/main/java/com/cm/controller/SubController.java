package com.cm.controller;
import com.cm.config.ServerResponse;
import com.cm.model.Subject;
import com.cm.service.SubService;
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
@RequestMapping("/subject")
public class SubController {
    private SubService subService;
    @Autowired
    public SubController(SubService subService) {
        this.subService = subService;
    }
    /**
     * 分页查询
     */
    @RequestMapping(value="findAllSub",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getPages(Integer current, Integer limit, String subName) {
        PageHelper.startPage(current, limit);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("subName",subName);
        List<Subject> docs = subService.findAllSub(map);
        PageInfo<Subject> pageInfo = new PageInfo<>(docs);
        ServerResponse serverResponse=new ServerResponse(100,pageInfo,"");
        return serverResponse;
    }
    @RequestMapping(value="addSubject",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addSubject(String subId, String subName) {
        return subService.addSubject(subId, subName);
    }
    @RequestMapping(value="deleteSubject",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteUser(String subId) {
        return subService.deleteSubject(subId);
    }
    @RequestMapping(value="updateSubject",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateSubject(String subId, String subName) {
        return subService.updateSubject(subId, subName);
    }
    @RequestMapping(value="findSubjectById",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findSubjectById(String subId) {
        return subService.findSubjectById(subId);
    }
    @RequestMapping(value="selectAllSub",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectAllSub() {
        return subService.selectAllSub();
    }
}

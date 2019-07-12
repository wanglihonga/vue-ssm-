package com.cm.service.impl;
import com.cm.config.ServerResponse;
import com.cm.dao.HomeworkDao;
import com.cm.model.Homework;
import com.cm.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("homeworkService")
public class HomeworkImpl implements HomeworkService {
    @Autowired
    private HomeworkDao homeworkDao;
    public ServerResponse addHomework(String teacherId, String subId, String pushTime,String fileSrc,String endTime, String classId) {
        Homework hk = new Homework();
        hk.setTeacherId(teacherId);
        hk.setSubId(subId);
        hk.setPushTime(pushTime);
        hk.setFileSrc(fileSrc);
        hk.setEndTime(endTime);
        hk.setClassId(classId);
        Integer number=homeworkDao.addHomework(hk);
        if (number > 0){
            return ServerResponse.createBySuccess("新增成功",number);
        }
        return ServerResponse.createByError("新增失败");
    }
    @Override
    public List<Homework> findAllHomework(Map<String, Object> map) {
        try {
            return homeworkDao.findAllHomework(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ServerResponse selectAllHomework() {
        List<Homework> clas = homeworkDao.selectAllHomework();
        if(clas != null) {
            return ServerResponse.createBySuccess("查询成功",clas);
        }
        return ServerResponse.createByError("查询记录为空");
    }
    public ServerResponse<Integer> updateHomework(Integer id, String endTime) {
        Homework cla=new Homework();
        cla.setId(id);
        cla.setEndTime(endTime);
        Integer number=homeworkDao.updateHomework(cla);
        if (number > 0){
            return ServerResponse.createBySuccess("修改成功",number);
        }
        return ServerResponse.createByError("修改失败");
    }
    public ServerResponse deleteHomework(Integer id) {
        Homework ho=new Homework();
        ho.setId(id);
        Integer deletenum=homeworkDao.deleteHomework(ho);
        if (deletenum > 0){
            return ServerResponse.createBySuccess("删除成功",deletenum);
        }
        return ServerResponse.createByError("删除失败");
    }
    public ServerResponse findHomeworkById(Integer id) {
        Homework clas = homeworkDao.findHomeworkById(id);
        if(clas != null) {
            return ServerResponse.createBySuccess("查询成功",clas);
        }
        return ServerResponse.createByError("查询记录为空");
    }
}

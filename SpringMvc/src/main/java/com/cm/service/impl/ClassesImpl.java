package com.cm.service.impl;
import com.cm.config.ServerResponse;
import com.cm.dao.ClassesDao;
import com.cm.model.Classes;
import com.cm.model.User;
import com.cm.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
@Service("classesService")
public class ClassesImpl implements ClassesService {
    @Autowired
    private ClassesDao classesDao;
    public ServerResponse addClass(String classId, String className) {
        Classes cla = new Classes();
        cla.setClassId(classId);
        cla.setClassName(className);
        Integer number=classesDao.addClass(cla);
        if (number > 0){
            return ServerResponse.createBySuccess("新增成功",number);
        }
        return ServerResponse.createByError("新增失败");
    }
    @Override
    public List<Classes> findAllClass(Map<String, Object> map) {
        try {
            return classesDao.findAllClass(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ServerResponse selectAllClass() {
        List<Classes> clas = classesDao.selectAllClass();
        if(clas != null) {
            return ServerResponse.createBySuccess("查询成功",clas);
        }
        return ServerResponse.createByError("查询记录为空");
    }
    public ServerResponse<Integer> updateClass(String classId, String className) {
        Classes cla=new Classes();
        cla.setClassId(classId);
        cla.setClassName(className);
        Integer number=classesDao.updateClass(cla);
        if (number > 0){
            return ServerResponse.createBySuccess("修改成功",number);
        }
        return ServerResponse.createByError("修改失败");
    }
    public ServerResponse deleteClass(String classId) {
        Classes cla=new Classes();
        cla.setClassId(classId);
        Integer deletenum=classesDao.deleteClass(cla);
        if (deletenum > 0){
            return ServerResponse.createBySuccess("删除成功",deletenum);
        }
        return ServerResponse.createByError("删除失败");
    }
    public ServerResponse findClassById(String classId) {
        Classes clas = classesDao.findClassById(classId);
        if(clas != null) {
            return ServerResponse.createBySuccess("查询成功",clas);
        }
        return ServerResponse.createByError("查询记录为空");
    }
}

package com.cm.service.impl;
import com.cm.config.ServerResponse;
import com.cm.dao.SubDao;
import com.cm.model.Classes;
import com.cm.model.Subject;
import com.cm.model.PageBean;
import com.cm.model.User;
import com.github.pagehelper.PageHelper;
import com.cm.service.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service("subService")
public class SubServiceImpl implements SubService {
    @Autowired
    private SubDao subDao;
    public ServerResponse addSubject(String subId, String subName) {
        Subject sub = new Subject();
        sub.setSubId(subId);
        sub.setSubName(subName);
        Integer number=subDao.addSubject(sub);
        if (number > 0){
            return ServerResponse.createBySuccess("新增成功",number);
        }
        return ServerResponse.createByError("新增失败");
    }
    @Override
    public List<Subject> findAllSub(Map<String, Object> map) {
        try {
            return subDao.findAllSub(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ServerResponse<Integer> updateSubject(String subId, String subName) {
        Subject subject=new Subject();
        subject.setSubId(subId);
        subject.setSubName(subName);
        Integer number=subDao.updateSubject(subject);
        if (number > 0){
            return ServerResponse.createBySuccess("修改成功",number);
        }
        return ServerResponse.createByError("修改失败");
    }
    public ServerResponse deleteSubject(String subId) {
        Subject sub=new Subject();
        sub.setSubId(subId);
        Integer deletenum=subDao.deleteSubject(sub);
        if (deletenum > 0){
            return ServerResponse.createBySuccess("删除成功",deletenum);
        }
        return ServerResponse.createByError("删除失败");
    }
    public ServerResponse findSubjectById(String subId) {
        Subject sub = subDao.findSubjectById(subId);
        if(sub != null) {
            return ServerResponse.createBySuccess("查询成功",sub);
        }
        return ServerResponse.createByError("查询记录为空");
    }
    public ServerResponse selectAllSub() {
        List<Subject> sub = subDao.selectAllSub();
        if(sub != null) {
            return ServerResponse.createBySuccess("查询成功",sub);
        }
        return ServerResponse.createByError("查询记录为空");
    }
}

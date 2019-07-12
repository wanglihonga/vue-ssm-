package com.cm.service.impl;
import com.cm.config.ServerResponse;
import com.cm.dao.myHomeworkDao;
import com.cm.model.Homework;
import com.cm.model.myHomework;
import com.cm.service.myHomeworkSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service("myHomework")
public class myHomeworkImpl implements myHomeworkSrevice {
    @Autowired
    private myHomeworkDao myhomeworkDao;
    public ServerResponse addmyHomework(String stuId, String stuName,String teacherName, Integer homeworkId, String subName,String className,String pushTime, String judgeState,String endTime, String commitTime,String uploadFile,String downloadFile,String classId,String commitState, Integer score, String teacherId) {
        myHomework mk = new myHomework();
        mk.setStuId(stuId);
        mk.setStuName(stuName);
        mk.setTeacherName(teacherName);
        mk.setHomeworkId(homeworkId);
        mk.setSubName(subName);
        mk.setClassName(className);
        mk.setPushTime(pushTime);
        mk.setJudgeState(judgeState);
        mk.setEndTime(endTime);
        mk.setCommitTime(commitTime);
        mk.setUploadFile(uploadFile);
        mk.setDownloadFile(downloadFile);
        mk.setClassId(classId);
        mk.setCommitState(commitState);
        mk.setScore(score);
        mk.setTeacherId(teacherId);
        Integer number=myhomeworkDao.addmyHomework(mk);
        if (number > 0){
            return ServerResponse.createBySuccess("新增成功",number);
        }
        return ServerResponse.createByError("新增失败");
    }
    @Override
    public List<myHomework> getMyHomeworkList(Map<String, Object> map) {
        try {
            return myhomeworkDao.getMyHomeworkList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<myHomework> findWorkByTeaId(Map<String, Object> map) {
        try {
            return myhomeworkDao.findWorkByTeaId(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ServerResponse findmyHomeworkById(Integer homeworkId) {
        myHomework clas = myhomeworkDao.findmyHomeworkById(homeworkId);
        if(clas != null) {
            return ServerResponse.createBySuccess("查询成功",clas);
        }
        return ServerResponse.createByError("查询记录为空");
    }
    public ServerResponse<Integer> giveScore(Integer score,Integer homeworkId) {
        myHomework cla=new myHomework();
        cla.setScore(score);
        cla.setHomeworkId(homeworkId);
        Integer number=myhomeworkDao.giveScore(cla);
        if (number > 0){
            return ServerResponse.createBySuccess("评分成功",number);
        }
        return ServerResponse.createByError("评分失败");
    }
}

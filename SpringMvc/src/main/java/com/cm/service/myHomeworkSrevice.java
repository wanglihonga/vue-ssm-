package com.cm.service;
import com.cm.config.ServerResponse;
import com.cm.model.myHomework;
import java.util.List;
import java.util.Map;

public interface myHomeworkSrevice {
    List<myHomework> getMyHomeworkList(Map<String, Object> map);
    List<myHomework> findWorkByTeaId(Map<String, Object> map);
    ServerResponse addmyHomework(String stuId, String stuName,String teacherName, Integer homeworkId, String subName,String className,String pushTime, String judgeState,String endTime, String commitTime,String uploadFile,String downloadFile,String classId,String commitState, Integer score, String teacherId);
    ServerResponse giveScore(Integer score, Integer homeworkId);
    ServerResponse findmyHomeworkById(Integer homeworkId);
}
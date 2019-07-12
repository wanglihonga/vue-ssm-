package com.cm.service;
import com.cm.config.ServerResponse;
import com.cm.model.Homework;
import java.util.List;
import java.util.Map;

public interface HomeworkService {
    ServerResponse addHomework(String teacherId, String subId, String pushTime,String fileSrc, String endTime, String classId);
    List<Homework> findAllHomework(Map<String, Object> map);
    ServerResponse deleteHomework(Integer id);
    ServerResponse updateHomework(Integer id, String endTime);
    ServerResponse findHomeworkById(Integer id);
    ServerResponse <List<Homework>>selectAllHomework();
}

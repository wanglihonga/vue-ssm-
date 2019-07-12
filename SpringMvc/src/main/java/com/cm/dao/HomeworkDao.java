package com.cm.dao;
import com.cm.model.Homework;
import java.util.List;
import java.util.Map;

public interface HomeworkDao {

    List<Homework> findAllHomework(Map<String, Object> map) throws Exception;
    List<Homework> selectAllHomework();
    Integer addHomework(Homework homework);
    Integer deleteHomework (Homework homework);
    Integer updateHomework (Homework homework);
    Homework findHomeworkById(Integer id);
}
package com.cm.dao;
import com.cm.model.Subject;
import java.util.List;
import java.util.Map;

public interface SubDao {
    List<Subject> findAllSub(Map<String, Object> map) throws Exception;
    List<Subject> selectAllSub();
    Integer addSubject(Subject subject);
    Integer deleteSubject (Subject subject);
    Integer updateSubject (Subject subject);
    Subject findSubjectById(String subId);
}
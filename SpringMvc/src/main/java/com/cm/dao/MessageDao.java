package com.cm.dao;
import com.cm.model.Message;
public interface MessageDao {
//    List<Subject> findAllSub(Map<String, Object> map) throws Exception;
//    List<Subject> selectAllSub();
    Integer addMessage(Message message);
//    Integer deleteSubject (Subject subject);
//    Integer updateSubject (Subject subject);
//    Subject findSubjectById(String subId);
}

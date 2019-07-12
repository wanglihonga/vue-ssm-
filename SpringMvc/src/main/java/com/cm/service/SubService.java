package com.cm.service;
import com.cm.model.Subject;
import com.cm.config.ServerResponse;
import com.cm.model.PageBean;
import java.util.List;
import java.util.Map;

public interface SubService {
    List<Subject> findAllSub(Map<String, Object> map);
    ServerResponse <List<Subject>>selectAllSub();
    ServerResponse addSubject(String subId, String subName);
    ServerResponse deleteSubject(String subId);
    ServerResponse updateSubject(String subId, String subName);
    ServerResponse findSubjectById(String subId);
}

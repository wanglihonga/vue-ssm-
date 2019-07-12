package com.cm.service;

import com.cm.config.ServerResponse;

public interface MsgService {
//    List<Subject> findAllSub(Map<String, Object> map);
//    ServerResponse <List<Subject>>selectAllSub();
    ServerResponse addMessage(String reporterId,String reporterName,String receiveId,String receiveName,String content);
//    ServerResponse deleteSubject(String subId);
//    ServerResponse updateSubject(String subId, String subName);
//    ServerResponse findSubjectById(String subId);
}
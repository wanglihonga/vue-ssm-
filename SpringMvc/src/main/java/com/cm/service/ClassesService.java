package com.cm.service;

import com.cm.config.ServerResponse;
import com.cm.model.Classes;

import java.util.List;
import java.util.Map;

public interface ClassesService {
    List<Classes> findAllClass(Map<String, Object> map);
    ServerResponse addClass(String classId, String className);
    ServerResponse deleteClass(String classId);
    ServerResponse updateClass(String classId, String className);
    ServerResponse findClassById(String classId);
    ServerResponse <List<Classes>>selectAllClass();
}

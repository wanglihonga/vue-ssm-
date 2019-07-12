package com.cm.dao;
import com.cm.model.Classes;
import java.util.List;
import java.util.Map;
public interface ClassesDao {
    List<Classes> findAllClass(Map<String, Object> map) throws Exception;
    List<Classes>selectAllClass();
    Integer addClass(Classes classes);
    Integer deleteClass (Classes classes);
    Integer updateClass (Classes classes);
    Classes findClassById(String classId);
}

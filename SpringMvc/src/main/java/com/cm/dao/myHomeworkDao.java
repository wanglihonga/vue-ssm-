package com.cm.dao;
import com.cm.model.myHomework;
import java.util.List;
import java.util.Map;

public interface myHomeworkDao {
        List<myHomework> getMyHomeworkList(Map<String, Object> map) throws Exception;
        List<myHomework> findWorkByTeaId(Map<String, Object> map) throws Exception;
        Integer addmyHomework(myHomework myhomework);
        Integer giveScore (myHomework myhomework);
        myHomework findmyHomeworkById(Integer homeworkId);

}

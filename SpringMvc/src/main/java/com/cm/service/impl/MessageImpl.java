package com.cm.service.impl;
import com.cm.config.ServerResponse;
import com.cm.dao.MessageDao;
import com.cm.model.Message;
import com.cm.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("msgService")
public class MessageImpl implements MsgService {
    @Autowired
    private MessageDao messageDao;
    public ServerResponse addMessage(String reporterId,String reporterName,String receiveId,String receiveName,String content) {
        Message ms = new Message();
        ms.setReporterId(reporterId);
        ms.setReporterName(reporterName);
        ms.setReceiveId(receiveId);
        ms.setReceiveName(receiveName);
        ms.setContent(content);
        Integer number=messageDao.addMessage(ms);
        if (number > 0){
            return ServerResponse.createBySuccess("新增成功",number);
        }
        return ServerResponse.createByError("新增失败");
    }
//    @Override
//    public List<Subject> findAllSub(Map<String, Object> map) {
//        try {
//            return subDao.findAllSub(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public ServerResponse<Integer> updateSubject(String subId, String subName) {
//        Subject subject=new Subject();
//        subject.setSubId(subId);
//        subject.setSubName(subName);
//        Integer number=subDao.updateSubject(subject);
//        if (number > 0){
//            return ServerResponse.createBySuccess("修改成功",number);
//        }
//        return ServerResponse.createByError("修改失败");
//    }
//    public ServerResponse deleteSubject(String subId) {
//        Subject sub=new Subject();
//        sub.setSubId(subId);
//        Integer deletenum=subDao.deleteSubject(sub);
//        if (deletenum > 0){
//            return ServerResponse.createBySuccess("删除成功",deletenum);
//        }
//        return ServerResponse.createByError("删除失败");
//    }
//    public ServerResponse findSubjectById(String subId) {
//        Subject sub = subDao.findSubjectById(subId);
//        if(sub != null) {
//            return ServerResponse.createBySuccess("查询成功",sub);
//        }
//        return ServerResponse.createByError("查询记录为空");
//    }
//    public ServerResponse selectAllSub() {
//        List<Subject> sub = subDao.selectAllSub();
//        if(sub != null) {
//            return ServerResponse.createBySuccess("查询成功",sub);
//        }
//        return ServerResponse.createByError("查询记录为空");
//    }
}

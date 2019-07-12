package com.cm.model;

public class Homework {
//    public Subject getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }
//
//    public Classes getClasses() {
//        return classes;
//    }
//
//    public void setClasses(Classes classes) {
//        this.classes = classes;
//    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    private User user;
    private Classes classes;
    private Subject subject;
//    String homeworkId;
    String TeacherId;
    String userId;
    String commitState;
    String pushTime;
    String endTime;
    String subId;
    String judgeState;
    String fileSrc;
    String classId;
    String subName;
    String userName;
    String className;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getFileSrc() {
        return fileSrc;
    }

    public void setFileSrc(String fileSrc) {
        this.fileSrc = fileSrc;
    }

//    public String getHomeworkId() {
//        return homeworkId;
//    }
//
//    public void setHomeworkId(String homeworkId) {
//        this.homeworkId = homeworkId;
//    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommitState() {
        return commitState;
    }

    public void setCommitState(String commitState) {
        this.commitState = commitState;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getJudgeState() {
        return judgeState;
    }

    public void setJudgeState(String judgeState) {
        this.judgeState = judgeState;
    }
}

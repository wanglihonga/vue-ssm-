package com.cm.model;

public class myHomework {
    int id;
    int homeworkId;
    String stuId;
    String stuName;
    String className;
    String classId;
    String teacherId;
    String subName;
    String judgeState;
    String commitState;
    String endTime;
    String pushTime;
    String uploadFile;
    String downloadFile;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    int score;
    String commitTime;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    String teacherName;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getJudgeState() {
        return judgeState;
    }

    public void setJudgeState(String judgeState) {
        this.judgeState = judgeState;
    }

    public String getCommitState() {
        return commitState;
    }

    public void setCommitState(String commitState) {
        this.commitState = commitState;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(String downloadFile) {
        this.downloadFile = downloadFile;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }
}

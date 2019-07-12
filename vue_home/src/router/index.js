import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login'
import Home from '@/views/Home/home'
import StudentList from '@/views/user/student/studentList'
import TeacherList from '@/views/user/teacher/TeacherList'
import SubjectList from '@/views/subject/list'
import ClassesList from '@/views/classes/classList'
import HomeworkList from '@/views/homework/homeworkList'
import AddHomework from '@/views/homework/addHomework'
import JudgeHomework from '@/views/homework/judgeHomework'
import PersonalInfo from '@/views/personal/personalInfo'
import MyHomework from '@/views/personal/myHomework'
import MyMessage from '@/views/personal/myMessage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Login
    }, {
      // 主页
      path: '/home',
      component: Home,
      name: '用户管理',
      iconCls: 'team',
      userType: '0',
      children: [{
        path: '/teacherList',
        component: TeacherList,
        name: '教师管理',
        meta: {
          title: '教师管理'
        },
        hidden: true
      }, {
        path: '/studentList',
        component: StudentList,
        name: '学生管理',
        meta: {
          title: '学生管理'
        },
        hidden: true
      }]
    }, {
      // 主页
      path: '/home',
      component: Home,
      name: '科目管理',
      iconCls: 'read',
      userType: '0',
      children: [{
        path: '/SubjectList',
        component: SubjectList,
        name: '科目管理',
        meta: {
          title: '科目管理'
        },
        hidden: true
      }]
    }, {
      // 主页
      path: '/home',
      component: Home,
      name: '班级管理',
      iconCls: 'home',
      userType: '0',
      children: [{
        path: '/ClassesList',
        component: ClassesList,
        name: '班级管理',
        meta: {
          title: '班级管理'
        },
        hidden: true
      }]
    }, {
      // 主页
      path: '/home',
      component: Home,
      name: '作业管理',
      iconCls: 'contacts',
      userType: '1',
      children: [{
        path: '/HomeworkList',
        component: HomeworkList,
        name: '作业管理',
        meta: {
          title: '作业管理'
        },
        hidden: true
      }, {
        path: '/addHomework',
        component: AddHomework,
        name: '发布作业',
        meta: {
          title: '发布作业'
        },
        hidden: false
      }, {
        path: '/judgeHomework',
        component: JudgeHomework,
        name: '批改作业',
        meta: {
          title: '批改作业'
        },
        hidden: false
      }]
    }, {
      // 主页
      path: '/home',
      component: Home,
      name: '个人中心',
      iconCls: 'user',
      children: [{
        path: '/personalInfo',
        component: PersonalInfo,
        name: '基本信息',
        meta: {
          title: '基本信息'
        },
        hidden: true
      }]
    }, {
      // 主页
      path: '/home',
      component: Home,
      name: '我的作业',
      iconCls: 'contacts',
      userType: '2',
      children: [{
        path: '/myHomework',
        component: MyHomework,
        name: '我的作业',
        meta: {
          title: '我的作业'
        },
        hidden: true
      }]
    }
    // , {
    //   // 主页
    //   path: '/home',
    //   component: Home,
    //   name: '我的消息',
    //   iconCls: 'message',
    //   children: [{
    //     path: '/myMessage',
    //     component: MyMessage,
    //     name: '我的消息',
    //     meta: {
    //       title: '我的消息'
    //     },
    //     hidden: true
    //   }]
    // }

    // {
    //   path: '/',
    //   name: 'Login',
    //   component: Login
    // },
    // {
    //   path: '/home',
    //   name: 'Home',
    //   component: Home
    // }
  ]
})

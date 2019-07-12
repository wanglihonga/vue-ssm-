<template>
  <div class="loginTxt layout">
    <div class="login">
      <el-form :model="loginForm" status-icon :rules="rules2" ref="loginForms" label-width="100px" class="loginForm" size="medium ">
        <div class="loginDiv">
          <h2 style="margin-bottom: 24px">作业提交与批改系统</h2>
          <el-form-item prop="userId">
            <el-input type="text" size="medium" v-model="loginForm.userId" placeholder="请输入账号" auto-complete="off" style="width:100%;height: 20px">
            <span slot="prefix" class="svg-container">
              <svg-icon icon-class="user" />
            </span>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" size="medium" v-model="loginForm.password" placeholder="请输入密码" auto-complete="off">
            <span slot="prefix" class="svg-container">
              <svg-icon icon-class="password" />
            </span>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-radio-group v-model="loginForm.userType">
              <el-radio :label="0" @change="changeType()">管理员</el-radio>
              <el-radio :label="1" @change="changeType()">教师</el-radio>
              <el-radio :label="2" @change="changeType()">学生</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%;" @click="loginClick('loginForms')">登录</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { findByUserId } from '@/api/users'
import qs from 'qs'
export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'))
      } else {
        if (this.loginForm.password !== '') {
          this.$refs.loginForms.validateField('password')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        userId: '',
        password: '',
        userType: 0
      },
      rules2: {
        userId: [
          { validator: validatePass, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      userData: []
    }
  },
  created () {
  },
  methods: {
    changeType () {
      console.log('222', this.loginForm.userType)
    },
    loginClick (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = {
            userId: this.loginForm.userId
          }
          findByUserId(qs.stringify(params)).then(res => {
            this.userData = res.data
            localStorage.setItem('classId', res.data.classId)
            localStorage.setItem('userName', res.data.userName)
            localStorage.setItem('photo', res.data.photo)
            console.log('===', this.userData)
            if (this.userData && this.userData.password === this.loginForm.password && this.userData && this.userData.userType === this.loginForm.userType) {
              this.$message({
                message: '登录成功',
                type: 'success'
              })
              localStorage.setItem('userId', this.loginForm.userId)
              localStorage.setItem('oldPwd', this.loginForm.password)
              localStorage.setItem('userType', this.loginForm.userType)
              this.$router.push({
                path: '/home'
              })
            } else {
              this.$message({
                message: '账号或密码错误',
                type: 'error'
              })
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
    // resetForm (formName) {
    //   this.$refs[formName].resetFields()
    // }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style rel="stylesheet/scss" lang="scss" scoped>
  $dark_gray:#889aa4;
  .layout {
    position: fixed;
    height: 100%;
    width: 100%;
    background: url("../assets/bg.jpg") no-repeat;
    background-size: 100% 100%;
  }
.login {
  border-radius: 10px;
  width: 380px;
  height: 360px;
  margin: 120px auto;
  background-color: #fff;
  position: relative;
}
  .loginForm {
    position: absolute;
    width: 90%;
    height: 90%;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    padding: 5%;
  }
.loginDiv {
  width: 85%;
  margin: 0 auto;
  text-align: center;
  position: relative;
  top: 3%;
  height: 70%;
}
.svg-container {
  /*padding: 6px 5px 6px 15px;*/
  /*color: #eee;*/
  color: $dark_gray;
  vertical-align: middle;
  width: 30px;
  display: inline-block;
}
</style>
<style>
  .el-input--prefix .el-input__inner {
    padding-left: 40px!important;
  }
  .el-input--medium .el-input__inner {
    height: 40px;
    line-height: 40px;
  }
</style>

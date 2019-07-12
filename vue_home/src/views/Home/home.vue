<template>
  <div>
    <div class="header" :style="{height: pageHeight * 0.1 + 'px'}">
      <img class="logo" src="../../assets/logo.png"/>
      <div class="userHead">
        <svg-icon class="icon" style="fill: red" icon-class="sound" fill="red" />
        <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          欢迎您，{{ userName }}
          <img v-if="photo !== 'null'" class="headImg" :src="$imgUrl + photo" />
          <img v-else class="headImg" src="../../assets/user/pic_head@2x.png" />
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="b">重置密码</el-dropdown-item>
            <el-dropdown-item command="c">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <el-container :style="{height: pageHeight * 0.9 + 'px'}">
      <el-aside width="230px" style="background-color: rgb(84, 92, 100)">
        <el-menu
          default-active="1"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          class="el-menu-vertical-demo"
          unique-opened
          router>
          <div v-for="(route, index) in $router.options.routes" v-if="$router.options.routes && route.children" :key="index">
            <el-submenu v-if="route.name && (route.userType === userType.toString() || !route.userType)" :index="(index + 1).toString()">
              <template>
                <template slot="title" v-if="route.name">
                  <svg-icon class="icon" :icon-class="route.iconCls" />
                  <span>{{ route.name }}</span>
                </template>
                <template v-for="item in route.children"  v-if="route.children && route.children.length">
                  <el-menu-item
                    :key="route.path + '/' + item.path"
                    :index="item.path"
                  >
                    <span slot="title">{{ item.name }}</span>
                  </el-menu-item>
                </template>
              </template>
            </el-submenu>
          </div>
          <!--<template v-for="route in $router.options.routes" v-if="route.children && route.children.length">-->
          <!--<template v-for="item in route.children" >-->
          <!--<el-menu-item-->
          <!--:key="route.path + '/' + item.path"-->
          <!--:index="item.path"-->
          <!--&gt;-->
          <!--<i class="el-icon-menu"></i>-->
          <!--<span slot="title">{{ item.name }}</span>-->
          <!--</el-menu-item>-->
          <!--</template>-->
          <!--</template>-->
          <!--<el-submenu index="1">-->
          <!--<template slot="title">-->
          <!--<i class="el-icon-message"></i>-->
          <!--<span>用户管理</span>-->
          <!--</template>-->
          <!--<el-menu-item-group>-->
          <!--<template slot="title"></template>-->
          <!--<el-menu-item index="/userList">教师管理</el-menu-item>-->
          <!--</el-menu-item-group>-->
          <!--</el-submenu>-->
          <!--<el-submenu index="2">-->
          <!--<template slot="title">-->
          <!--<i class="el-icon-message"></i>-->
          <!--<span>课程管理</span>-->
          <!--</template>-->
          <!--<el-menu-item-group>-->
          <!--<template slot="title"></template>-->
          <!--<el-menu-item index="/userList">用户列表</el-menu-item>-->
          <!--</el-menu-item-group>-->
          <!--</el-submenu>-->
          <!--<el-submenu index="3">-->
          <!--<template slot="title">-->
          <!--<i class="el-icon-menu"></i>-->
          <!--<span>个人中心</span>-->
          <!--</template>-->
          <!--<el-menu-item-group>-->
          <!--<template slot="title"></template>-->
          <!--<el-menu-item index="/personalInfo">基本信息</el-menu-item>-->
          <!--</el-menu-item-group>-->
          <!--<el-menu-item-group>-->
          <!--<template slot="title"></template>-->
          <!--<el-menu-item index="/resetPwd">我的作业</el-menu-item>-->
          <!--</el-menu-item-group>-->
          <!--</el-submenu>-->
        </el-menu>
      </el-aside>
      <el-container>
        <BreadLayout />
        <el-main>
          <div v-if="$route.fullPath === '/home'">欢迎使用作业提交批改系统</div>
          <transition v-else name="fade" mode="out-in">
            <router-view></router-view>
          </transition>
        </el-main>
        <el-footer class="footer">
          2019&nbsp;North China Electric Power University科技学院 <br/>
          地址：河北省保定市瑞祥大街282号华北电力大学科技学院。邮编：071000
        </el-footer>
      </el-container>
    </el-container>
    <el-dialog title="重置密码" :visible.sync="dialogFormVisible" :modal-append-to-body='upShow' :before-close="cancleUpdate">
      <el-form :model="form" :rules="rules2" ref="form" label-width="90px">
        <el-form-item label="旧密码" prop="oldPass">
          <el-input type="password" v-model="form.oldPass" auto-complete="off" maxlength="8"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input type="password" v-model="form.newPass" auto-complete="off" maxlength="8"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="form.checkPass" auto-complete="off" maxlength="8"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleUpdate('form')">取 消</el-button>
        <el-button type="primary" @click="resetPwd('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import BreadLayout from '../../components/Breadcrumb/index'
import { updatePwd } from '@/api/users'
export default {
  name: 'layout',
  components: {
    BreadLayout
  },
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.newPass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      dialogFormVisible: false,
      upShow: false,
      formLabelWidth: '120px',
      form: {
        oldPass: '',
        newPass: '',
        checkPass: ''
      },
      rules2: {
        oldPass: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPass: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ]
      },
      photo: localStorage.getItem('photo'),
      userName: localStorage.getItem('userName')
    }
  },
  created () {
    this.pageHeight = document.documentElement.clientHeight
    this.userType = localStorage.getItem('userType')
    console.log('this.userType', this.userType)
  },
  methods: {
    handleCommand (command) {
      if (command === 'c') {
        localStorage.clear()
        this.$router.push({
          path: '/'
        })
      } else if (command === 'b') {
        this.dialogFormVisible = true
        console.log('00000', this.dialogFormVisible)
      }
    },
    // 清空
    Empty () {
      this.form.oldPass = ''
      this.form.newPass = ''
      this.form.checkPass = ''
    },
    resetPwd (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const params = {
            userId: localStorage.getItem('userId'),
            oldPwd: this.form.oldPass,
            newPwd: this.form.newPass
          }
          updatePwd(this.$qs.stringify(params)).then(res => {
            if (res && res.data === 1) {
              this.$message({
                message: '修改成功',
                type: 'success'
              })
            } else {
              this.$message({
                message: '修改失败',
                type: 'error'
              })
            }
          })
          this.dialogFormVisible = false
          this.Empty()
          this.$refs[formName].resetFields()
        } else {
          console.log('error submit!!')
          this.Empty()
          return false
        }
      })
    },
    cancleUpdate (formName) {
      this.dialogFormVisible = false
      this.Empty()
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style scoped>
  .logo {
    width: 300px;
    height: 56px;
    margin-left: 24px;
  }
  .slideBar {
    width: 250px;
    height: 100%;
  }
  .icon {
    width: 25px;
    height: 25px;
    margin-right: 8px;
    vertical-align: middle;
  }
  .header {
    width: 100%;
    height: 56px;
    background-color:#fff;
    -moz-box-shadow: 0 3px 3px #eee; /* 老的 Firefox */
    box-shadow: 0 3px 3px #eee;
  }
  .userHead {
    float: right;
    line-height: 56px;
    margin-right: 40px;
  }
  .headImg {
    width: 40px;
    height: 40px;
    vertical-align: middle;
    margin-left: 16px;
  }
  .footer {
    position: relative;
    font-size: 14px;
    color: gray;
    text-align: center;
    border-top: 1px solid #ddd;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
  }
</style>

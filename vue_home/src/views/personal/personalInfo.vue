<template>
  <div>
    <el-card class="box-card" :body-style="{ width: '80%', margin: '0 auto'}">
      <div slot="header" class="clearfix">
        <span>基本信息</span>
        <!--<el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
      </div>
      <div>
        <el-row>
          <el-col :span="4"><div class="grid-content">头像</div></el-col>
          <el-col :span="12">
            <div class="grid-content">
              <el-upload
                class="avatar-uploader"
                ref="upload"
                action="http://localhost:8080/file/uploadImg"
                name="picture"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <div v-if="imageUrl">
                  <img :src="$imgUrl + imageUrl" class="avatar">
                  <el-button type="primary" size="small" icon="el-icon-upload">上传头像</el-button>
                </div>
                <div v-else>
                  <img class="avatar" src="../../assets/user/pic_head@2x.png" />
                  <el-button type="primary" size="small" icon="el-icon-upload">上传头像</el-button>
                </div>
              </el-upload>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><div class="grid-content">账号</div></el-col>
          <el-col :span="7"><div class="grid-content">{{ userInfo.userId }}</div></el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><div class="grid-content">姓名</div></el-col>
          <el-col :span="7"><div class="grid-content">{{ userInfo.userName }}</div></el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><div class="grid-content">身份</div></el-col>
          <el-col :span="7"><div class="grid-content">{{ userInfo.userType === 0 ? '管理员' : userInfo.userType === 1 ? '教师' : '学生' }}</div></el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><div class="grid-content">联系方式</div></el-col>
          <el-col :span="7" v-if="showInput">
            <div class="grid-content">
              <el-input v-model="userInfo.phone" placeholder="请输入内容" @blur="savePhone"></el-input>
            </div>
          </el-col>
          <el-col :span="7" v-else><div class="grid-content">{{ userInfo.phone === null ? '暂无': userInfo.phone }}</div></el-col>
          <el-col :span="2" v-if="!showInput"><div class="grid-content"><i class="el-icon-edit-outline" @click="editPhone"></i></div></el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { findByUserId, updatePhone, uploadPhoto } from '@/api/users'
export default {
  inject: ['reload'], // 引入方法
  name: 'personal-info',
  data () {
    return {
      userInfo: {
        phone: '',
        photo: ''
      },
      showInput: false,
      imageUrl: ''
    }
  },
  created () {
    this.getUserInfo()
  },
  methods: {
    getUserInfo () {
      const params = {
        userId: localStorage.getItem('userId')
      }
      findByUserId(this.$qs.stringify(params)).then(res => {
        this.userInfo = res.data
        this.imageUrl = this.userInfo.photo
        localStorage.setItem('photo', this.userInfo.photo)
      })
    },
    editPhone () {
      localStorage.setItem('oldPhone', this.userInfo.phone)
      this.showInput = true
    },
    handleAvatarSuccess (res, file) {
      if (file.response.success) {
        this.userInfo.photo = file.response.message // 将返回的文件储存路径赋值picture字段
        localStorage.setItem('photo', file.response.message)
      }
      this.imageUrl = URL.createObjectURL(file.raw)
      const params = {
        userId: localStorage.getItem('userId'),
        photo: this.userInfo.photo
      }
      console.log('params = ,', params)
      uploadPhoto(this.$qs.stringify(params)).then(res => {
        if (res && res.data === 1) {
          this.$message({
            message: '上传成功',
            type: 'success'
          })
          this.getUserInfo()
          this.reload() // 调用方法
        } else {
          this.$message({
            message: '上传失败',
            type: 'error'
          })
        }
      })
      console.log(this.imageUrl, file)
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    savePhone () {
      if (this.userInfo.phone === '') {
        this.$message({
          message: '请输入手机号！',
          type: 'warning'
        })
      } else if (!(/^1[345789]\d{9}$/.test(this.userInfo.phone))) {
        this.$message({
          message: '手机号格式有误！',
          type: 'warning'
        })
        return false
      } else {
        if (localStorage.getItem('oldPhone') !== this.userInfo.phone) {
          const params = {
            userId: localStorage.getItem('userId'),
            newPhone: this.userInfo.phone
          }
          console.log('params = ,', params)
          updatePhone(this.$qs.stringify(params)).then(res => {
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
          this.showInput = false
          console.log(this.showInput)
        }
      }
    }
  }
}
</script>

<style scoped lang="scss">
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 95%;
    margin: 0 auto;
  }
  .el-row {
    margin-bottom: 20px;
  &:last-child {
     margin-bottom: 0;
   }
  }
  .el-col {
    border-radius: 4px;
  }
  .grid-content {
    height: 50px;
    line-height: 50px;
    border-radius: 4px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 50px;
    height: 50px;
    vertical-align: middle;
    margin-right: 36px;
  }
</style>

<template>
    <div>
      <div class="addTopBox">{{this.$route.query.id ? (this.$route.query.look ? '作业详情': '编辑作业') : '发布作业'}}</div>
      <div class="addBottomBox">
        <el-form :model="addform" :rules="this.$route.query.look || this.$route.query.edit  ? {} : rules" ref="addform" label-width="150px">
          <el-card>
            <el-form-item v-if="this.$route.query.look || this.$route.query.edit" label="科目：">
              <el-select v-model="addform.subName" placeholder="请选择科目" :disabled="this.$route.query.look || this.$route.query.edit ? true : false">
                <el-option
                  v-for="item in subList"
                  :key="item.subId"
                  :label="item.subName"
                  :value="item.subId">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-else label="科目：" prop="subId">
              <el-select v-model="addform.subId" placeholder="请选择科目" :disabled="this.$route.query.look || this.$route.query.edit ? true : false">
                <el-option
                  v-for="item in subList"
                  :key="item.subId"
                  :label="item.subName"
                  :value="item.subId">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-if="this.$route.query.look || this.$route.query.edit" label="班级：">
              <el-select v-model="addform.className" placeholder="请选择班级" :disabled="this.$route.query.look || this.$route.query.edit ? true : false">
                <el-option
                  v-for="item in classList"
                  :key="item.classId"
                  :label="item.className"
                  :value="item.classId">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-else label="班级：" prop="classId">
              <el-select v-model="addform.classId" placeholder="请选择班级" :disabled="this.$route.query.look || this.$route.query.edit ? true : false">
                <el-option
                  v-for="item in classList"
                  :key="item.classId"
                  :label="item.className"
                  :value="item.classId">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="发布时间：">
              {{ $moment(addform.pushTime).format('YYYY-MM-DD HH:mm') }}
            </el-form-item>
            <el-form-item label="截止时间：">
              <el-date-picker
                v-model="addform.endTime"
                type="datetime"
                format="yyyy-MM-dd HH:mm"
                :disabled="this.$route.query.look ? true : false"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
            <!--<el-form-item v-else label="截止时间：">-->
              <!--{{ $moment(addform.endTime * 1000).format('YYYY-MM-DD HH:mm') }}-->
            <!--</el-form-item>-->
            <el-form-item label="作业附件：" v-if="this.$route.query.look || this.$route.query.edit">
              <a class='link-type' :download="file.name" @click="download(file.name)">{{ file.name }}<el-button type="primary" size="mini" icon="el-icon-download" style="margin-left: 16px">下载</el-button></a>
            </el-form-item>
            <el-form-item v-else label="作业附件：">
              <fileButton type="FILE" @successFile="getFile" />
              <div>{{ file.name }}</div>
            </el-form-item>
          </el-card>
        </el-form>
      </div>
      <div class="back">
        <el-button v-if="!this.$route.query.look" size="small" class="backBtn" @click="submitForm('addform')">提交</el-button>
        <el-button size="small" class="backBtn" @click="back">返回</el-button>
      </div>
    </div>
</template>

<script>
import { selectAllClass } from '@/api/classes'
import { selectAllSub } from '@/api/subjects'
import { downloadFile } from '@/api/upload'
import { addHomework, findHomeworkById, updateHomework } from '@/api/homework'
import fileButton from '../../components/fileButton'
export default {
  name: 'add-homework',
  components: {
    fileButton
  },
  data () {
    var date = new Date()
    // var year = date.getFullYear()
    // var month = date.getMonth() + 1
    // var day = date.getDate()
    // var hour = date.getHours()
    // var minute = date.getMinutes()
    return {
      classList: [],
      subList: [],
      file: {
        name: ''
      },
      src: '',
      addform: {
        subId: '',
        classId: '',
        subName: '',
        className: '',
        // pushTime: year + '-' + month + '-' + day + ' ' + hour + ':' + minute,
        pushTime: date,
        fileSrc: '',
        judgeState: '0'
      },
      rules: {
        subId: [
          { required: true, message: '请选择科目', trigger: 'change' }
        ],
        classId: [
          { required: true, message: '请选择班级', trigger: 'change' }
        ]
      }
    }
  },
  created () {
    if (this.$route.query.id || this.$route.query.edit) {
      this.getHomeworkDetails()
    } else {
      this.getAllClasses()
      this.getAllSub()
    }
  },
  methods: {
    getAllSub () {
      selectAllSub(this.$qs.stringify()).then(res => {
        this.subList = res.data
        console.log('subList==', this.subList)
      })
    },
    getAllClasses () {
      selectAllClass(this.$qs.stringify()).then(res => {
        this.classList = res.data
        console.log('classList==', this.classList)
      })
    },
    getHomeworkDetails () {
      const params = {
        id: Number(this.$route.query.id)
      }
      findHomeworkById(this.$qs.stringify(params)).then(res => {
        this.addform = res.data
        // this.addform.endTime = res.data.endTime
        // this.addform.pushTime = res.data.pushTime
        this.file.name = res.data.fileSrc
        // this.select = this.addform.userId.substring(0, 4)
        // this.studentId = this.addform.classId.substring(5, 9)
      })
      this.addVisible = true
    },
    // 下载附件
    download (fileName) {
      const params = {
        fileName: fileName
      }
      downloadFile(this.$qs.stringify(params)).then(res => {
        const content = res
        const blob = new Blob([content])
        if ('download' in document.createElement('a')) { // 非IE下载
          const elink = document.createElement('a')
          elink.download = fileName
          elink.style.display = 'none'
          elink.href = URL.createObjectURL(blob)
          document.body.appendChild(elink)
          elink.click()
          URL.revokeObjectURL(elink.href) // 释放URL 对象
          document.body.removeChild(elink)
        } else { // IE10+下载
          navigator.msSaveBlob(blob, fileName)
        }
      })
    },
    back () {
      this.$router.go(-1)
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.file.name)
          if (!this.file.name) {
            this.$message({
              message: '请选择附件',
              type: 'warning'
            })
            return
          } else if (!this.addform.endTime) {
            this.$message({
              message: '请选择截止时间',
              type: 'warning'
            })
            return
          }
          if (this.$route.query.edit) {
            const par = {
              id: Number(this.$route.query.id),
              // endTime: this.$moment(this.addform.endTime).format('YYYY-MM-DD HH:mm')
              endTime: new Date(this.addform.endTime)
            }
            updateHomework(this.$qs.stringify(par)).then(res => {
              if (res && res.data === 1) {
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
                this.$refs[formName].resetFields()
                this.$router.push({
                  path: '/homeworkList'
                })
              } else {
                this.$message({
                  message: '修改失败',
                  type: 'error'
                })
              }
            })
          } else {
            const params = {
              teacherId: localStorage.getItem('userId'),
              subId: this.addform.subId,
              pushTime: new Date(this.addform.pushTime),
              judgeState: this.addform.judgeState,
              fileSrc: this.file.name,
              endTime: new Date(this.addform.endTime),
              classId: this.addform.classId
            }
            console.log('params', params)
            let param = new FormData() // 创建form对象
            param.append('file', this.file)// 通过append向form对象添加数据
            console.log(param.get('file')) // FormData私有类对象，访问不到，可以通过get判断值是否传进去
            let config = {
              headers: {'Content-Type': 'multipart/form-data'}
            } // 添加请求头
            this.$http.post('/file/upload', param, config)
              .then(response => {
                console.log('上传成功')
                addHomework(this.$qs.stringify(params)).then(res => {
                  if (res && res.data === 1) {
                    this.$message({
                      message: '新增成功',
                      type: 'success'
                    })
                    // this.getUsers(0)
                    this.addVisible = false
                  } else {
                    this.$message({
                      message: '新增失败',
                      type: 'error'
                    })
                  }
                  this.$router.push({
                    path: '/homeworkList'
                  })
                })
              }).catch((error) => {
                this.$message({
                  message: error,
                  type: 'error'
                })
              })
          }
        } else {
          console.log('失败')
          return false
        }
        this.$refs[formName].resetFields()
      })
    },
    getFile (event) {
      this.file = event.target.files[0]
      console.log(this.file)
    }
  }
}
</script>

<style scoped>
  .addTopBox{
    padding: 0 8px 0 24px;
    font-size: 24px;
    font-weight: bold;
  }
   .addBottomBox{
     margin: 20px;
     width: 96%;
   }
  .back {
    text-align: center;
  }
</style>

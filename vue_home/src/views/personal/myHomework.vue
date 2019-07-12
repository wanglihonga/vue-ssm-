<template>
  <div>
    <el-form :inline="true" :model="formData" class="demo-form-inline" size="small">
      <el-form-item label="搜索:">
        <el-input v-model="formData.className" type="text" placeholder="请输入科目" style="width:150px;"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getHomework(0)">搜索</el-button>
        <el-button type="primary" @click="Empty">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      border
      :header-cell-style="tableHeaderBG"
      :cell-style="cellStyle"
      style="width: 100%;border:1px solid #eee;">
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column
        label="科目"
        width="80">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.subName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="班级"
        width="150">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.className }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="教师姓名"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="发布时间"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ $moment(new Date(scope.row.pushTime)).format('YYYY-MM-DD HH:mm') }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="截止时间"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ $moment(new Date(scope.row.endTime)).format('YYYY-MM-DD HH:mm') }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="附件"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.fileSrc }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="280">
        <template slot-scope="scope">
          <el-button
            class="operateBtn"
            size="mini"
            @click="lookDetails(scope.row.id)">查看</el-button>
          <el-button
            class="operateBtn"
            type="primary"
            size="mini"
            icon="el-icon-download"
            @click="download(scope.row.fileSrc)">下载</el-button>
          <el-button
            class="operateBtn"
            size="mini"
            @click="uploadHomework(scope.row.id)">上传作业</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      :total="total"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"/>
    <el-dialog title="上传作业" :visible.sync="addVisible" :modal-append-to-body='upShow' :show-close="false">
      <el-form  label-width="150px">
        <el-form-item label="科目：">
          {{ homeData.subName }}
        </el-form-item>
        <el-form-item label="班级：">
          {{ homeData.className }}
        </el-form-item>
        <el-form-item label="教师：">
          {{ homeData.userName }}
        </el-form-item>
        <el-form-item label="发布时间：">
          {{ $moment(new Date(homeData.pushTime)).format('YYYY-MM-DD HH:mm') }}
        </el-form-item>
        <el-form-item label="截止时间：">
          {{ $moment(new Date(homeData.endTime)).format('YYYY-MM-DD HH:mm') }}
        </el-form-item>
        <el-form-item label="作业附件：" v-if="this.$route.query.look || this.$route.query.edit">
          <div>{{ file.name }}</div>
        </el-form-item>
        <el-form-item v-else label="上传作业：">
          <fileButton type="FILE" @successFile="getFile" />
          <div>{{ file.name }}</div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleAdd()">取 消</el-button>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="作业详情" :visible.sync="showDetails" :modal-append-to-body='upShow' style="width: 80%; margin: 0 auto">
      <el-row :gutter="20" class="rowsStyle">
        <el-col :span="12"><div>科目：{{ homeDetails && homeDetails.subName }}</div></el-col>
        <el-col :span="12"><div>教师：{{ homeDetails && homeDetails. teacherName}}</div></el-col>
      </el-row>
      <el-row :gutter="20" class="rowsStyle">
        <el-col :span="12"><div>发布时间：{{ homeDetails && $moment(homeDetails.pushTime).format('YYYY-MM-DD HH:mm') }}</div></el-col>
        <el-col :span="12"><div>截止时间：{{ homeDetails && $moment(homeDetails.endTime).format('YYYY-MM-DD HH:mm') }}</div></el-col>
      </el-row>
      <el-row :gutter="20" class="rowsStyle">
        <el-col :span="12"><div>作业附件：<a class='link-type' href="#" :download="homeDetails && homeDetails.downloadFile ? homeDetails.downloadFile : homeDetails && homeDetails.fileSrc" @click="download(homeDetails && homeDetails.downloadFile ? homeDetails.downloadFile : homeDetails && homeDetails.fileSrc)">{{ homeDetails && homeDetails.downloadFile ? homeDetails.downloadFile : homeDetails && homeDetails.fileSrc }}</a></div></el-col>
        <el-col :span="12"><div>提交附件：<a class='link-type' href="#" :download="homeDetails && homeDetails.uploadFile ? homeDetails.uploadFile : '未提交'" @click="homeDetails && homeDetails.uploadFile ? download(scope.row.fileSrc): ''">{{ homeDetails && homeDetails.uploadFile ? homeDetails.uploadFile : '未提交' }}</a></div></el-col>
      </el-row>
      <el-row :gutter="20" class="rowsStyle">
        <el-col :span="12"><div>批改状态：{{ homeDetails && homeDetails.judgeState ? homeDetails.judgeState : '未批改' }}</div></el-col>
        <el-col :span="12"><div>成绩：{{ homeDetails && homeDetails.judgeState &&  homeDetails.score ? homeDetails.score : '暂无' }}</div></el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import fileButton from '../../components/fileButton'
import { getMyHomeworkList, addmyHomework, findHomeworkById, findmyHomeworkById } from '@/api/homework'
import { downloadFile } from '@/api/upload'
export default {
  name: 'my-homework',
  components: {
    fileButton
  },
  data () {
    return {
      file: {
        name: ''
      },
      src: '',
      formData: {
        className: ''
      },
      tableData: [],
      page: 1,
      limit: 10,
      total: 0,
      upShow: false,
      addVisible: false,
      homeData: [],
      homeDetails: [],
      showDetails: false,
      filesrc: 'localhost:8080/upload/'
    }
  },
  created () {
    this.getHomework()
  },
  methods: {
    addHomework () {

    },
    getFile (event) {
      this.file = event.target.files[0]
      console.log(this.file)
    },
    lookDetails (id) {
      this.showDetails = true
      const params = {
        homeworkId: Number(id)
      }
      findmyHomeworkById(this.$qs.stringify(params)).then(res => {
        this.homeDetails = res.data
        if (this.homeDetails && this.homeDetails.commitState) {
          const params = {
            homeworkId: Number(id)
          }
          findmyHomeworkById(this.$qs.stringify(params)).then(res => {
            this.homeDetails = res.data
            console.log('this.homeDetails =', this.homeDetails)
            // this.$message({
            //   message: '获取成功',
            //   type: 'w'
            // })
          })
        } else {
          const params = {
            id: id
          }
          findHomeworkById(this.$qs.stringify(params)).then(res => {
            this.homeDetails = res.data
            console.log('homeDetails =', this.homeDetails)
          })
        }
      })
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
    cancleAdd (formName) {
      this.addVisible = false
      this.file.name = ''
    },
    // 清空
    Empty () {
      this.formData.className = ''
    },
    getHomework (v) {
      if (v) {
        this.page = v
      }
      const params = {
        current: this.page,
        limit: this.limit,
        classId: localStorage.getItem('classId'),
        subName: this.formData.className
      }

      getMyHomeworkList(this.$qs.stringify(params)).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        console.log('list =', this.tableData)
        // this.$message({
        //   message: '获取成功',
        //   type: 'w'
        // })
      })
    },
    uploadHomework (id) {
      const params = {
        homeworkId: Number(id)
      }
      findmyHomeworkById(this.$qs.stringify(params)).then(res => {
        this.homeDetails = res.data
        console.log('=============', this.homeDetails)
        if (this.homeDetails && this.homeDetails.commitState === '已提交') {
          this.$message({
            message: '您已提交作业！',
            type: 'warning'
          })
        } else {
          console.log('1111111111')
          // else if(){
          //   // 当前时间晚于提交时间不可以再提交
          // }
          this.addVisible = true
          const params = {
            id: id
          }
          findHomeworkById(this.$qs.stringify(params)).then(res => {
            this.homeData = res.data
            console.log('subName =', this.homeData)
          })
        }
      })
    },
    submitForm () {
      console.log(this.file.name)
      if (!this.file.name) {
        this.$message({
          message: '请选择附件',
          type: 'warning'
        })
        return
      }
      // if (this.$route.query.edit) {
      //   const par = {
      //     id: Number(this.$route.query.id),
      //     endTime: this.$moment(this.addform.endTime).format('YYYY-MM-DD HH:mm')
      //   }
      //   updateHomework(this.$qs.stringify(par)).then(res => {
      //     if (res && res.data === 1) {
      //       this.$message({
      //         message: '修改成功',
      //         type: 'success'
      //       })
      //       this.$refs[formName].resetFields()
      //       this.$router.push({
      //         path: '/homeworkList'
      //       })
      //     } else {
      //       this.$message({
      //         message: '修改失败',
      //         type: 'error'
      //       })
      //     }
      //   })
      // } else {
      const params = {
        stuId: localStorage.getItem('useId'),
        stuName: localStorage.getItem('userName'),
        teacherName: this.homeData.userName,
        homeworkId: Number(this.homeData.id),
        subName: this.homeData.subName,
        className: this.homeData.className,
        pushTime: this.homeData.pushTime,
        judgeState: '未批改',
        endTime: this.homeData.endTime,
        commitTime: this.$moment(new Date()).format('YYYY-MM-DD HH:mm'),
        uploadFile: this.file.name,
        downloadFile: this.homeData.fileSrc,
        classId: this.homeData.classId,
        commitState: '已提交',
        score: 0,
        teacherId: this.homeData.userId
      }
      console.log('params=', params)
      let param = new FormData() // 创建form对象
      param.append('file', this.file)// 通过append向form对象添加数据
      console.log(param.get('file')) // FormData私有类对象，访问不到，可以通过get判断值是否传进去
      let config = {
        headers: {'Content-Type': 'multipart/form-data'}
      } // 添加请求头
      this.$http.post('/file/upload', param, config)
        .then(response => {
          console.log('上传成功')
          addmyHomework(this.$qs.stringify(params)).then(res => {
            if (res && res.data === 1) {
              this.$message({
                message: '上传成功',
                type: 'success'
              })
              // this.getUsers(0)
              this.addVisible = false
            } else {
              this.$message({
                message: '上传失败',
                type: 'error'
              })
            }
          })
          // this.$message({
          //   message: '上传成功',
          //   type: 'success'
          // })
        }).catch((error) => {
          this.$message({
            message: error,
            type: 'error'
          })
        })
    },
    // 跳转页面
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.limit = val
      this.getHomework(0)
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.getHomework(val)
    },
    // 表格头部背景色
    tableHeaderBG: () => {
      return 'background: #F2F2F2; text-align: center'
    },
    cellStyle: () => {
      return 'text-align: center'
    }
  }
}
</script>

<style scoped>
  .pagination {
    width: 50%;
    margin: 24px auto;
  }
  .rowsStyle {
    margin-bottom: 24px;
  }
</style>

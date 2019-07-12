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
        label="学生姓名"
        width="150">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.stuName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="发布时间"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ $moment(scope.row.pushTime).format('YYYY-MM-DD HH:mm') }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="截止时间"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ $moment(scope.row.endTime).format('YYYY-MM-DD HH:mm') }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="作业附件"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.downloadFile }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="学生作业"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.uploadFile }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="批改状态"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.judgeState }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="分数"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.score === 0 ? '暂无' : scope.row.score }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="280">
        <template slot-scope="scope">
          <el-button
            class="operateBtn"
            size="mini"
            @click="lookDetails(scope.row.homeworkId)">查看</el-button>
          <el-button
            class="operateBtn"
            size="mini"
            :disabled="scope.row.judgeState === '已批改'"
            @click="giveScore(scope.row.homeworkId)">评分</el-button>
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
    <el-dialog title="评分" :visible.sync="addVisible" :modal-append-to-body='upShow'>
      <el-form :model="addform" :rules="rules2" ref="addform" label-width="80px">
        <el-form-item label="分数" prop="score">
          <el-input v-model="addform.score" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleAdd('addform')">取 消</el-button>
        <el-button type="primary" @click="okAdd('addform')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="作业详情" :visible.sync="showDetails" :modal-append-to-body='upShow' style="width: 80%; margin: 0 auto">
      <el-row :gutter="20" class="rowsStyle">
        <el-col :span="12"><div>科目：{{ homeDetails && homeDetails.subName }}</div></el-col>
        <el-col :span="12"><div>学生：{{ homeDetails && homeDetails.stuName}}</div></el-col>
      </el-row>
      <el-row :gutter="20" class="rowsStyle">
        <el-col :span="12"><div>发布时间：{{ $moment(homeDetails && homeDetails.pushTime).format('YYYY-MM-DD HH:mm') }}</div></el-col>
        <el-col :span="12"><div>截止时间：{{ $moment(homeDetails && homeDetails.endTime).format('YYYY-MM-DD HH:mm') }}</div></el-col>
      </el-row>
      <el-row :gutter="20" class="rowsStyle">
        <el-col :span="12"><div>作业附件：<a class='link-type' href="#" :download="homeDetails.downloadFile" @click="download(homeDetails.downloadFile)">{{ homeDetails && homeDetails.downloadFile }}</a></div></el-col>
        <el-col :span="12"><div>提交附件：<a class='link-type' href="#" :download="homeDetails.uploadFile" @click="homeDetails && homeDetails.uploadFile ? download(homeDetails.uploadFile) : ''">{{ homeDetails && homeDetails.uploadFile }}</a></div></el-col>
      </el-row>
      <el-row :gutter="20" class="rowsStyle">
        <el-col :span="12"><div>批改状态：{{ homeDetails && homeDetails.judgeState ? homeDetails.judgeState : '未批改' }}</div></el-col>
        <el-col :span="12"><div>成绩：{{ homeDetails && homeDetails.judgeState && homeDetails.score ? homeDetails.score : '暂无' }}</div></el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import fileButton from '../../components/fileButton'
import { findWorkByTeaId, findmyHomeworkById, giveScore } from '@/api/homework'
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
      addform: {
        score: '',
        honeworkId: ''
      },
      rules2: {
        score: [
          { required: true, message: '请输入分数', trigger: 'blur' }
        ]
      }
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
    getHomework (v) {
      if (v) {
        this.page = v
      }
      const params = {
        current: this.page,
        limit: this.limit,
        teacherId: localStorage.getItem('userId'),
        subName: this.formData.className
      }

      findWorkByTeaId(this.$qs.stringify(params)).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        console.log('list =', this.tableData)
        // this.$message({
        //   message: '获取成功',
        //   type: 'w'
        // })
      })
    },
    giveScore (id) {
      this.addVisible = true
      this.addform.honeworkId = id
    },
    okAdd (addform) {
      this.$refs[addform].validate((valid) => {
        if (valid) {
          const params = {
            score: Number(this.addform.score),
            homeworkId: Number(this.addform.honeworkId)
          }
          giveScore(this.$qs.stringify(params)).then(res => {
            if (res && res.data === 1) {
              this.$message({
                message: '评分成功',
                type: 'success'
              })
              this.getHomework(0)
              this.$refs[addform].resetFields()
              this.addVisible = false
            } else {
              this.$message({
                message: '评分失败',
                type: 'error'
              })
            }
          })
          // if (this.frmName === '修改科目信息') {
          //   const params = {
          //     subId: this.addform.subId,
          //     subName: this.addform.subName
          //   }
          //   console.log('param=', params)
          //   updateSubject(this.$qs.stringify(params)).then(res => {
          //     if (res && res.data === 1) {
          //       this.$message({
          //         message: '修改成功',
          //         type: 'success'
          //       })
          //       this.getSubjects(0)
          //       this.$refs[addform].resetFields()
          //       this.addVisible = false
          //     } else {
          //       this.$message({
          //         message: '修改失败',
          //         type: 'error'
          //       })
          //     }
          //   })
          // } else {
          //   const params = {
          //     subId: this.addform.subId,
          //     subName: this.addform.subName
          //   }
          //   console.log('params=', this.addform)
          //   addSubject(this.$qs.stringify(params)).then(res => {
          //     // this.deleteShow = false
          //     if (res && res.data === 1) {
          //       this.$message({
          //         message: '新增成功',
          //         type: 'success'
          //       })
          //       this.getSubjects(0)
          //       this.$refs[addform].resetFields()
          //       this.addVisible = false
          //     } else {
          //       this.$message({
          //         message: '新增失败',
          //         type: 'error'
          //       })
          //     }
          //   })
          // }
        } else {
          console.log('error submit!!')
          return false
        }
        this.studentId = ''
        this.select = ''
        this.addform.subId = ''
        this.addform.subName = ''
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

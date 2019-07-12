<template>
  <div>
    <el-form :inline="true" :model="subForm" class="demo-form-inline" size="small">
      <el-form-item>
        <el-button type="primary" class="addT" size="small" @click="addStudent()"><i class="el-icon-plus"></i> 新增</el-button>
      </el-form-item>
      <el-form-item label="搜索:">
        <el-input v-model="subForm.subName" type="text" placeholder="请输入ID/科目" style="width:150px;"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getSubjects(0)">搜索</el-button>
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
        label="科目ID"
        width="150">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.subId }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="科目名称">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.subName }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button
            class="operateBtn"
            size="mini"
            @click="handleEdit(scope.row.subId)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="Delete(scope.row.subId)">删除</el-button>
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
    <el-dialog
      :visible.sync="deleteShow"
      :modal-append-to-body='upShow'
      title="提示"
      width="30%">
      <span>是否确定删除此信息？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteShow = false">取 消</el-button>
        <el-button type="primary" @click="isDelete">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="frmName" :visible.sync="addVisible" :modal-append-to-body='upShow'>
      <el-form :model="addform" :rules="rules2" ref="addform" label-width="80px">
        <el-form-item label="科目ID" prop="subId">
          <el-input v-model="addform.subId" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="科目名称" prop="subName">
          <el-input v-model="addform.subName" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleAdd('addform')">取 消</el-button>
        <el-button type="primary" @click="okAdd('addform')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { findAllSub, deleteSubject, addSubject, updateSubject, findSubjectById } from '@/api/subjects'
export default {
  name: 'list',
  data () {
    var myDate = new Date()
    return {
      tableData: [],
      subForm: {
        subName: ''
      },
      frmName: '',
      addform: {
        subId: '',
        subName: ''
      },
      teaInfo: [],
      page: 1,
      limit: 10,
      total: 0,
      deleteShow: false,
      upShow: false,
      deleteId: '',
      addVisible: false,
      rules2: {
        studentId: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        subId: [
          { required: true, message: '请输入ID', trigger: 'blur' }
        ],
        subName: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      studentId: '',
      select: '',
      year: myDate.getFullYear() - 2009,
      disable: false
    }
  },
  created () {
    this.getSubjects()
  },
  methods: {
    getSubjects (v) {
      if (v) {
        this.page = v
      }
      const params = {
        current: this.page,
        limit: this.limit,
        subName: this.subForm.subName
      }

      findAllSub(this.$qs.stringify(params)).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        // this.$message({
        //   message: '获取成功',
        //   type: 'w'
        // })
      })
    },
    // 产生随机数函数
    RndNum (n) {
      var rnd = ''
      for (var i = 0; i < n; i++) {
        rnd += Math.floor(Math.random() * 10)
      }
      return rnd
    },
    changeYear (e) {
      console.log(e)
      e.toString()
      this.studentId = this.RndNum(5)
      this.addform.userId = e.toString() + (this.studentId).toString()
    },
    cancleAdd (formName) {
      this.$refs[formName].resetFields()
      this.studentId = ''
      this.select = ''
      this.addform.subId = ''
      this.addform.subName = ''
      this.addVisible = false
    },
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    // 清空
    Empty () {
      this.subForm.subName = ''
    },
    // 跳转页面
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.limit = val
      this.getSubjects(0)
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.getSubjects(val)
    },
    handleEdit (val) {
      this.disable = true
      this.frmName = '修改科目信息'
      const params = {
        subId: val
      }
      findSubjectById(this.$qs.stringify(params)).then(res => {
        this.addform = res.data
        // this.select = this.addform.userId.substring(0, 4)
        // this.studentId = this.addform.classId.substring(5, 9)
      })
      this.addVisible = true
    },
    // 表格头部背景色
    tableHeaderBG: () => {
      return 'background: #F2F2F2; text-align: center'
    },
    cellStyle: () => {
      return 'text-align: center'
    },
    // 删除
    Delete (e) {
      this.deleteShow = true
      this.deleteId = e
    },
    addStudent () {
      // this.disable = false
      this.subId = ''
      // this.select = ''
      this.addform.subId = ''
      this.addform.subName = ''
      this.frmName = '新增科目'
      this.addVisible = true
    },
    okAdd (addform) {
      this.$refs[addform].validate((valid) => {
        if (valid) {
          if (this.frmName === '修改科目信息') {
            const params = {
              subId: this.addform.subId,
              subName: this.addform.subName
            }
            console.log('param=', params)
            updateSubject(this.$qs.stringify(params)).then(res => {
              if (res && res.data === 1) {
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
                this.getSubjects(0)
                this.$refs[addform].resetFields()
                this.addVisible = false
              } else {
                this.$message({
                  message: '修改失败',
                  type: 'error'
                })
              }
            })
          } else {
            const params = {
              subId: this.addform.subId,
              subName: this.addform.subName
            }
            console.log('params=', this.addform)
            addSubject(this.$qs.stringify(params)).then(res => {
              // this.deleteShow = false
              if (res && res.data === 1) {
                this.$message({
                  message: '新增成功',
                  type: 'success'
                })
                this.getSubjects(0)
                this.$refs[addform].resetFields()
                this.addVisible = false
              } else {
                this.$message({
                  message: '新增失败',
                  type: 'error'
                })
              }
            })
          }
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
    isDelete () {
      // const arr = []
      // for (var item in this.deleteList) {
      //   const obj = {
      //     id: this.deleteList[item].id
      //   }
      //   arr.push(obj)
      // }
      const params = {
        subId: this.deleteId
      }
      deleteSubject(this.$qs.stringify(params)).then(res => {
        this.deleteShow = false
        if (res && res.data === 1) {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.getSubjects(0)
        } else {
          this.$message({
            message: '删除失败',
            type: 'error'
          })
        }
      })
    }
  }
}
</script>

<style scoped>
  .addT {
    margin-bottom: 16px;
  }
  .pagination {
    width: 50%;
    margin: 24px auto;
  }
</style>
<style>
  .el-select .el-input {
    width: 150px;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>

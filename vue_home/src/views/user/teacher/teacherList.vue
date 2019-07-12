<template>
  <div>
    <el-form :inline="true" :model="formData" class="demo-form-inline" size="small">
      <el-form-item>
        <el-button type="primary" class="addT" size="small" @click="addTeacher()"><i class="el-icon-plus"></i> 新增</el-button>
      </el-form-item>
      <el-form-item label="搜索:">
        <el-input v-model="formData.userName" type="text" placeholder="请输入ID/姓名" style="width:150px;"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getUsers(0)">搜索</el-button>
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
        label="ID"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="用户类型"
        width="80">
        <template slot-scope="scope">
          <span style="margin-left: 10px">教师</span>
        </template>
      </el-table-column>
      <el-table-column
        label="姓名"
        width="150">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="手机号"
        width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.phone === null ? '暂无' : scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="280">
        <template slot-scope="scope">
          <el-button
            class="operateBtn"
            size="mini"
            @click="handleEdit(scope.row.userId)">编辑</el-button>
          <el-button
            class="operateBtn"
            size="mini"
            @click="resetPwd(scope.row.userId)">重置密码</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="Delete(scope.row.userId)">删除</el-button>
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
        <el-form-item label="ID" prop="userId">
          <el-input v-model="teacherId" class="input-with-select" :disabled="disable">
            <el-select v-model="select" slot="prepend" placeholder="请选择入职年份" @change="changeYear"  auto-complete="off" :disabled="disable">
              <el-option v-for="(i, index) in year" :key="index" :label="i+2009" :value="i+2009"></el-option>
              <!--<el-option label="2018" value="2"></el-option>-->
              <!--<el-option label="2019" value="3"></el-option>-->
            </el-select>
          </el-input>
          <!--<el-input v-model="addform.userId" auto-complete="off"></el-input>-->
        </el-form-item>
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="addform.userName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addform.sex">
            <el-radio label="1">女</el-radio>
            <el-radio label="2">男</el-radio>
          </el-radio-group>
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
import { findAll, deleteUser, addUser, findByUserId, updateTeacher, resetPwd } from '@/api/users'
export default {
  name: 'teacherList',
  components: {
  },
  data () {
    var myDate = new Date()
    return {
      tableData: [],
      formData: {
        userName: ''
      },
      frmName: '',
      addform: {
        userId: '',
        userName: '',
        sex: '1'
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
        teacherId: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        userId: [
          { required: true, message: '请输入ID', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      teacherId: '',
      select: '',
      year: myDate.getFullYear() - 2009,
      disable: false
    }
  },
  created () {
    this.getUsers()
  },
  methods: {
    getUsers (v) {
      if (v) {
        this.page = v
      }
      const params = {
        current: this.page,
        limit: this.limit,
        userType: 1,
        userName: this.formData.userName
      }

      findAll(this.$qs.stringify(params)).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        // this.$message({
        //   message: '获取成功',
        //   type: 'w'
        // })
      })
    },
    resetPwd (val) {
      const params = {
        userId: val
      }
      resetPwd(this.$qs.stringify(params)).then(res => {
        if (res && res.data === 1) {
          this.$message({
            message: '重置密码成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: '重置密码失败',
            type: 'error'
          })
        }
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
      this.teacherId = this.RndNum(5)
      this.addform.userId = e.toString() + (this.teacherId).toString()
    },
    cancleAdd (formName) {
      this.$refs[formName].resetFields()
      this.teacherId = ''
      this.select = ''
      this.addform.userId = ''
      this.addform.userName = ''
      this.addform.sex = '1'
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
      this.formData.userName = ''
    },
    // 跳转页面
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.limit = val
      this.getUsers(0)
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.getUsers(val)
    },
    handleEdit (val) {
      this.disable = true
      this.frmName = '修改教师信息'
      const params = {
        userId: val
      }
      findByUserId(this.$qs.stringify(params)).then(res => {
        this.addform = res.data
        this.select = this.addform.userId.substring(0, 4)
        console.log(this.select);
        this.teacherId = this.addform.userId.substring(4, 9)
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
    addTeacher () {
      this.disable = false
      this.teacherId = ''
      this.select = ''
      this.addform.userId = ''
      this.addform.userName = ''
      this.addform.sex = '1'
      this.frmName = '新增教师'
      this.addVisible = true
      console.log('addVisible', this.addVisible)
    },
    okAdd (addform) {
      console.log(this.frmName)
      this.$refs[addform].validate((valid) => {
        if (valid) {
          if (this.frmName === '修改教师信息') {
            const params = {
              userId: this.addform.userId,
              userName: this.addform.userName,
              sex: this.addform.sex
            }
            console.log('paramsss=', params)
            updateTeacher(this.$qs.stringify(params)).then(res => {
              if (res && res.data === 1) {
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
                this.getUsers(0)
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
              userId: this.addform.userId,
              userName: this.addform.userName,
              password: '123456',
              sex: this.addform.sex,
              userType: 1
            }
            console.log('params=', this.addform)
            addUser(this.$qs.stringify(params)).then(res => {
              this.deleteShow = false
              if (res && res.data === 1) {
                this.$message({
                  message: '新增成功',
                  type: 'success'
                })
                this.getUsers(0)
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
        this.teacherId = ''
        this.select = ''
        this.addform.userId = ''
        this.addform.userName = ''
        this.addform.sex = '1'
      })
    },
    isDelete () {
      const params = {
        userId: this.deleteId
      }
      deleteUser(this.$qs.stringify(params)).then(res => {
        this.deleteShow = false
        if (res && res.data === 1) {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.getUsers(0)
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

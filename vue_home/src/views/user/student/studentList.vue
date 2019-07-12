<template>
  <div>
    <el-form :inline="true" :model="formData" class="demo-form-inline" size="small">
      <el-form-item>
        <el-button type="primary" class="addT" size="small" @click="addStudent('addform')"><i class="el-icon-plus"></i> 新增</el-button>
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
          <span style="margin-left: 10px">学生</span>
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
    <el-dialog :title="frmName" :visible.sync="addVisible" :modal-append-to-body='upShow' :show-close="false">
      <el-form :model="addform" :rules="rules2" ref="addform" label-width="80px">
        <el-form-item label="班级" prop="classId">
          <el-select v-model="addform.classId" placeholder="请选择班级" @change="changeClass" :disabled="frmName === '修改学生信息'">
            <el-option
              v-for="item in classList"
              :key="item.classId"
              :label="item.className"
              :value="item.classId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="frmName === '新增学生'" label="学号" prop="userId">
          {{ preStuId }}
          <el-select v-model="select" placeholder="请选择后两位" @change="change">
            <el-option v-for="(i, index) in idList" :key="index" :label="i" :value="i"></el-option>
          </el-select>
          <!--<el-input-number v-model="num8" controls-position="right" :min="1" :max="10"></el-input-number>-->
          <!--<el-input v-model="studentId" class="input-with-select" :disabled="disable">-->
            <!--<el-select v-model="select" slot="prepend" placeholder="请选择入学年份" @change="changeYear"  auto-complete="off" :disabled="disable">-->
              <!--<el-option v-for="(i, index) in year" :key="index" :label="i+2009" :value="i+2009"></el-option>-->
              <!--&lt;!&ndash;<el-option label="2018" value="2"></el-option>&ndash;&gt;-->
              <!--&lt;!&ndash;<el-option label="2019" value="3"></el-option>&ndash;&gt;-->
            <!--</el-select>-->
          <!--</el-input>-->
          <!--<el-input v-model="addform.userId" auto-complete="off"></el-input>-->
        </el-form-item>
        <el-form-item v-else label="学号" prop="userId">{{ addform.userId}}</el-form-item>
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="addform.userName"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addform.sex" @change="changee">
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
import { selectAllClass } from '@/api/classes'
export default {
  name: 'studentList',
  components: {
  },
  data () {
    var myDate = new Date()
    return {
      num8: 1,
      classValue: '',
      tableData: [],
      formData: {
        userName: ''
      },
      frmName: '',
      addform: {
        classId: '',
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
        classId: [
          { required: true, message: '请选择班级', trigger: 'change' }
        ],
        studentId: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        userId: [
          { required: true, message: '请输入ID', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      studentId: '',
      preStuId: '',
      select: '',
      year: myDate.getFullYear(),
      disable: false,
      classList: [],
      idList: [],
      ishave: false
    }
  },
  created () {
    this.getAllClass()
    this.getUsers()
  },
  methods: {
    changee () {
      console.log('-============', this.addform)
    },
    change (e) {
      this.select = e.toString()
      this.addform.userId = this.preStuId + this.select
    },
    getAllClass () {
      selectAllClass(this.$qs.stringify()).then(res => {
        this.classList = res.data
        console.log('classList==', this.classList)
      })
    },
    getUsers (v) {
      if (v) {
        this.page = v
      }
      const params = {
        current: this.page,
        limit: this.limit,
        userType: 2,
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
    changeClass (e) {
      this.preStuId = this.year.toString() + e.toString()
      for (var i = 1; i <= 50; i++) {
        if (i < 10) {
          this.idList.push('0' + i.toString())
        } else {
          this.idList.push(i.toString())
        }
      }
    },
    changeYear (e) {
      this.preStuId = e
      // console.log(e)
      // e.toString()
      // this.studentId = e
      // this.addform.userId = e.toString() + (this.studentId).toString()
    },
    cancleAdd (formName) {
      this.$refs[formName].resetFields()
      this.emptyForm()
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
    emptyForm () {
      // this.addform.classId = ''
      this.preStuId = ''
      this.select = ''
      // this.addform.userName = ''
      // this.addform.sex = '1'
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
      // this.emptyForm()
      this.disable = true
      this.frmName = '修改学生信息'
      const params = {
        userId: val
      }
      findByUserId(this.$qs.stringify(params)).then(res => {
        if (res.statu === 100) {
          this.addform = res.data
          this.select = this.addform.userId.substring(0, 4)
          this.studentId = this.addform.userId.substring(5, 9)
        } else {
          this.$message({
            message: '查询失败',
            type: 'error'
          })
        }
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
      this.ishave = false
      this.disable = false
      this.frmName = '新增学生'
      this.addVisible = true
    },
    okAdd (formName) {
      console.log(formName, this.addform)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.frmName === '修改学生信息') {
            const params = {
              userId: this.addform.userId,
              userName: this.addform.userName,
              sex: this.addform.sex
            }
            updateTeacher(this.$qs.stringify(params)).then(res => {
              if (res && res.data === 1) {
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
                this.getUsers(0)
                this.$refs[formName].resetFields()
                this.addVisible = false
              } else {
                this.$message({
                  message: '修改失败',
                  type: 'error'
                })
              }
            })
          } else {
            const pars = {
              userId: this.addform.userId
            }
            const params = {
              userId: this.addform.userId,
              userName: this.addform.userName,
              password: '123456',
              sex: this.addform.sex,
              userType: 2,
              classId: this.addform.classId
            }
            console.log('params=', pars)
            findByUserId(this.$qs.stringify(pars)).then(res => {
              if (res.statu === 100) {
                this.$message({
                  message: '该学号已存在',
                  type: 'error'
                })
                this.addVisible = false
              } else {
                console.log('000111000', params)
                addUser(this.$qs.stringify(params)).then(res => {
                  if (res && res.data === 1) {
                    this.$message({
                      message: '新增成功',
                      type: 'success'
                    })
                    this.getUsers(0)
                    this.addVisible = false
                  } else {
                    this.$message({
                      message: '新增失败',
                      type: 'error'
                    })
                  }
                })
              }
            })
          }
          this.$refs[formName].resetFields()
        } else {
          console.log('error submit!!')
          return false
        }
        this.emptyForm()
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
  },
  computed: {
    // getStuPreId () {
    //   return this.preStuId
    // }
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
  .el-input-group__append {
    background-color: #fff;
  }
</style>

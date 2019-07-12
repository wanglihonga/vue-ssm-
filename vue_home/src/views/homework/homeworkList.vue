<template>
    <div>
      <el-form :inline="true" :model="formData" class="demo-form-inline" size="small">
        <el-form-item label="搜索:">
          <el-input v-model="formData.className" type="text" placeholder="请输入班级/科目" style="width:150px;"/>
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
              size="mini"
              @click="handleEdit(scope.row.id)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="Delete(scope.row.id)">删除</el-button>
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
    </div>
</template>

<script>
import fileButton from '../../components/fileButton'
import { findAllHomework, deleteHomework } from '@/api/homework'
export default {
  name: 'homework-list',
  components: {
    fileButton
  },
  data () {
    return {
      file: '',
      src: '',
      formData: {
        className: ''
      },
      tableData: [],
      page: 1,
      limit: 10,
      total: 0,
      deleteShow: false,
      upShow: false,
      deleteId: ''
    }
  },
  created () {
    this.getHomework()
  },
  methods: {
    addHomework () {

    },
    lookDetails (id) {
      console.log('id =', id)
      this.$router.push({
        path: '/addHomework',
        query: {
          id,
          look: true
        }
      })
    },
    handleEdit (id) {
      console.log('id =', id)
      this.$router.push({
        path: '/addHomework',
        query: {
          id,
          edit: true
        }
      })
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
        className: this.formData.className,
        teacherId: localStorage.getItem('userId')
      }

      findAllHomework(this.$qs.stringify(params)).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        console.log('list =', this.tableData)
        // this.$message({
        //   message: '获取成功',
        //   type: 'w'
        // })
      })
    },
    onUploadFile (e) {
      let file = e.target.files[0]
      let param = new FormData() // 创建form对象
      param.append('file', file)// 通过append向form对象添加数据
      console.log(param.get('file')) // FormData私有类对象，访问不到，可以通过get判断值是否传进去
      let config = {
        headers: {'Content-Type': 'multipart/form-data'}
      } // 添加请求头
      this.$http.post('/file/upload', param, config)
        .then(response => {
          this.$message({
            message: '上传成功',
            type: 'success'
          })
        }).catch((error) => {
          this.$message({
            message: error,
            type: 'error'
          })
        })
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
    isDelete () {
      // const arr = []
      // for (var item in this.deleteList) {
      //   const obj = {
      //     id: this.deleteList[item].id
      //   }
      //   arr.push(obj)
      // }
      const params = {
        id: this.deleteId
      }
      deleteHomework(this.$qs.stringify(params)).then(res => {
        this.deleteShow = false
        if (res && res.data === 1) {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.getHomework(0)
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
  .pagination {
    width: 50%;
    margin: 24px auto;
  }
</style>

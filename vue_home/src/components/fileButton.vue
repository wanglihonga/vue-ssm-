<template>
  <div style="display: inline-block">
    <div v-if="type === 'IMG'" type="primary" icon="el-icon-upload" size="small" class="imgbtn">
      <i class="el-icon-upload"/>
      <input type="file" accept="image/*" class="imgfile" @change="onUploadIMG($event)">请选择图片
    </div>
    <div v-if="type === 'FILE'" type="primary" icon="el-icon-upload" size="small" class="imgbtn">
      <i class="el-icon-upload imargin"/>
      <input type="file" class="imgfile" @change="onUploadFile($event)">
      <span class="imgfont">上传附件</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    type: {
      type: String,
      default: 'IMG'
    }
  },
  data () {
    return {}
  },
  /*
* 逻辑处理
* */
  computed: {},
  /*
* 页面初始化
* */
  created() {
  },
  /*
* 执行方法
* */
  methods: {
    onUploadIMG (e) {
      this.$emit('successImg', e)
    },
    onUploadFile (e) {
      this.$emit('successFile', e)
    }
  }
}
</script>

<style scoped>
  .imgbtn{
    display: inline-block;
    position: relative;
    width: 99px;
    background-color: #1795ff;
    color: white;
    padding-left: 6px;
    padding-top: 1px;
    border-radius: 5px;
  }
  .imgfile{
    opacity: 0;
    position: absolute;
    top: 0;
    left: 0;
    width: 97px;
    height: 29px;
  }
  .imgfile{
    position: absolute;
  }
  .imargin{
    margin-left: 5px;
  }
</style>

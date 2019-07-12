// import Vue from 'vue'
// import axios from 'axios'
// import { getToken } from '@/utils/auth'
// export default{
//   // 手机号
//   phone: (str) => {
//     const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
//     return reg.test(str)
//   },
//   // 身份证
//   idendityId: (card) => {
//     const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
//     return reg.test(card)
//   },
//   // 信息号
//   information: (information) => {
//     const reg = /[A-Za-z_][A-Za-z_0-9]{3,3}$/
//     return reg.test(information)
//   },
//   // url
//   website: (http) => {
//     const reg = /^((([hH][tT][tT][pP][sS]?|[fF][tT][pP])\:\/\/)?([\w\.\-]+(\:[\w\.\&%\$\-]+)*@)?((([^\s\(\)\<\>\\\"\.\[\]\,@;:]+)(\.[^\s\(\)\<\>\\\"\.\[\]\,@;:]+)*(\.[a-zA-Z]{2,4}))|((([01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}([01]?\d{1,2}|2[0-4]\d|25[0-5])))(\b\:(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[1-5]\d{4}|[1-9]\d{0,3}|0)\b)?((\/[^\/][\w\.\,\?\'\\\/\+&%\$#\=~_\-@]*)*[^\.\,\?\"\'\(\)\[\]!;<>{}\s\x7F-\xFF])?)$/
//     return reg.test(http)
//   },
//   computeAge (UUserCard) {
//     let birthday = ''
//     if (UUserCard.length === 15) {
//       birthday = '19' + (UUserCard.substring(6, 8) + '-' + UUserCard.substring(8, 10) + '-' + UUserCard.substring(10, 12))
//     } else if (UUserCard.length === 18) {
//       birthday = (UUserCard.substring(6, 10) + '-' + UUserCard.substring(10, 12) + '-' + UUserCard.substring(12, 14))
//     }
//     // 获得今天的时间
//     const date = new Date()
//     birthday = new Date(birthday)
//     const newDate = date.getTime() - birthday.getTime()
//     // 向下取整  例如 10岁 20天 会计算成 10岁
//     // 如果要向上取整 计算成11岁，把floor替换成 ceil
//     return Math.floor(newDate / 1000 / 60 / 60 / 24 / 365)
//   },
//   // 去除富文本空格和&nbsp;
//   contentTest (str) {
//     return str.replace(/<[^>]+>/g, '').replace(/[ ]|[&nbsp;]/g, '')
//   },
//   //  导出
//   dao: (daochu) => {
//     const api = Vue.prototype.$excelUrl
//     return api + daochu
//   },
//   // 模板
//   template: (template) => {
//     const api = Vue.prototype.$plateUrl
//     return api + template + '.xls'
//   },
//   // 根据年龄条件查询后台，转成活了多少年
//   survivalTime: (age) => {
//     return Number(new Date().getFullYear()) - Number(age)
//   },
//   // 时间格式转换 年月日 时分秒
//   dateChange: (v, type) => {
//     if (type === 'year') {
//       return format(v, 'YYYY')
//     }
//     if (type === 'mouth') {
//       return format(v, 'YYYY-MM')
//     }
//     if (type === 'day') {
//       return format(v, 'YYYY-MM-DD')
//     }
//     if (type === 'hour') {
//       return format(v, 'YYYY-MM-DD HH')
//     }
//     if (type === 'Minute') {
//       return format(v, 'YYYY-MM-DD HH:mm')
//     }
//     if (type === 'second') {
//       return format(v, 'YYYY-MM-DD HH:mm:ss')
//     }
//   },
//   // 时间段格式转化 毫秒
//   dateChangeArray: (date) => {
//     var arr = []
//     var a = date[0].getTime()
//     var b = date[1].getTime()
//     arr.push(a)
//     arr.push(b)
//     return arr
//   },
//   // 表格头部背景色
//   tableHeaderBG: () => {
//     return 'background: #F2F2F2'
//   },
//   // 图片上传
//   onUploadImg(e) {
//     var a = this.uploadSectionImg(e.target)
//     if (!a) {
//       return
//     }
//     var formData = new FormData()
//     formData.append('file', e.target.files[0])
//     formData.append('type', 'test')
//     // todo 根据实际接口进行更换上传接口
//     var baseUrl = Vue.prototype.$apiUrl + '/module/imgUpload'
//     return axios.post(baseUrl, formData, {
//       headers: {
//         'Content-Type': 'multipart/form-data',
//         'x-access-token': getToken()
//       }
//     })
//   },
//   uploadSectionImg(param) {
//     const fileObj = param.files[0]
//     const isLt2M = fileObj.size / 1024 / 1024 < 2
//     if (!isLt2M) {
//       Vue.prototype.$message.warning('上传图片大小不能超过 2MB!')
//       return false
//     }
//     if (fileObj.type === 'image/jpeg') {
//       return true
//     } else if (fileObj.type === 'image/png') {
//       return true
//     } else {
//       Vue.prototype.$message.warning('只能上传jpg/png文件')
//       return false
//     }
//   },
//   //  附件上传
//   onUploadFile(e) {
//     var a = this.uploadSectionFile(e.target)
//     if (!a) {
//       return
//     }
//     var knowFileArr = e.target.value.split('.').length
//     var knowFile = e.target.value.split('.')[knowFileArr - 1]
//     if (knowFile !== 'mp4' && knowFile !== 'doc' && knowFile !== 'docx' && knowFile !== 'txt' && knowFile !== 'pdf' && knowFile !== 'jpg' && knowFile !== 'jpeg' && knowFile !== 'png' && knowFile !== 'pptx' && knowFile !== 'xls' && knowFile !== 'xlsx') {
//       Vue.prototype.$message.warning('只能上传doc/docx/txt/pdf/jpg/jpeg/png/mp4/pptx/xls文件')
//       return
//     }
//     var formData = new FormData()
//     formData.append('file', e.target.files[0])
//     formData.append('type', 'test')
//     // todo 根据实际接口进行更换上传接口
//     // var baseUrl = Vue.prototype.$apiUrl + '/authFile/uploadFile'
//     var baseUrl
//     if (knowFile === 'mp4') {
//       baseUrl = Vue.prototype.$apiUrl + '/authFile/uploadFile'
//     } else {
//       baseUrl = Vue.prototype.$apiUrl + '/authFile/uploadTranCodeimageFile'
//     }
//     return axios.post(baseUrl, formData, {
//       headers: {
//         'Content-Type': 'multipart/form-data',
//         'x-access-token': getToken()
//       }
//     })
//   },
//   uploadSectionFile(param) {
//     const fileObj = param.files[0]
//     const isLt2M = fileObj.size / 1024 / 1024 < 1000
//     if (!isLt2M) {
//       Vue.prototype.$message.error('上传大小不能超过 1000MB!')
//       return false
//     }
//     return true
//     // todo 目前没有其他条件限制
//     // if (fileObj.type === 'file/doc') {
//     // } else if (fileObj.type === 'file/xsl') {
//     // }
//     // return fileObj
//   }
// }
//
// import { format } from 'date-fns'

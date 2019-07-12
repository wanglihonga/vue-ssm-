import request from '@/utils/request'

// 上传
// /file/upload
export function upload (params) {
  return request({
    url: '/file/upload',
    method: 'post',
    data: params
  })
}
// 下载
// // /file/download
// export function getFile (params) {
//   return request({
//     url: '/file/getFile',
//     method: 'post',
//     data: params
//   })
// }
/**
 * 下载附件
 * @returns {AxiosPromise}
 */
export function downloadFile (params) {
  return request({
    url: '/file/download',
    method: 'post',
    data: params,
    responseType: 'blob' // 这一步也很关键，一定要加上 responseType 值为 blob
  })
}

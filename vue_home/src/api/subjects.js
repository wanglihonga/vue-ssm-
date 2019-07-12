import request from '@/utils/request'

// 查询
// /subject/findAllSub
export function findAllSub (params) {
  return request({
    url: '/subject/findAllSub',
    method: 'post',
    data: params
  })
}
// 查询
// /subject/selectAllSub
export function selectAllSub (params) {
  return request({
    url: '/subject/selectAllSub',
    method: 'post',
    data: params
  })
}
// 删除
// /subject/deleteSubject
export function deleteSubject (params) {
  return request({
    url: '/subject/deleteSubject',
    method: 'post',
    data: params
  })
}
// 修改
// /subject/updateSubject
export function updateSubject (params) {
  return request({
    url: '/subject/updateSubject',
    method: 'post',
    data: params
  })
}

// 新增
// /subject/addSubject
export function addSubject (params) {
  return request({
    url: '/subject/addSubject',
    method: 'post',
    data: params
  })
}

// /subject/findSubjectById
export function findSubjectById (params) {
  return request({
    url: '/subject/findSubjectById',
    method: 'post',
    data: params
  })
}

import request from '@/utils/request'

// 查询所有用户
// /classes/findAllClass
export function findAllClass (params) {
  return request({
    url: '/classes/findAllClass',
    method: 'post',
    data: params
  })
}
// 删除
// /classes/deleteClass
export function deleteClass (params) {
  return request({
    url: '/classes/deleteClass',
    method: 'post',
    data: params
  })
}
// 修改
// /classes/updateClass
export function updateClass (params) {
  return request({
    url: '/classes/updateClass',
    method: 'post',
    data: params
  })
}

// 新增
// /classes/addClass
export function addClass (params) {
  return request({
    url: '/classes/addClass',
    method: 'post',
    data: params
  })
}
// /classes/findByClassId
export function findClassById (params) {
  return request({
    url: '/classes/findClassById',
    method: 'post',
    data: params
  })
}

// /classes/selectAllClass
export function selectAllClass (params) {
  return request({
    url: '/classes/selectAllClass',
    method: 'post',
    data: params
  })
}

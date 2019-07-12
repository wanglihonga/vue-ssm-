import request from '@/utils/request'

// 查询所以用户
// /user/getAllUsers
export function findAll (params) {
  return request({
    url: '/user/findAll',
    method: 'post',
    data: params
  })
}
// 用户删除
// /user/deleteUser
export function deleteUser (params) {
  return request({
    url: '/user/deleteUser',
    method: 'post',
    data: params
  })
}
// 用户详情
// /user/findByUserId
export function findByUserId (params) {
  return request({
    url: '/user/findByUserId',
    method: 'post',
    data: params
  })
}
// 修改密码
// /user/updatePwd
export function updatePwd (params) {
  return request({
    url: '/user/updatePwd',
    method: 'post',
    data: params
  })
}
// 修改电话
// /user/updatePhone
export function updatePhone (params) {
  return request({
    url: '/user/updatePhone',
    method: 'post',
    data: params
  })
}
// 修改头像
// /user/updatePhone
export function uploadPhoto (params) {
  return request({
    url: '/user/uploadPhoto',
    method: 'post',
    data: params
  })
}
// 根据类型查询用户
// /user/findUsersByUserType
export function findUsersByUserType (params) {
  return request({
    url: '/user/findUsersByUserType',
    method: 'post',
    data: params
  })
}

// 新增用户
// /user/addUser
export function addUser (params) {
  return request({
    url: '/user/addUser',
    method: 'post',
    data: params
  })
}
// 修改教师信息
// /user/updateTeacher
export function updateTeacher (params) {
  return request({
    url: '/user/updateTeacher',
    method: 'post',
    data: params
  })
}
// 重置密码
// /user/resetPwd
export function resetPwd (params) {
  return request({
    url: '/user/resetPwd',
    method: 'post',
    data: params
  })
}

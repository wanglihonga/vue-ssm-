import request from '@/utils/request'

// 发布作业
// /homework/addHomework
export function addHomework (params) {
  return request({
    url: '/homework/addHomework',
    method: 'post',
    data: params
  })
}

// 查询所有用户
// /homework/findAllHomework
export function findAllHomework (params) {
  return request({
    url: '/homework/findAllHomework',
    method: 'post',
    data: params
  })
}
// 删除
// /homework/deleteHomework
export function deleteHomework (params) {
  return request({
    url: '/homework/deleteHomework',
    method: 'post',
    data: params
  })
}
// 修改
// /homework/updateHomework
export function updateHomework (params) {
  return request({
    url: '/homework/updateHomework',
    method: 'post',
    data: params
  })
}
// /homework/findHomeworkById
export function findHomeworkById (params) {
  return request({
    url: '/homework/findHomeworkById',
    method: 'post',
    data: params
  })
}

// /homework/selectAlllHomework
export function selectAlllHomework (params) {
  return request({
    url: '/homework/selectAlllHomework',
    method: 'post',
    data: params
  })
}

// myHomework/getMyHomeworkList
export function getMyHomeworkList (params) {
  return request({
    url: '/myHomework/getMyHomeworkList',
    method: 'post',
    data: params
  })
}
// /myHomework/addmyHomework
export function addmyHomework (params) {
  return request({
    url: '/myHomework/addmyHomework',
    method: 'post',
    data: params
  })
}
// /myHomework/findmyHomeworkById
export function findmyHomeworkById (params) {
  return request({
    url: '/myHomework/findmyHomeworkById',
    method: 'post',
    data: params
  })
}
// /myHomework/findWorkByTeaId
export function findWorkByTeaId (params) {
  return request({
    url: '/myHomework/findWorkByTeaId',
    method: 'post',
    data: params
  })
}

// /myHomework/giveScore
export function giveScore (params) {
  return request({
    url: '/myHomework/giveScore',
    method: 'post',
    data: params
  })
}

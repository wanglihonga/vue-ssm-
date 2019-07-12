// import request from '@/utils/request'
// import allRoutes from '@/router/allRoutes'
// import staticRoutes from '@/router/staticRoutes'
// import { getToken, setToken, removeToken, getStorge, setStorge, removeStorge } from '@/utils/auth'
// import Layout from '@/views/layout/Layout'
//
// const auth = {
//   state: {
//     token: getToken(),
//     permissionCodes: [],
//     userInfo: {},
//     name: getStorge('UserName'),
//     userId: getStorge('UserId'),
//     avatar: '',
//     isDynamicRoutesInit: false,
//     dynamicRoutes: [],
//     roles: [],
//     isAccountDetailLoading: false,
//     accountDetailData: {},
//     isRoleDetailLoading: false,
//     roleDetailData: {},
//     roleModuleListMap: {},
//     rolePermissionListMap: {},
//     isModuleDetailLoading: false,
//     moduleDetailData: {},
//     isPermissionDetailLoading: false,
//     permissionDetailData: {}
//   },
//
//   mutations: {
//     SET_TOKEN: (state, token) => {
//       state.token = token
//     },
//     SET_NAME: (state, name) => {
//       state.name = name
//     },
//     SET_AVATAR: (state, avatar) => {
//       state.avatar = avatar
//     },
//     SET_ROLES: (state, roles) => {
//       state.roles = roles
//     },
//     SET_DYNAMIC_ROUTES: (state, dynamicRoutes) => {
//       state.dynamicRoutes = dynamicRoutes
//       state.isDynamicRoutesInit = true
//     },
//     SET_PERMISSION_CODES: (state, data) => {
//       // setStorgeItem('permissionCodes', data)
//       state.permissionCodes = data
//     },
//     SET_USERINFO: (state, data) => {
//       setStorge('UserName', data.accountResult.username)
//       setStorge('UserId', data.accountResult.id)
//       setStorge('UserInfo', data)
//       state.userInfo = data
//       state.userId = data.accountResult.id
//       state.name = data.accountResult.username
//     },
//     SET_IS_MODULES_ACCOUNT_DETAIL_LOADING: (state, data) => {
//       state.isAccountDetailLoading = data
//     },
//     SET_MODULES_ACCOUNT_DETAIL_DATA: (state, data) => {
//       state.accountDetailData = data
//     },
//     SET_IS_MODULES_ROLE_DETAIL_LOADING: (state, data) => {
//       state.isRoleDetailLoading = data
//     },
//     SET_MODULES_ROLE_DETAIL_DATA: (state, data) => {
//       state.roleDetailData = data
//     },
//     SET_ROLE_MODULE_LIST_MAP: (state, data) => {
//       state.roleModuleListMap = data.roleModuleListMap
//       state.rolePermissionListMap = data.rolePermissionListMap
//     },
//     SET_IS_MODULE_DETAIL_LOADING: (state, data) => {
//       state.isModuleDetailLoading = data
//     },
//     SET_MODULE_DETAIL_DATA: (state, data) => {
//       state.moduleDetailData = data
//     },
//     SET_IS_PERMISSION_DETAIL_LOADING: (state, data) => {
//       state.isPermissionDetailLoading = data
//     },
//     SET_PERMISSION_DETAIL_DATA: (state, data) => {
//       state.permissionDetailData = data
//     }
//   },
//
//   actions: {
//     // 登录
//     Login({ commit }, userInfo) {
//       const username = userInfo.username.trim()
//       return new Promise((resolve, reject) => {
//         request({
//           url: '/auth/login',
//           method: 'post',
//           data: {
//             'username': username,
//             'password': userInfo.password
//           }
//         }).then(response => {
//           const data = response.data
//           setToken(data.token)
//           commit('SET_TOKEN', data.token)
//           commit('SET_USERINFO', data)
//           resolve()
//         }).catch(error => {
//           reject(error)
//         })
//       })
//     },
//     // 获取用户菜单权限
//     requestDynamicRoutes({ commit, state }) {
//       return new Promise((resolve, reject) => {
//         request({
//           url: '/auth/pcAuthInfo',
//           method: 'post',
//           data: { }
//         }).then(response => {
//           const resultDatas = []
//           const tempDatas = []
//           const moduleResultList = response.data.moduleResultList
//           const moduleResultMap = {}
//           if (moduleResultList !== null && moduleResultList.length > 0) {
//             moduleResultList.forEach((item) => {
//               const moduleCode = item.moduleCode
//               const moduleUrl = item.moduleUrl
//               const moduleName = item.moduleName
//               const moduleParentId = item.moduleParentId
//               const moduleIcon = item.moduleIcon
//               const moduleId = item.id
//               if (moduleParentId === 1) {
//                 tempDatas.push({
//                   moduleId: moduleId,
//                   path: '/' + moduleUrl,
//                   component: Layout,
//                   name: moduleCode,
//                   meta: { title: moduleName, icon: moduleIcon },
//                   children: []
//                 })
//               } else {
//                 if (moduleResultMap[moduleParentId] == null) {
//                   moduleResultMap[moduleParentId] = []
//                 }
//                 const tempModule = Object.assign({
//                   path: moduleUrl,
//                   name: moduleCode,
//                   meta: { title: moduleName, icon: '' }
//                 }, allRoutes[item.moduleCode])
//                 tempModule.meta.title = moduleName
//                 moduleResultMap[moduleParentId].push(tempModule)
//               }
//             })
//             tempDatas.forEach((item) => {
//               const moduleId = item.moduleId
//               if (moduleResultMap[moduleId] != null && moduleResultMap[moduleId].length > 0) {
//                 moduleResultMap[moduleId].forEach((childItem, childIndex) => {
//                   if (childIndex === 0) {
//                     item.redirect = item.path + '/' + childItem.path
//                   }
//                   item.children.push(childItem)
//                 })
//                 resultDatas.push(item)
//               }
//             })
//           }
//           //  添加静态配置，生产环境需要去掉
//           if (staticRoutes.length > 0) {
//             staticRoutes.forEach((item) => {
//               resultDatas.push(Object.assign(item, allRoutes['LAYOUT']))
//             })
//           }
//           console.log('resultDatas', resultDatas)
//           console.log('moduleResultMap', moduleResultMap)
//           resultDatas.push({ path: '*', redirect: '/404', hidden: true })
//           var home = [{
//             path: '/',
//             component: Layout,
//             redirect: '/home',
//             name: 'Dashboard',
//             children: [{
//               path: 'home',
//               component: () => import('@/views/home'),
//               meta: {
//                 title: '首页',
//                 icon: 'home'
//               }
//             }]
//           }]
//           // const resultDatas = []
//           commit('SET_DYNAMIC_ROUTES', [...home, ...resultDatas])
//           commit('SET_PERMISSION_CODES', response.data.permissionSet)
//           console.log(resultDatas, 'router end')
//           resolve([...home, ...resultDatas])
//         }).catch(error => {
//           commit('SET_DYNAMIC_ROUTES', [])
//           commit('SET_PERMISSION_CODES', [])
//           reject(error)
//         })
//       })
//     },
//     // 获取用户信息
//     GetInfo({ commit, state }) {
//       return new Promise((resolve, reject) => {
//         request({
//           url: '/user/info',
//           method: 'get',
//           params: { 'token': state.token }
//         }).then(response => {
//           const data = response.data
//           commit('SET_ROLES', data.roles)
//           commit('SET_NAME', data.name)
//           commit('SET_AVATAR', data.avatar)
//           resolve(response)
//         }).catch(error => {
//           reject(error)
//         })
//       })
//     },
//
//     // 登出
//     LogOut({ commit, state }) {
//       return new Promise((resolve, reject) => {
//         commit('SET_TOKEN', '')
//         commit('SET_ROLES', [])
//         removeToken()
//         removeStorge('UserName')
//         resolve()
//       })
//     },
//     // 前端 登出
//     FedLogOut({ commit }) {
//       return new Promise(resolve => {
//         commit('SET_TOKEN', '')
//         removeToken()
//         resolve()
//       })
//     },
//     //  获取账号编辑页面数据
//     queryAccountDetailData({ commit }, id) {
//       commit('SET_IS_MODULES_ACCOUNT_DETAIL_LOADING', true)
//       commit('SET_MODULES_ACCOUNT_DETAIL_DATA', {})
//       return new Promise((resolve, reject) => {
//         request({
//           url: '/auth/accountDetail',
//           method: 'post',
//           data: { id: id }
//         }).then(response => {
//           commit('SET_IS_MODULES_ACCOUNT_DETAIL_LOADING', false)
//           commit('SET_MODULES_ACCOUNT_DETAIL_DATA', response.data)
//           resolve(response.data)
//         }).catch(error => {
//           commit('SET_IS_MODULES_ACCOUNT_DETAIL_LOADING', false)
//           reject(error)
//         })
//       })
//     },
//     //  重置账号新增页面数据
//     resetAccountDetailData({ commit }) {
//       commit('SET_MODULES_ACCOUNT_DETAIL_DATA', {})
//     },
//     //  获取账号编辑页面数据
//     queryRoleDetailData({ commit }, id) {
//       commit('SET_IS_MODULES_ROLE_DETAIL_LOADING', true)
//       commit('SET_MODULES_ROLE_DETAIL_DATA', {})
//       return new Promise((resolve, reject) => {
//         request({
//           url: '/auth/roleDetail',
//           method: 'post',
//           data: { id: id }
//         }).then(response => {
//           commit('SET_IS_MODULES_ROLE_DETAIL_LOADING', false)
//           commit('SET_MODULES_ROLE_DETAIL_DATA', response.data)
//           resolve(response.data)
//         }).catch(error => {
//           commit('SET_IS_MODULES_ROLE_DETAIL_LOADING', false)
//           reject(error)
//         })
//       })
//     },
//     //  重置角色新增页面数据
//     resetRoleDetailData({ commit }) {
//       commit('SET_MODULES_ROLE_DETAIL_DATA', { permissionList: [] })
//     },
//     //  角色管理页面获取系统中全部可用模块
//     queryRoleModuleListMap({ commit }) {
//       return new Promise((resolve, reject) => {
//         request({
//           url: '/auth/roleModuleList',
//           method: 'post',
//           data: {}
//         }).then(response => {
//           //  模块列表组
//           const tempModuleMap = {}
//           response.data.moduleResultList.forEach((item) => {
//             if (tempModuleMap[item.moduleParentId + ''] === null || tempModuleMap[item.moduleParentId + ''] === undefined) {
//               tempModuleMap[item.moduleParentId + ''] = []
//             }
//             tempModuleMap[item.moduleParentId + ''].push(item)
//           })
//           //  权限列表组
//           const tempPermissionMap = {}
//           response.data.permissionResultList.forEach((item) => {
//             if (tempPermissionMap[item.moduleId + ''] === null || tempPermissionMap[item.moduleId + ''] === undefined) {
//               tempPermissionMap[item.moduleId + ''] = []
//             }
//             tempPermissionMap[item.moduleId + ''].push(item)
//           })
//           commit('SET_ROLE_MODULE_LIST_MAP', { roleModuleListMap: tempModuleMap, rolePermissionListMap: tempPermissionMap })
//         }).catch(error => {
//           commit('SET_ROLE_MODULE_LIST_MAP', {})
//           reject(error)
//         })
//       })
//     },
//     //  获取账号编辑页面数据
//     queryModuleDetailData({ commit }, id) {
//       commit('SET_IS_MODULE_DETAIL_LOADING', true)
//       commit('SET_MODULE_DETAIL_DATA', {})
//       return new Promise((resolve, reject) => {
//         request({
//           url: '/auth/moduleDetail',
//           method: 'post',
//           data: { id: id }
//         }).then(response => {
//           commit('SET_IS_MODULE_DETAIL_LOADING', false)
//           commit('SET_MODULE_DETAIL_DATA', response.data)
//           resolve(response.data)
//         }).catch(error => {
//           commit('SET_IS_MODULE_DETAIL_LOADING', false)
//           reject(error)
//         })
//       })
//     },
//     //  重置角色新增页面数据
//     resetModuleDetailData({ commit }) {
//       commit('SET_MODULE_DETAIL_DATA', { permissionList: [] })
//     },
//     //  获取权限编辑页面数据
//     queryPermissionDetailData({ commit }, id) {
//       commit('SET_IS_PERMISSION_DETAIL_LOADING', true)
//       commit('SET_PERMISSION_DETAIL_DATA', {})
//       return new Promise((resolve, reject) => {
//         request({
//           url: '/auth/permissionDetail',
//           method: 'post',
//           data: { id: id }
//         }).then(response => {
//           commit('SET_IS_PERMISSION_DETAIL_LOADING', false)
//           commit('SET_PERMISSION_DETAIL_DATA', response.data)
//           resolve(response.data)
//         }).catch(error => {
//           commit('SET_IS_PERMISSION_DETAIL_LOADING', false)
//           reject(error)
//         })
//       })
//     },
//     //  重置权限新增页面数据
//     resetPermissionDetailData({ commit }, data) {
//       console.log('data', data)
//       commit('SET_PERMISSION_DETAIL_DATA', Object.assign({}, data))
//     }
//   }
// }
//
// export default auth

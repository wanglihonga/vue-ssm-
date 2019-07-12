const getters = {
  sidebar: state => state.app.sidebar,
  dynamicRoutes: state => state.auth.dynamicRoutes,
  permissionCodes: state => state.auth.permissionCodes,
  device: state => state.app.device,
  token: state => state.user.token,
  currentUser: state => state.user.currentUser,
  avatar: state => state.user.currentUser.avatar || 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
  roles: state => state.user.roles,
  roleRouter: state => state.user.roleRouter,
  visitedViews: state => state.tabs.visitedViews,
  cachedViews: state => state.tabs.cachedViews
}
export default getters

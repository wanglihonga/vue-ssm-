import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const UserKey = 'Current-User'
export function getToken () {
  return Cookies.get(TokenKey)
}

export function setToken (token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken () {
  return Cookies.remove(TokenKey)
}

export function getUser () {
  return Cookies.get(UserKey)
}

export function setUser (user) {
  return Cookies.set(UserKey, user)
}

// export function setroleRouter(user) {
//   return Cookies.set(UserKey, user)
// }

export function getStorge (key) {
  return Cookies.get(key)
}

export function setStorge (key, value) {
  return Cookies.set(key, value)
}

export function removeStorge (key) {
  return Cookies.remove(key)
}

export function getStorgeItem (key) {
  const value = localStorage.getItem(key)
  if (value === null || value === undefined) {
    return null
  }
  return JSON.parse(value)
}

export function setStorgeItem (key, value) {
  return localStorage.setItem(key, JSON.stringify(value))
}

import axios from 'axios'
import { ElMessage } from 'element-plus'
import { systemStore } from "@/store/system"
import router from '@/router'

export function request(config: any) {
  const store = systemStore()
  const token = store.userLocal.access_token || store.userSession.access_token
  // 创建axios的实例
  const instance = axios.create({
    baseURL: "/api",
    timeout: 50000
  })
  // 请求拦截器配置
  instance.interceptors.request.use(config => {
    if (token) {
      config.headers.Authorization = 'Bearer ' + token
    }
    return config
  }, error => {
    console.log(error)
    // return Promise.error(error)
    return Promise.reject(new Error(error))
  })
  // 响应拦截器配置
  instance.interceptors.response.use(response => {
    return response.data
  }, error => {
    console.log(error)
    if (error.response) {
      switch (error.response.status) {
        case 400:
          return Promise.reject(error.response.data)
        case 401:
          console.log("无权访问")
          ElMessage.error('对不起，您暂无权限访问此接口，请登录重试！')
          localStorage.clear()
          sessionStorage.clear()
          window.location.href = "http://127.0.0.1:5173";
          break
        case 403:
          ElMessage.error('对不起，您暂无权限访问此接口！')
          break
        case 404:
          console.log("404啦")
          break
        case 500:
          console.log("500啦")
          ElMessage.error('后端接口异常，请稍候重试！')
          break
        default:
          return Promise.reject(error)
      }
    } else {
      console.log("请求超时")
      ElMessage.error('请求超时，检查网络状态或刷新重试！')
    }

    return Promise.reject(error)
  })
  // 发送真正的网络请求
  return instance(config);
}

export default request
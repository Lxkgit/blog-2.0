import { request } from "@/api/request";

const api = {
  get(url: any, params?: any) {
    const config: any = {
      method: 'get',
      url: url
    }
    if (params) config.params = params
    return request(config)
  },
  getFile(url: any, params?: any) {
    const config: any = {
      method: 'get',
      url: url,
      responseType: 'blob'
    }
    if (params) config.params = params
    return request(config)
  },
  post(url: any, params?: any) {
    const config: any = {
      method: 'post',
      url: url
    }
    if (params) config.data = params
    return request(config)
  },
  put(url: any, params?: any) {
    const config: any = {
      method: 'put',
      url: url
    }
    if (params) config.data = params
    return request(config)
  },
  patch(url: any, params?: any) {
    const config: any = {
      method: 'patch',
      url: url
    }
    if (params) config.data = params
    return request(config)
  },
  delete(url: any, params?: any) {
    const config: any = {
      method: 'delete',
      url: url
    }
    if (params) config.params = params
    return request(config)
  }
}

export default api
import api from "@/api/api"

// 用户登录接口
export const userLoginApi = (username: string, password: string) => {
  const uri = "/auth/oauth/token?grant_type=password&client_id=system&client_secret=system&username=" + username + "&password=" + password
  return api.post(uri)
}



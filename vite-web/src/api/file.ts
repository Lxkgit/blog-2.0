import api from "@/api/api"

// 上传文件接口
export const upload = (params: any) => {
    const uri = "/file/upload"
    return api.post(uri, params)
}
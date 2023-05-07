import api from "@/api/api"

// 上传文件接口
export const upload = (params: any) => {
    const uri = "/file/files/upload"
    return api.post(uri, params)
}
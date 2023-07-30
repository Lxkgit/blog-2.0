import api from "@/api/api"

// 上传文件接口
export const uploadApi = (params: any) => {
    const uri = "/file/upload"
    return api.post(uri, params)
}


// 博客数据接口
export const selectBlogDataApi = () => {
    const uri = "/file/data"
    return api.get(uri)
}

// --------------设置接口-------------
export const selectBlogSettingApi = (params: any) => {
    const uri = "/file/setting/select"
    return api.get(uri, params)
}

export const updateBlogSettingApi = (params: any) => {
    const uri = "/file/setting/update"
    return api.post(uri, params)
}
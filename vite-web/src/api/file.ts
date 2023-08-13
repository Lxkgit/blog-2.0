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

export const selectBlogSettingByIdApi = (id: any) => {
    const uri = "/file/setting/id?id=" + id
    return api.get(uri)
}

export const selectBlogSettingApi = (params: any) => {
    const uri = "/file/setting/select"
    return api.get(uri, params)
}

export const updateBlogSettingApi = (params: any) => {
    const uri = "/file/setting/update"
    return api.post(uri, params)
}


// --------------文件服务接口-------------

export const saveFileDirApi = (id: any, params: any) => {
    const uri = "/file/dir/save"
    return api.post(uri, params)
}

export const deleteFileDirOrFileApi = (params: any) => {
    const uri = "/file/dir/delete"
    return api.delete(uri, params)
}

export const updateFileDirOrFileApi = (params: any) => {
    const uri = "/file/dir/update"
    return api.delete(uri, params)
}

export const selectFileDirOrFileApi = (params: any) => {
    const uri = "/file/dir/select"
    return api.get(uri, params)
}

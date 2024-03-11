import api from "@/api/api"

// 上传文件接口
export const uploadApi = (params: any) => {
    const uri = "/file/upload"
    return api.post(uri, params)
}

// 导入日记
export const importDiaryApi = (params: any) => {
    const uri = "/file/upload/diary/import"
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

export const saveFileDirApi = (params: any) => {
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

export const syncFileApi = (params: any) => {
    const uri = "/file/dir/sync"
    return api.get(uri, params)
}

// --------------服务器设备接口-------------

export const saveDeviceApi = (params: any) => {
    const uri = "/file/device/save"
    return api.post(uri, params)
}

export const deleteDeviceApi = (params: any) => {
    const uri = "/file/device/delete"
    return api.delete(uri, params)
}

export const updateDeviceApi = (params: any) => {
    const uri = "/file/device/update"
    return api.post(uri, params)
}

export const selectDeviceListApi = () => {
    const uri = "/file/device/list"
    return api.get(uri)
}

export const selectDeviceByIdApi = (params: any) => {
    const uri = "/file/device/id"
    return api.get(uri, params)
}

// --------------单片机接口-------------

export const saveChipApi = (params: any) => {
    const uri = "/file/chip/save"
    return api.post(uri, params)
}

export const deleteChipApi = (params: any) => {
    const uri = "/file/chip/delete"
    return api.delete(uri, params)
}

export const updateChipApi = (params: any) => {
    const uri = "/file/chip/update"
    return api.post(uri, params)
}

export const selectChipListApi = (params: any) => {
    const uri = "/file/chip/list"
    return api.get(uri, params)
}

export const selectChipByIdApi = (params: any) => {
    const uri = "/file/chip/id"
    return api.get(uri, params)
}

// --------------传感器接口-------------

export const saveSensorApi = (params: any) => {
    const uri = "/file/sensor/save"
    return api.post(uri, params)
}

export const deleteSensorApi = (params: any) => {
    const uri = "/file/sensor/delete"
    return api.delete(uri, params)
}

export const updateSensorApi = (params: any) => {
    const uri = "/file/sensor/update"
    return api.post(uri, params)
}

export const selectSensorListApi = (params: any) => {
    const uri = "/file/sensor/list"
    return api.get(uri, params)
}

export const selectSensorByIdApi = (params: any) => {
    const uri = "/file/sensor/id"
    return api.get(uri, params)
}

// --------------传感器控制接口-------------


export const saveSensorControlApi = (params: any) => {
    const uri = "/file/sensor/control/save"
    return api.post(uri, params)
}

export const deleteSensorControlApi = (params: any) => {
    const uri = "/file/sensor/control/delete"
    return api.delete(uri, params)
}

export const updateSensorControlApi = (params: any) => {
    const uri = "/file/sensor/control/update"
    return api.post(uri, params)
}

export const selectSensorControlListApi = (params: any) => {
    const uri = "/file/sensor/control/list"
    return api.get(uri, params)
}

export const selectSensorControlByIdApi = (id: any) => {
    const uri = "/file/sensor/control/id=" + id
    return api.get(uri)
}

export const sendSensorControlApi = (params: any) => {
    const uri = "/file/sensor/control/send"
    return api.get(uri, params)
}

// --------------传感器数据接口-------------

export const selectSensorDataListApi = (params: any) => {
    const uri = "/file/sensor/data"
    return api.get(uri, params)
}

// --------------传感器类型接口-------------

export const selectSensorTypeListApi = () => {
    const uri = "/file/sensor/type/list"
    return api.get(uri)
}
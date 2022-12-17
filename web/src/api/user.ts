import service from "../axios/axios"

// 用户列表接口
export const userListApi = (page: any, size: any) => {
    return service({
        method: 'GET',
        url: "/user/user/list?page=" + page + "&size=" + size
    })
}

// 角色列表接口
export const roleListApi = (page: any, size: any) => {
    return service({
        method: 'GET',
        url: "/user/role/list?page=" + page + "&size=" + size
    })
}

export const selectRolePerListApi = (roleId: any) => {
    return service({
        method: 'GET',
        url: "/user/role//permission/select?roleId=" + roleId
    })
}
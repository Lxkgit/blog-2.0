import service from "../axios/axios"

// 用户列表接口
export const userListApi = (page: any, size: any) => {
    return service({
        method: 'GET',
        url: "/user/user/list?page=" + page + "&size=" + size
    })
}

export const updateUserApi = (userDate: any) => {
    return service({
        method: 'POST',
        url: "/user/user/permission/update",
        data: userDate
    })
}

// 角色列表接口
export const roleListApi = (page: any, size: any) => {
    return service({
        method: 'GET',
        url: "/user/role/list?page=" + page + "&size=" + size
    })
}

export const selectRolePerListApi = (roleId: any, menuType: any) => {
    return service({
        method: 'GET',
        url: "/user/role/permission/select?roleId=" + roleId + "&menuType=" + menuType
    })
}

export const updateRolePerApi = (rolePer: any) => {
    return service({
        method: 'POST',
        url: "/user/role/permission/update",
        data: rolePer
    })
}

// 查询开通文档功能用户
export const selectDocUserListApi = () => {
    return service({
        method: 'GET',
        url: "/user/user/doc/user"
    })
}
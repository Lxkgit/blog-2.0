import service from "../axios/axios"

export const menuApi = (menuType: any) => {
    return service({
        method: 'GET',
        url: "/user/menu/list?type=" + menuType
    })
}

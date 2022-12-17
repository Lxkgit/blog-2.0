import service from "../axios/axios"

export const menuApi = () => {
    return service({
        method: 'GET',
        url: "/user/menu/list"
    })
}

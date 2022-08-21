import service from "../axios/axios"

export const getMenuApi = () => {
    return service({
        method: 'GET',
        url: "/user/menu/list"
    })
}

import service from "../axios/axios"

/**
 * 获取用户菜单
 * @param menuType 菜单类型
 * @returns 
 */
export const menuApi = (menuType: any) => {
    return service({
        method: 'GET',
        url: "/user/menu/list?type=" + menuType
    })
}

/**
 * 获取全部菜单
 * @param menuType 菜单类型
 * @returns 
 */
 export const allMenuApi = (menuType: any) => {
    return service({
        method: 'GET',
        url: "/user/menu/all/list?type=" + menuType
    })
}
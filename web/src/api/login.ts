import service from "../axios/axios"

export const userLoginApi = (username: string, password: string) => {
    return service({
        method: 'POST',
        url: "/auth/oauth/token?grant_type=password&client_id=system&client_secret=system&username=" + username + "&password=" + password 
    })
}

export const selectUserById = (userId: any) => {
    return service({
        method: 'GET',
        url: "/user/user/select/user/id?userId=" + userId
    })
}
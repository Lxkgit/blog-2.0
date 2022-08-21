import service from "../axios/axios"

export const userLoginApi = (username: string, password: string) => {
    return service({
        method: 'POST',
        url: "/auth/oauth/token?grant_type=password&client_id=system&client_secret=system&username=" + username + "&password=" + password 
    })
}

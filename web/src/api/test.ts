import service from "../axios/axios"

export const getBlogList = () => {
    return service({
        method: 'GET',
        url: "/content/article/list/1/2"

    })
}


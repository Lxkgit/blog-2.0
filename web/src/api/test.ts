import service from "../axios/axios"

export const getBlogList = (page: number, size: number) => {
    return service({
        method: 'GET',
        url: "/content/article/list/" + page + "/" + size

    })
}

export const getBlogType = () => {
    return service({
        method: 'GET',
        url: "/content/article/type/tree"
    })
}


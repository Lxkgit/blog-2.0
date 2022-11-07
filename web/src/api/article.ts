import service from "../axios/axios"

export const getBlogList = (param) => {
    return service({
        method: 'GET',
        url: "/content/article/list",
        params: param

    })
}

export const saveArticle = (article) => {
    return service({
        method: 'POST',
        url: "/content/article/save",
        data: article
    })
}

export const getBlogType = () => {
    return service({
        method: 'GET',
        url: "/content/article/type/tree"
    })
}

export const getBlogById = (id: any) => {
    return service({
        method: 'GET',
        url: "/content/article/id?id=" + id 
    })
}

export const getDiaryList = (month) => {
    return service({
        method: 'GET',
        url: "/content/diary/select?dateMonth=" + month,
    })
}


import service from "../axios/axios"

export const getBlogList = (param: any) => {
    return service({
        method: 'GET',
        url: "/content/article/list",
        params: param
    })
}

export const saveArticle = (article: any) => {
    return service({
        method: 'POST',
        url: "/content/article/save",
        data: article
    })
}

export const updateArticle = (article: any) => {
    return service({
        method: 'POST',
        url: "/content/article/update",
        data: article
    })
}

export const deleteArticleByIds = (ids: any) => {
    return service({
        method: 'DELETE',
        url: "/content/article/delete?articleIds=" + ids,
    })
} 

export const getArticleType = () => {
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

export const getDiaryList = (month: any) => {
    return service({
        method: 'GET',
        url: "/content/diary/select?dateMonth=" + month,
    })
}


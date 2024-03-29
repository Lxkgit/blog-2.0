import { config } from "process"
import service from "../axios/axios"

// 文章相关接口
export const getArticleList = (param: any) => {
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

export const getArticleById = (id: any) => {
    return service({
        method: 'GET',
        url: "/content/article/id?id=" + id 
    })
}

// 上传图片接口
export const upload = (params: any) => {
    return service({
        method: 'POST',
        url: "/file/files/upload",
        data: params
    })
}

// 文章分类相关接口
export const getArticleType = () => {
    return service({
        method: 'GET',
        url: "/content/article/type/tree"
    })
}

// 文章标签相关接口
export const getArticleLabel = () => {
    return service({
        method: 'GET',
        url: "/content/article/label/list"
    })
}

//  日记相关接口
export const getDiaryList = (param: any) => {
    return service({
        method: 'GET',
        url: "/content/diary/list",
        params: param
    })
}

export const saveDiary = (diary: any) => {
    return service({
        method: 'POST',
        url: "/content/diary/save",
        data: diary
    })
}

export const updateDiary = (diary: any) => {
    return service({
        method: 'POST',
        url: "/content/diary/update",
        data: diary
    })
}

export const deleteDiaryByIds = (ids: any) => {
    return service({
        method: 'DELETE',
        url: "/content/diary/delete?ids=" + ids,
    })
} 

export const importDiary = (param: any) => {
    return service({
        method: 'POST',
        url: "/file/files/diary/import",
        data: param
    })
}

// 文档接口
export const getDocCatalogList = (param: any) => {
    return service({
        method: 'GET',
        url: "/content/doc/catalog/list",
        params: param
    })
}

export const getDocCatalogListById = (param: any) => {
    return service({
        method: 'GET',
        url: "/content/doc/catalog/id",
        params: param
    })
}

export const createCatalog = (catalog: any) => {
    return service({
        method: 'POST',
        url: "/content/doc/catalog/save",
        data: catalog
    })
}

export const updateCatalog = (catalog: any) => {
    return service({
        method: 'POST',
        url: "/content/doc/catalog/update",
        data: catalog
    })
}

export const selectDocContent = (id: any) => {
    return service({
        method: 'GET',
        url: "/content/doc/catalog/select/content?id=" + id,
    })
}

export const saveDocContent = (content: any) => {
    return service({
        method: 'POST',
        url: "/content/doc/catalog/save/content",
        data: content
    })
}

export const updateDocContent = (content: any) => {
    return service({
        method: 'POST',
        url: "/content/doc/catalog/update/content",
        data: content
    })
}

export const deleteDocByIds = (ids: any) => {
    return service({
        method: 'DELETE',
        url: "/content/doc/catalog/delete?ids=" + ids,
    })
} 
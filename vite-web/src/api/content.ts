import api from "@/api/api"

// 获取文章列表
export const getArticleListApi = (params: any) => {
  const uri = "/content/article/list"
  return api.get(uri, params)
}

// 根据文章id获取文章信息
export const getArticleByIdApi = (id: any) => {
  const uri = "/content/article/id?id=" + id
  return api.get(uri)
}

// 保存文章
export const saveArticleApi = (article: any) => {
  const uri = "/content/article/save"
  return api.post(uri, article)
}

// 修改文章
export const updateArticleApi = (article: any) => {
  const uri = "/content/article/update"
  return api.post(uri, article)
}

// 根据id删除文章
export const deleteArticleByIdsApi = (ids: any) => {
  const uri = "/content/article/delete?articleIds=" + ids
  return api.delete(uri)
} 

// 获取文章分类树接口
export const getArticleTypeTreeApi = () => {
  const uri = "/content/article/type/tree"
  return api.get(uri)
}

// 通过id获取文章分类树接口
export const getArticleTypeByIdApi = (id: any) => {
  const uri = "/content/article/type/id?id=" + id
  return api.get(uri)
}

// 获取文章标签
export const getArticleLabelApi = () => {
  const uri = "/content/article/label/list"
  return api.get(uri)
}
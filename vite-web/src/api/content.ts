import api from "@/api/api"

// 获取文章列表
export const getArticleList = (params: any) => {
  const uri = "/content/article/list"
  return api.get(uri, params)
}

// 根据文章id获取文章信息
export const getArticleById = (id: any) => {
  const uri = "/content/article/id?id=" + id
  return api.get(uri)
}

// 获取文章分类树接口
export const getArticleTypeTree = () => {
  const uri = "/content/article/type/tree"
  return api.get(uri)
}

// 通过id获取文章分类树接口
export const getArticleTypeById = (id: any) => {
  const uri = "/content/article/type/id?id=" + id
  return api.get(uri)
}
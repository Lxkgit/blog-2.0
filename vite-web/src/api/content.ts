import api from "@/api/api"

// 获取文章列表
export const getArticleList = (params: any) => {
  const uri = "/content/article/list"
  return api.get(uri, params)
}

export const getArticleById = (id: any) => {
  const uri = "/content/article/id?id=" + id
  return api.get(uri)
}

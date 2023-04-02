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
// --------------文章分类-------------
/**
 * 保存文章分类
 * @param articleType 
 * @returns 
 */
export const saveArticleTypeApi = (articleType: any) => {
  const uri = "/content/article/type/save"
  return api.post(uri, articleType)
}

/**
 * 修改文章分类
 * @param articleType 
 * @returns 
 */
export const updateArticleTypeApi = (articleType: any) => {
  const uri = "/content/article/type/update"
  return api.post(uri, articleType)
}

/**
 * 根据id删除文章分类
 * @param ids 
 * @returns 
 */
export const deleteArticleTypeByIdsApi = (ids: any) => {
  const uri = "/content/article/type/delete?articleTypeId=" + ids
  return api.delete(uri)
}

// --------------文章标签分类-------------

export const getArticleLabelTypeListApi = () => {
  const uri = "/content/label/type/list"
  return api.get(uri)
}

// 通过id获取文章分类树接口
export const getArticleTypeByIdApi = (id: any) => {
  const uri = "/content/article/type/id?id=" + id
  return api.get(uri)
}

/**
 * 保存文章标签分类接口
 * @param labelType 
 * @returns 
 */
export const saveArticleLabelTypeApi = (labelType: any) => {
  const uri = "/content/label/type/save"
  return api.post(uri, labelType)
}

/**
 * 根据id删除文章标签分类
 * @param ids 
 * @returns 
 */
export const deleteArticleLabelTypeByIdsApi = (ids: any) => {
  const uri = "/content/label/type/delete?articleLabelTypeIds=" + ids
  return api.delete(uri)
}

// --------------文章标签-------------
/**
 * 根据标签分类查询标签，标签分类为0查询全部标签
 * @returns 
 */
export const getArticleLabelListApi = (labelType: any) => {
  const uri = "/content/article/label/list?labelType=" + labelType
  return api.get(uri)
}

/**
 * 保存文章标签接口
 * @param label 
 * @returns 
 */
export const saveArticleLabelApi = (label: any) => {
  const uri = "content/article/label/save"
  return api.post(uri, label)
}

/**
 * 根据id删除文章标签
 * @param ids 
 * @returns 
 */
export const deleteArticleLabelByIdsApi = (ids: any) => {
  const uri = "/content/article/label/delete?ids=" + ids
  return api.delete(uri)
}
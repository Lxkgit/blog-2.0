import api from "@/api/api"

// --------------文章接口-------------
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

// --------------日记-------------
/**
 * 分页查询日记接口
 * @param params 
 * @returns 
 */
export const getDiaryListApi = (params: any) => {
  const uri = "/content/diary/list"
  return api.get(uri, params)
}

/**
 * 保存日记接口
 * @param diary 
 * @returns 
 */
export const saveDiaryApi = (diary: any) => {
  const uri = "/content/diary/save"
  return api.post(uri, diary)
}

/**
 * 修改日记接口
 * @param diary 
 * @returns 
 */
export const updateDiaryApi = (diary: any) => {
  const uri = "/content/diary/update"
  return api.post(uri, diary)
}

// 根据id删除文章
export const deleteDiaryByIdsApi = (ids: any) => {
  const uri = "/content/diary/delete?ids=" + ids
  return api.delete(uri)
} 

// --------------文档-------------
/**
 * 获取用户文档接口
 * @param userId 
 * @returns 
 */
export const getDocCatalogTreeApi = (param: any) => {
  const uri = "/content/doc/catalog/tree"
  return api.get(uri, param)
}

/**
 * 根据文档目录id获取文档内容
 * @param id 
 * @returns 
 */
export const getDocContentByIdApi = (id: any) => {
  const uri = "/content/doc/content/id?id=" + id
  return api.get(uri)
}

/**
 * 根据文档目录id获取目录
 * @param id 
 * @returns 
 */
export const getDocCatalogByIdApi = (id: any) => {
  const uri = "/content/doc/catalog/id?id=" + id
  return api.get(uri)
}

/**
 * 创建文档目录接口
 * @param catalog 
 * @returns 
 */
export const saveDocCatalogApi = (catalog: any) => {
  const uri = "/content/doc/content/insert"
  return api.put(uri, catalog)
}

/**
 * 删除文档接口
 * @param id 
 * @returns 
 */
export const deleteDocApi = (id: any) => {
  const uri = "/content/doc/content/delete?id=" + id
  return api.delete(uri)
}

/**
 * 修改文档目录接口
 * @param catalog 
 */
export const updateCatalogApi = (catalog: any) => {
  const uri = "/content/doc/catalog/update"
  return api.post(uri, catalog)
}

/**
 * 修改文档内容接口
 * @param content 
 * @returns 
 */
export const updateContentApi = (content: any) => {
  const uri = "/content/doc/content/update"
  return api.post(uri, content)
}

export const selectDocUserListApi = () => {
  const uri = "/content/doc/content/user"
  return api.get(uri)
}
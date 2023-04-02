import api from "@/api/api"

// 角色列表接口
export const roleListApi = (page: any, size: any) => {
  const uri = "/user/role/list?page=" + page + "&size=" + size
  return api.get(uri)
}

// 创建角色接口
export const createRoleApi = (roleDate: any) => {
  const uri = "/user/role/save"
  return api.post(uri, roleDate)
}

// 修改角色接口
export const updateRoleApi = (roleDate: any) => {
  const uri = "/user/role/update"
  return api.post(uri, roleDate)
}

// 删除角色接口
export const deleteRoleApi = (ids: any) => {
  const uri = "/user/role/update?ids=" + ids
  return api.delete(uri)
}

// 查询角色权限列表接口
export const selectRolePerListApi = (roleId: any, menuType: any) => {
  const uri = "/user/role/permission/select?roleId=" + roleId + "&menuType=" + menuType
  return api.get(uri)
}

// 修改角色权限接口
export const updateRolePerApi = (rolePer: any) => {
  const uri = "/user/role/permission/update"
  return api.post(uri, rolePer)
}


// 用户列表接口
export const userListApi = (page: any, size: any) => {
  const uri = "/user/user/list?page=" + page + "&size=" + size
  return api.get(uri)
}

// 修改用户权限
export const updateUserApi = (userDate: any) => {
  const uri = "/user/user/permission/update"
  return api.post(uri, userDate)
}

/**
 * 获取全部菜单
 * menuType = 1 获取到目录
 * menuType = 2 获取到操作
 * @param menuType 
 * @returns 
 */
export const allMenuApi = (menuType: any) => {
  const uri = "/user/menu/all/list?type=" + menuType
  return api.get(uri)
}

/**
 * 获取用户菜单
 * menuType = 1 获取到目录
 * menuType = 2 获取到操作
 * @param menuType 
 * @returns 
 */
export const userMenuApi = (menuType: any) => {
  const uri = "/user/menu/list?type=" + menuType
  return api.get(uri)
}

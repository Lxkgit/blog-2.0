package com.blog.user.service.impl;

import com.blog.common.entity.user.SysPermission;
import com.blog.common.entity.user.SysRole;
import com.blog.common.entity.user.vo.SysPermissionVo;
import com.blog.user.dao.SysPermissionDAO;
import com.blog.user.service.SysPermissionService;
import com.blog.user.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: lxk
 * @date 2022/6/6 17:10
 * @description:
 */

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionDAO sysPermissionDAO;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public Set<SysPermission> selectPermissionByRoleIds(Set<Integer> roleIds, Integer menuType) {
        return sysPermissionDAO.selectPermissionByRoleIds(roleIds, menuType);
    }

    @Override
    public List<SysPermissionVo> selectPermissionListByUserId(Integer userId, Integer menuType) {
        Set<SysRole> sysRoles = sysRoleService.selectRoleByUserId(userId);
        Set<SysPermission> sysPermissionSet = sysPermissionDAO.selectPermissionByRoleIds(sysRoles.stream().map(SysRole::getId).collect(Collectors.toSet()), menuType);
        List<SysPermissionVo> sysPermissionList = new ArrayList<>();
        setPermissionTree(sysPermissionSet, sysPermissionList);
        return sysPermissionList;
    }

    @Override
    public List<SysPermissionVo> selectPermissionList(Integer menuType) {
        Set<SysPermission> sysPermissionSet = sysPermissionDAO.selectPermissionList(menuType);
        List<SysPermissionVo> sysPermissionList = new ArrayList<>();
        setPermissionTree(sysPermissionSet, sysPermissionList);
        return sysPermissionList;
    }

    private void setPermissionTree(Set<SysPermission> sysPermissionSet, List<SysPermissionVo> sysPermissionList){
        for (SysPermission sysPermission : sysPermissionSet) {
            SysPermissionVo sysPermissionVo = new SysPermissionVo();
            BeanUtils.copyProperties(sysPermission, sysPermissionVo);
            sysPermissionVo.setLabel(sysPermission.getMenuName());
            sysPermissionList.add(sysPermissionVo);
        }

        for (SysPermissionVo sysPermissionVo : sysPermissionList){
            if (sysPermissionVo.getParentId()!=0){
                for (SysPermissionVo permissionVo : sysPermissionList) {
                    if (permissionVo.getId().equals(sysPermissionVo.getParentId())) {
                        if (permissionVo.getList()==null){
                            permissionVo.setList(new ArrayList<>());
                        }
                        permissionVo.getList().add(sysPermissionVo);
                        Collections.sort(permissionVo.getList());
                        permissionVo.setChildren(permissionVo.getList());
                        break;
                    }
                }
            }
        }

        sysPermissionList.removeIf(sysPermissionVo -> sysPermissionVo.getParentId() != 0);
        Collections.sort(sysPermissionList);
    }
}

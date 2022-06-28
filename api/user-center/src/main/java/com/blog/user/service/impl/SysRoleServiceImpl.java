package com.blog.user.service.impl;


import com.blog.common.entity.user.SysRole;
import com.blog.common.entity.user.vo.SysRoleVo;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.user.dao.SysRoleDAO;
import com.blog.user.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: lxk
 * @date 2022/6/6 17:11
 * @description:
 */


@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDAO sysRoleDAO;

    @Override
    public Set<SysRole> selectRoleByUserId(Integer userId) {
        return sysRoleDAO.selectRoleByUserId(userId);
    }

    @Override
    public MyPage<SysRole> selectRoleByPage(int page, int size) {
        MyPage<SysRole> myPage = null;
        List<SysRole> roleList = sysRoleDAO.selectRoleListByPage((page-1)*size, size);

        try {
            int count = sysRoleDAO.selectRoleCount();
            myPage = MyPageUtils.pageUtil(roleList, page, size, count);
        } catch (Exception e){
            e.printStackTrace();
        }
        return myPage;
    }

    @Override
    public int saveRole(SysRole sysRole) {
        return sysRoleDAO.insert(sysRole);
    }

    @Override
    public int updateRole(SysRole sysRole) {
        return sysRoleDAO.updateRole(sysRole);
    }

    @Override
    public Map<String, Object> selectRolePermission(Integer roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        List<Integer> perIds = sysRoleDAO.selectRolePermission(roleId);
        map.put("perIds", perIds);
        return map;
    }

    @Override
    public Map<String, Object> deleteRoleByIds(String roleIds) {
        HashMap<String, Object> map = new HashMap<>();
        String[] ids = roleIds.split(",");
        int num = ids.length;
        int del = sysRoleDAO.deleteRoleByIds(ids);
        map.put("delete", num);
        map.put("success", del);
        return map;
    }

    @Override
    public Map<String, Object> updateRolePermission(SysRoleVo sysRoleVo) {
        HashMap<String, Object> map = new HashMap<>();
        int del = sysRoleDAO.deleteRolePermission(sysRoleVo.getId());
        map.put("delete", del);
        List<Integer> perIds = sysRoleVo.getPerIds();
        if (perIds.size()>0){
            int save = sysRoleDAO.saveRolePermission(sysRoleVo.getId(), perIds);
            map.put("save", save);
        }
        return map;
    }
}

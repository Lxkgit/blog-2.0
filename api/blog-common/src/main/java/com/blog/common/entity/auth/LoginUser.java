package com.blog.common.entity.auth;


import com.blog.common.entity.user.BlogUser;
import com.blog.common.entity.user.SysRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: lxk
 * @date 2022/6/10 10:16
 * @description: spring security 当前登录用户信息
 */

@Getter
@Setter
public class LoginUser extends BlogUser implements UserDetails {

    private static final long serialVersionUID = -5650242260144561959L;

    private Boolean enable;

    private Set<SysRole> sysRoles;

    private Set<String> permissions;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (!CollectionUtils.isEmpty(sysRoles)) {
            sysRoles.forEach(role -> {
                if (role.getRoleCode().startsWith("ROLE_")) {
                    collection.add(new SimpleGrantedAuthority(role.getRoleCode()));
                } else {
                    collection.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()));
                }
            });
        }

        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.forEach(per -> {
                collection.add(new SimpleGrantedAuthority(per));
            });
        }

        return collection;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        setEnable(getStatus() == 1);
        return getEnable();
    }
}

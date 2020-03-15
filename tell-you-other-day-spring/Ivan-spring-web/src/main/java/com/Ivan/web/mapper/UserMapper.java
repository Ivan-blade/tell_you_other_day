package com.Ivan.web.mapper;


import com.Ivan.web.bean.Role;
import com.Ivan.web.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User loadUserByUsername(@Param("username") String username);

    Long loadUserByMail(@Param("email") String email);

    long reg(User user);

    int updateUserEmail(@Param("email") String email, @Param("id") Long id);

    int updateUserMatch(@Param("userId") Long userId,@Param("otherId") Long otherId);

    List<Role> getAllRole();

    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

    int deleteUserById(Long uid);

    int deleteUserRolesByUid(Long id);

    int setUserRoles(@Param("rids") Long[] rids, @Param("id") Long id);

    User getUserById(@Param("id") Long id);
}

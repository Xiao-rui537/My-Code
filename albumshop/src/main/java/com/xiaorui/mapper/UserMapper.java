package com.xiaorui.mapper;

import com.xiaorui.beans.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * UserMapper 接口
 * 定义与用户相关的数据库操作方法。
 */
@Mapper
public interface UserMapper {

    /**
     * 添加用户。
     * @param user 用户对象
     */
    public void addUser(User user);

    /**
     * 根据用户名判断用户是否存在。
     * @param uname 用户名
     * @return 用户 ID，如果不存在则返回 null
     */
    public Integer identify(String uname);

    /**
     * 根据用户名获取用户 ID。
     * @param uname 用户名
     * @return 用户 ID
     */
    public Integer getUidByUname(String uname);

    /**
     * 用户登录。
     * @param uname 用户名
     * @return 用户对象
     */
    public User login(String uname);

    /**
     * 根据用户 ID 查询用户密码。
     * @param uid 用户 ID
     * @return 用户密码
     */
    public String queryUpwd(Integer uid);

    /**
     * 查询所有用户。
     * @return 用户列表
     */
    public List<User> queryUser();

    /**
     * 查询用户总数。
     * @return 用户总数
     */
    public Integer queryCountOfUser();

    /**
     * 根据用户 ID 删除用户。
     * @param uid 用户 ID
     */
    public void deleteUser(Integer uid);

    /**
     * 根据用户账号查询用户信息。
     * @param userAccount 用户账号
     * @return 用户对象
     */
    public User queryUserByAccount(String userAccount);

    /**
     * 根据用户账号查找用户（用于登陆）。
     * @param userAccount 用户账号
     * @return 用户对象
     */
    public User findUserByAccount(String userAccount);

    /**
     * 根据用户 ID 更新用户信息。
     * @param user 用户对象
     * @return 更新的记录数
     */
    public Integer updateUserById(User user);
}


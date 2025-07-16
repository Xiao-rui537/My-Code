package com.xiaorui.service;

import com.xiaorui.beans.User;
import java.util.List;
import java.util.Map;

/**
 * UserService 接口
 * 定义与用户相关的业务逻辑方法。
 */
public interface UserService {

    /**
     * 用户注册。
     * @param formData 包含注册信息的表单数据
     * @return 是否注册成功
     */
    public Boolean register(Map<String, Object> formData);

    /**
     * 根据用户 ID 删除用户。
     * @param uid 用户 ID
     * @return 是否删除成功
     */
    public boolean deleteUser(Integer uid);

    /**
     * 根据用户账号查询用户信息。
     * @param userAccount 用户账号
     * @return 用户对象
     */
    public User queryUserByAccount(String userAccount);

    /**
     * 根据用户账号查找用户(用于登陆)。
     * @param userAccount 用户账号
     * @return 用户对象
     */
    public User findUserByAccount(String userAccount);

    /**
     * 更新用户信息。
     * @param user 用户对象
     * @return 是否更新成功
     */
    public boolean updateUser(User user);

    /**
     * 查询所有用户。
     * @return 用户列表
     */
    public List<User> getUsers();

    /**
     * 添加用户。
     * @param user 用户对象
     * @return 是否添加成功
     */
    public boolean addUser(User user);
}
package com.xiaorui.service.impl;

import com.xiaorui.beans.User;
import com.xiaorui.mapper.UserMapper;
import com.xiaorui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UserServiceImpl 类
 * 实现了 UserService 接口，处理用户相关的业务逻辑。
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;

    /**
     * 构造方法，注入 UserMapper。
     * @param userMapper 用户映射器
     */
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户注册。
     * @param formData 包含用户注册信息的表单数据
     * @return 是否注册成功
     */
    @Override
    public Boolean register(Map<String, Object> formData) {
        try {
            // 提取表单数据中的字段并验证必填字段
            String account = (String) formData.get("account");
            String password = (String) formData.get("password");
            String username = (String) formData.get("username");
            String gender = (String) formData.get("gender");
            String birthdate = (String) formData.get("birthdate");
            String address = (String) formData.get("address");
            String phone = (String) formData.get("phone");

            if (isEmpty(account) || isEmpty(password) || isEmpty(username)) {
                logger.warn("Mandatory fields are missing: account={}, password={}, username={}", account, password, username);
                return false; // 如果缺少必填字段，则返回 false
            }

            User user = new User();
            user.setUser_account(account);
            user.setUser_password(password);
            user.setUser_name(username);
            user.setUser_gender(Optional.ofNullable(gender).orElse("Unknown"));
            user.setUser_hometown(Optional.ofNullable(address).orElse(""));
            user.setUser_phone(Optional.ofNullable(phone).orElse(""));
            user.setUser_isadmin(false);

            // 处理出生日期
            setUserBirthDate(birthdate, user);

            // 保存用户到数据库
            userMapper.addUser(user);

            logger.info("User registered successfully: {}", user);
            return true;

        } catch (Exception e) {
            logger.error("Error during user registration", e);
            return false; // 如果发生错误，则返回 false
        }
    }

    /**
     * 检查字符串是否为空。
     * @param str 要检查的字符串
     * @return 是否为空
     */
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 设置用户出生日期。
     * @param birthdate 出生日期字符串
     * @param user 用户对象
     */
    private void setUserBirthDate(String birthdate, User user) {
        if (birthdate != null) {
            try {
                // 使用 SimpleDateFormat 解析日期
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = formatter.parse(birthdate);
                // 格式化日期并设置为 Date 对象
                SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = outputFormatter.format(parsedDate);
                user.setUser_birth(outputFormatter.parse(formattedDate));
            } catch (ParseException e) {
                // 记录无效日期格式并设置为 null
                logger.warn("Invalid birthdate format: {}", birthdate);
                user.setUser_birth(null); // 可以设置为默认日期
            }
        } else {
            user.setUser_birth(null); // 未提供出生日期时设置为 null
        }
    }

    /**
     * 更新用户信息。
     * @param user 用户对象
     * @return 是否更新成功
     */
    @Override
    public boolean updateUser(User user) {
        try {
            boolean result = userMapper.updateUserById(user) > 0;
            if (result) {
                logger.info("User updated successfully: {}", user);
            } else {
                logger.warn("User update failed: {}", user);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return false;
        }
    }

    /**
     * 根据用户 ID 删除用户。
     * @param uid 用户 ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteUser(Integer uid) {
        try {
            userMapper.deleteUser(uid);
            logger.info("User deleted successfully with ID: {}", uid);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", uid, e);
            return false;
        }
    }

    /**
     * 根据用户账号查询用户信息。
     * @param userAccount 用户账号
     * @return 用户对象
     */
    @Override
    public User queryUserByAccount(String userAccount) {
        try {
            User user = userMapper.queryUserByAccount(userAccount);
            if (user != null) {
                logger.info("User queried successfully by account: {}", userAccount);
            } else {
                logger.warn("No user found with account: {}", userAccount);
            }
            return user;
        } catch (Exception e) {
            logger.error("Error querying user by account: {}", userAccount, e);
            return null;
        }
    }

    /**
     * 根据用户账号查找用户。
     * @param userAccount 用户账号
     * @return 用户对象
     */
    @Override
    public User findUserByAccount(String userAccount) {
        try {
            User user = userMapper.findUserByAccount(userAccount);
            if (user != null) {
                logger.info("User found successfully by account: {}", userAccount);
            } else {
                logger.warn("No user found with account: {}", userAccount);
            }
            return user;
        } catch (Exception e) {
            logger.error("Error finding user by account: {}", userAccount, e);
            return null;
        }
    }

    /**
     * 查询所有用户。
     * @return 用户列表
     */
    @Override
    public List<User> getUsers() {
        try {
            List<User> users = userMapper.queryUser();
            logger.info("User list retrieved successfully");
            return users;
        } catch (Exception e) {
            logger.error("Error fetching user list", e);
            return null;
        }
    }

    /**
     * 添加用户。
     * @param user 用户对象
     * @return 是否添加成功
     */
    @Override
    public boolean addUser(User user) {
        try {
            userMapper.addUser(user);
            logger.info("User added successfully: {}", user);
            return true;
        } catch (Exception e) {
            logger.error("Error adding user", e);
            return false;
        }
    }
}

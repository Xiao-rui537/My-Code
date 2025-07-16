package com.xiaorui.controller;

import com.xiaorui.beans.User;
import com.xiaorui.service.UserService;
import com.xiaorui.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaorui.utils.TokenUtil.generateToken;

/**
 * UserController 控制器类
 * 处理与用户相关的 HTTP 请求，包括注册、登录、更新和删除用户信息。
 */
@CrossOrigin // 解决跨域问题
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    /**
     * 构造方法，使用自动注入方式注入 UserService。
     * @param userService 用户服务类
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户注册接口。
     * @param formData 包含用户注册信息的表单数据
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseUtil register(@RequestBody Map<String, Object> formData) {
        try {
            boolean isSuccess = userService.register(formData);
            if (isSuccess) {
                logger.info("User registered successfully");
                return new ResponseUtil(200, "注册成功");
            } else {
                logger.warn("User registration failed: Invalid input");
                return new ResponseUtil(400, "注册失败，请检查输入");
            }
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            return new ResponseUtil(500, "服务器内部错误");
        }
    }

    /**
     * 用户登录接口。
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return 登录结果和用户令牌
     */
    @PostMapping("/login")
    public ResponseUtil login(@RequestParam("userAccount") String userAccount,
                              @RequestParam("userPassword") String userPassword) {
        try {
            User user = userService.findUserByAccount(userAccount);
            if (user == null) {
                logger.warn("Login failed: Account does not exist - userAccount: {}", userAccount);
                return new ResponseUtil(400, "账号不存在");
            }
            if (!userPassword.equals(user.getUser_password())) {
                logger.warn("Login failed: Incorrect password for userAccount: {}", userAccount);
                return new ResponseUtil(400, "密码错误");
            }
            String token = generateToken(userAccount, userPassword);
            boolean isAdmin = user.isUser_isadmin();
            String id = user.getUser_account();
            logger.info("User logged in successfully: {}", userAccount);
            return new ResponseUtil(200, "登录成功", token, isAdmin, id);
        } catch (Exception e) {
            logger.error("Error during user login", e);
            return new ResponseUtil(500, "服务器内部错误");
        }
    }

    /**
     * 更新用户信息。
     * @param user 用户对象
     * @return 更新结果
     */
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (user.getUser_birth() == null) {
                logger.warn("Birthdate is null for user: {}", user.getUser_account());
            }
            boolean isUpdated = userService.updateUser(user);
            if (isUpdated) {
                logger.info("User info updated successfully: {}", user.getUser_account());
                response.put("success", true);
                response.put("message", "User info updated successfully");
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Failed to update user info: {}", user.getUser_account());
                response.put("success", false);
                response.put("message", "Failed to update user info");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            logger.error("Error updating user info", e);
            response.put("success", false);
            response.put("message", "服务器内部错误");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 删除用户。
     * @param uid 用户 ID
     * @return 删除结果
     */
    @DeleteMapping("/deleteUser/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable int uid) {
        try {
            boolean result = userService.deleteUser(uid);
            if (result) {
                logger.info("User deleted successfully: ID {}", uid);
                return ResponseEntity.ok().body("用户删除成功");
            } else {
                logger.warn("Failed to delete user: ID {}", uid);
                return ResponseEntity.badRequest().body("删除失败");
            }
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", uid, e);
            return ResponseEntity.status(500).body("服务器内部错误");
        }
    }

    /**
     * 根据用户账号获取用户信息。
     * @param userAccount 用户账号
     * @return 用户信息
     */
    @GetMapping("/info{userAccount:.+}")
    public ResponseEntity<?> getUserByAccount(@PathVariable String userAccount) {
        try {
            User user = userService.queryUserByAccount(userAccount);
            if (user != null) {
                logger.info("User found for account: {}", userAccount);
                return ResponseEntity.ok().body(user);
            } else {
                logger.warn("User not found for account: {}", userAccount);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户未找到");
            }
        } catch (Exception e) {
            logger.error("Error fetching user by account: {}", userAccount, e);
            return ResponseEntity.status(500).body("服务器内部错误");
        }
    }

    /**
     * 获取所有用户信息。
     * @return 用户列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<User>> getUserList() {
        try {
            List<User> users = userService.getUsers();
            logger.info("Fetched all users successfully");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching user list", e);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 添加用户。
     * @param user 用户对象
     * @return 添加结果
     */
    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        Map<String, Object> res = new HashMap<>();
        try {
            if (user.getUser_birth() == null) {
                logger.warn("Birthdate is null for new user: {}", user.getUser_account());
            }
            boolean result = userService.addUser(user);
            if (result) {
                logger.info("User added successfully: {}", user.getUser_account());
                res.put("success", true);
                res.put("message", "User added successfully");
                return ResponseEntity.ok(res);
            } else {
                logger.warn("Failed to add user: {}", user.getUser_account());
                res.put("success", false);
                res.put("message", "Failed to add user");
                return ResponseEntity.status(400).body(res);
            }
        } catch (Exception e) {
            logger.error("Error adding user", e);
            res.put("success", false);
            res.put("message", "服务器内部错误");
            return ResponseEntity.status(500).body(res);
        }
    }
}

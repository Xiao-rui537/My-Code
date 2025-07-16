package com.xiaorui.beans;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * User 实体类
 * 对应数据库中的用户表，包含用户相关的字段和访问器。
 */
public class User {

    @TableId // 标注为主键字段
    private Integer user_id; // 用户ID，唯一标识

    private String user_account; // 用户账号
    private String user_name; // 用户名称
    private String user_password; // 用户密码
    private String user_gender; // 用户性别
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 指定前端接收日期格式
    private Date user_birth; // 用户出生日期
    private String user_hometown; // 用户家乡
    private String user_phone; // 用户联系电话
    private boolean user_isadmin; // 用户是否为管理员

    /**
     * 无参构造方法
     * 用于实例化对象时不传入任何参数
     */
    public User() {}

    // 访问器（Getter 和 Setter 方法）

    /**
     * 获取用户ID
     * @return 用户ID
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * 设置用户ID
     * @param user_id 用户ID
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * 获取用户账号
     * @return 用户账号
     */
    public String getUser_account() {
        return user_account;
    }

    /**
     * 设置用户账号
     * @param user_account 用户账号
     */
    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    /**
     * 获取用户名称
     * @return 用户名称
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * 设置用户名称
     * @param user_name 用户名称
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * 获取用户密码
     * @return 用户密码
     */
    public String getUser_password() {
        return user_password;
    }

    /**
     * 设置用户密码
     * @param user_password 用户密码
     */
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    /**
     * 获取用户性别
     * @return 用户性别
     */
    public String getUser_gender() {
        return user_gender;
    }

    /**
     * 设置用户性别
     * @param user_gender 用户性别
     */
    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    /**
     * 获取用户出生日期
     * @return 用户出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd") // 指定返回JSON的日期格式
    public Date getUser_birth() {
        return user_birth;
    }

    /**
     * 设置用户出生日期
     * @param user_birth 用户出生日期
     */
    public void setUser_birth(Date user_birth) {
        this.user_birth = user_birth;
    }

    /**
     * 获取用户家乡
     * @return 用户家乡
     */
    public String getUser_hometown() {
        return user_hometown;
    }

    /**
     * 设置用户家乡
     * @param user_hometown 用户家乡
     */
    public void setUser_hometown(String user_hometown) {
        this.user_hometown = user_hometown;
    }

    /**
     * 获取用户联系电话
     * @return 用户联系电话
     */
    public String getUser_phone() {
        return user_phone;
    }

    /**
     * 设置用户联系电话
     * @param user_phone 用户联系电话
     */
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    /**
     * 获取用户是否为管理员
     * @return 是否为管理员
     */
    public boolean isUser_isadmin() {
        return user_isadmin;
    }

    /**
     * 设置用户是否为管理员
     * @param user_isadmin 是否为管理员
     */
    public void setUser_isadmin(boolean user_isadmin) {
        this.user_isadmin = user_isadmin;
    }

    /**
     * 带参构造方法
     * 用于实例化对象时传入所有参数
     *
     * @param user_id 用户ID
     * @param user_account 用户账号
     * @param user_name 用户名称
     * @param user_password 用户密码
     * @param user_gender 用户性别
     * @param user_birth 用户出生日期
     * @param user_hometown 用户家乡
     * @param user_phone 用户联系电话
     * @param user_isadmin 是否为管理员
     */
    public User(Integer user_id, String user_account, String user_name, String user_password, String user_gender, Date user_birth, String user_hometown, String user_phone, boolean user_isadmin) {
        this.user_id = user_id;
        this.user_account = user_account;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_gender = user_gender;
        this.user_birth = user_birth;
        this.user_hometown = user_hometown;
        this.user_phone = user_phone;
        this.user_isadmin = user_isadmin;
    }

    /**
     * 重写 toString 方法
     * 用于返回对象的字符串表示
     *
     * @return 用户对象的字符串表示
     */
    @Override
    public String toString() {
        return "user{" +
                "user_id=" + user_id +
                ", user_account='" + user_account + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_birth='" + user_birth + '\'' +
                ", user_hometown='" + user_hometown + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_isadmin=" + user_isadmin +
                '}';
    }
}

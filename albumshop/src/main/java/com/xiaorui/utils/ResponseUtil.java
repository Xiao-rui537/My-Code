package com.xiaorui.utils;

/**
 * ResponseUtil 类
 * 封装响应信息：
 *  - 状态码 [必须]
 *  - 消息提示 [必须]
 *  - 携带数据（例如 Token）[可选]
 *  - 是否管理员标志 [可选]
 */
public class ResponseUtil {

    private String id;         // 用户 ID 或其他唯一标识符
    private Integer code;      // 状态码（如 200 表示成功，400 表示错误）
    private String msg;        // 消息提示（如 "成功" 或 "失败"）
    private String token;      // 携带的数据，例如认证 Token
    private Boolean isAdmin;   // 是否管理员标志

    /**
     * 获取状态码。
     * @return 状态码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置状态码。
     * @param code 状态码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取消息提示。
     * @return 消息提示
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置消息提示。
     * @param msg 消息提示
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取携带的数据（例如 Token）。
     * @return 携带的数据
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置携带的数据（例如 Token）。
     * @param token 携带的数据
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取是否管理员标志。
     * @return 是否管理员
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * 设置是否管理员标志。
     * @param isAdmin 是否管理员
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取 ID。
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置 ID。
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 默认构造函数。
     */
    public ResponseUtil() {}

    /**
     * 带所有字段的构造函数。
     * @param code 状态码
     * @param msg 消息提示
     * @param token 携带的数据
     * @param isAdmin 是否管理员
     * @param id 唯一标识符
     */
    public ResponseUtil(Integer code, String msg, String token, Boolean isAdmin, String id) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.isAdmin = isAdmin;
        this.id = id;
    }

    /**
     * 仅包含状态码和消息提示的构造函数。
     * @param code 状态码
     * @param msg 消息提示
     */
    public ResponseUtil(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 重写 toString 方法，返回对象的字符串表示。
     * @return 字符串表示的响应信息
     */
    @Override
    public String toString() {
        return "ResponseUtil{" +
                "id='" + id + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
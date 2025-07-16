package com.xiaorui.beans;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Order 实体类
 * 对应数据库中的 Order 表，表示订单信息。
 */
@TableName("Order") // 指定数据库表名
public class Order {

    @TableId // 标注为主键字段
    private Integer order_id; // 订单ID，唯一标识

    private String order_address; // 订单收货地址
    private String order_receiver; // 订单接收人姓名
    private String order_phone; // 接收人联系电话
    private String order_user_account; // 下单用户的账号
    private String order_pay_type; // 支付方式
    private Integer order_price; // 订单总价
    private Integer order_album_id; // 购买的专辑ID

    /**
     * 无参构造方法
     * 用于实例化对象时不传入任何参数
     */
    public Order() {
    }

    // 访问器（Getter 和 Setter 方法）

    /**
     * 获取订单ID
     * @return 订单ID
     */
    public Integer getOrder_id() {
        return order_id;
    }

    /**
     * 设置订单ID
     * @param order_id 订单ID
     */
    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    /**
     * 获取订单地址
     * @return 订单收货地址
     */
    public String getOrder_address() {
        return order_address;
    }

    /**
     * 设置订单地址
     * @param order_address 订单收货地址
     */
    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    /**
     * 获取订单接收人
     * @return 订单接收人姓名
     */
    public String getOrder_receiver() {
        return order_receiver;
    }

    /**
     * 设置订单接收人
     * @param order_receiver 订单接收人姓名
     */
    public void setOrder_receiver(String order_receiver) {
        this.order_receiver = order_receiver;
    }

    /**
     * 获取接收人联系电话
     * @return 接收人联系电话
     */
    public String getOrder_phone() {
        return order_phone;
    }

    /**
     * 设置接收人联系电话
     * @param order_phone 接收人联系电话
     */
    public void setOrder_phone(String order_phone) {
        this.order_phone = order_phone;
    }

    /**
     * 获取用户账号
     * @return 下单用户账号
     */
    public String getOrder_user_account() {
        return order_user_account;
    }

    /**
     * 设置用户账号
     * @param order_user_account 下单用户账号
     */
    public void setOrder_user_account(String order_user_account) {
        this.order_user_account = order_user_account;
    }

    /**
     * 获取支付方式
     * @return 支付方式
     */
    public String getOrder_pay_type() {
        return order_pay_type;
    }

    /**
     * 设置支付方式
     * @param order_pay_type 支付方式
     */
    public void setOrder_pay_type(String order_pay_type) {
        this.order_pay_type = order_pay_type;
    }

    /**
     * 获取订单总价
     * @return 订单总价
     */
    public Integer getOrder_price() {
        return order_price;
    }

    /**
     * 设置订单总价
     * @param order_price 订单总价
     */
    public void setOrder_price(Integer order_price) {
        this.order_price = order_price;
    }

    /**
     * 获取关联专辑ID
     * @return 关联专辑ID
     */
    public Integer getOrder_album_id() {
        return order_album_id;
    }

    /**
     * 设置关联专辑ID
     * @param order_album_id 购买的专辑ID
     */
    public void setOrder_album_id(Integer order_album_id) {
        this.order_album_id = order_album_id;
    }

    /**
     * 带参构造方法
     * 用于实例化对象时传入所有参数
     *
     * @param order_id 订单ID
     * @param order_address 订单收货地址
     * @param order_receiver 订单接收人姓名
     * @param order_phone 接收人联系电话
     * @param order_user_account 下单用户账号
     * @param order_pay_type 支付方式
     * @param order_price 订单总价
     * @param order_album_id 购买的专辑ID
     */
    public Order(Integer order_id, String order_address, String order_receiver, String order_phone, String order_user_account, String order_pay_type, Integer order_price, Integer order_album_id) {
        this.order_id = order_id;
        this.order_address = order_address;
        this.order_receiver = order_receiver;
        this.order_phone = order_phone;
        this.order_user_account = order_user_account;
        this.order_pay_type = order_pay_type;
        this.order_price = order_price;
        this.order_album_id = order_album_id;
    }

    /**
     * 重写 toString 方法
     * 用于返回对象的字符串表示
     *
     * @return 订单对象的字符串表示
     */
    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", order_address='" + order_address + '\'' +
                ", order_receiver='" + order_receiver + '\'' +
                ", order_phone='" + order_phone + '\'' +
                ", order_user_account='" + order_user_account + '\'' +
                ", order_pay_type='" + order_pay_type + '\'' +
                ", order_price=" + order_price +
                ", order_album_id=" + order_album_id +
                '}';
    }
}

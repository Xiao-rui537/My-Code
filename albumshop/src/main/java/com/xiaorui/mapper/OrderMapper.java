package com.xiaorui.mapper;

import com.xiaorui.beans.Order;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * OrderMapper 接口
 * 定义与订单相关的数据库操作方法。
 */
@Mapper
public interface OrderMapper {

    /**
     * 添加订单。
     * @param order 订单对象
     */
    public void addOrder(Order order);

    /**
     * 删除订单。
     * @param oid 订单 ID
     */
    public void deleteOrder(Integer oid);

    /**
     * 根据用户账号查询订单。
     * @param order_account 用户账号
     * @return 用户的订单列表
     */
    public List<Order> queryOrderByAccount(String order_account);

    /**
     * 查询所有订单。
     * @return 所有订单的列表
     */
    public List<Order> queryOrder();

    /**
     * 查询订单总数。
     * @return 订单总数
     */
    public Integer queryCountOfOrder();

    /**
     * 更新订单。
     * @param order 订单对象
     */
    public void updateOrder(Order order);
}


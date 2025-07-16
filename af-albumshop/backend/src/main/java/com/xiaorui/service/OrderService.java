package com.xiaorui.service;

import com.xiaorui.beans.Order;
import java.util.List;

/**
 * OrderService 接口
 * 定义与订单相关的业务逻辑方法。
 */
public interface OrderService {

    /**
     * 添加订单。
     * @param order 订单对象
     */
    public void addOrder(Order order);

    /**
     * 根据订单 ID 删除订单。
     * @param oid 订单 ID
     * @return 是否删除成功
     */
    public boolean deleteOrderByOid(Integer oid);

    /**
     * 查询所有订单。
     * @return 订单列表
     */
    public List<Order> queryOrder();

    /**
     * 更新订单信息。
     * @param order 订单对象
     * @return 是否更新成功
     */
    public boolean updateOrder(Order order);
}

package com.xiaorui.service.impl;

import com.xiaorui.beans.Order;
import com.xiaorui.mapper.OrderMapper;
import com.xiaorui.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OrderServiceImpl 类
 * 实现了 OrderService 接口，处理订单相关的业务逻辑。
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderMapper orderMapper;

    /**
     * 构造方法，注入 OrderMapper。
     * @param orderMapper 订单映射器
     */
    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * 添加订单。
     * @param order 订单对象
     */
    @Override
    public void addOrder(Order order) {
        try {
            // 添加订单
            orderMapper.addOrder(order);
            logger.info("Order added successfully: {}", order);
        } catch (Exception e) {
            logger.error("Error occurred while adding order: {}", order, e);
        }
    }

    /**
     * 根据订单 ID 删除订单。
     * @param oid 订单 ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteOrderByOid(Integer oid) {
        try {
            // 删除订单
            orderMapper.deleteOrder(oid);
            logger.info("Order deleted successfully with oid: {}", oid);
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while deleting order with oid: {}", oid, e);
            return false;
        }
    }

    /**
     * 查询所有订单。
     * @return 订单列表
     */
    @Override
    public List<Order> queryOrder() {
        try {
            List<Order> orders = orderMapper.queryOrder();
            logger.info("All orders queried successfully");
            return orders;
        } catch (Exception e) {
            logger.error("Error occurred while querying orders", e);
            return null;
        }
    }

    /**
     * 更新订单信息。
     * @param order 订单对象
     * @return 是否更新成功
     */
    @Override
    public boolean updateOrder(Order order) {
        try {
            orderMapper.updateOrder(order);
            logger.info("Order updated successfully: {}", order);
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while updating order: {}", order, e);
            return false;
        }
    }
}
package com.xiaorui.controller;

import com.xiaorui.beans.Order;
import com.xiaorui.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderController 控制器类
 * 处理与订单相关的 HTTP 请求，包括提交、查询、更新和删除订单。
 */
@Controller
@RequestMapping("/orders")
@CrossOrigin // 解决跨域问题
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    /**
     * 构造方法，使用自动注入方式注入 OrderService。
     * @param orderService 订单服务类
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 提交订单。
     * @param order 订单对象
     * @return 包含提交结果的响应
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitOrder(@RequestBody Order order) {
        Map<String, String> response = new HashMap<>();
        try {
            orderService.addOrder(order);
            response.put("message", "订单提交成功！");
            logger.info("Order submitted successfully: {}", order);
            return ResponseEntity.ok(response);  // 返回 HTTP 200 状态
        } catch (Exception e) {
            logger.error("Error submitting order: {}", e.getMessage(), e);
            response.put("message", "订单提交失败，请重试。");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 获取所有订单。
     * @return 订单列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.queryOrder();
            logger.info("Retrieved all orders successfully");
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching all orders: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 创建订单。
     * @param order 订单对象
     * @return 新创建的订单对象
     */
    @PostMapping("/add")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            orderService.addOrder(order);
            logger.info("Order created successfully: {}", order);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            logger.error("Error creating order: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误：" + e.getMessage());
        }
    }

    /**
     * 更新订单。
     * @param orderId 订单 ID
     * @param order 订单对象
     * @return 更新结果
     */
    @PutMapping("/update/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Integer orderId, @RequestBody Order order) {
        try {
            order.setOrder_id(orderId);
            boolean result = orderService.updateOrder(order);
            if (result) {
                logger.info("Order updated successfully: {}", order);
                return ResponseEntity.ok("订单信息更新成功");
            } else {
                logger.warn("Failed to update order with ID: {}", orderId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改失败");
            }
        } catch (Exception e) {
            logger.error("Error updating order with ID {}: {}", orderId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误：" + e.getMessage());
        }
    }

    /**
     * 删除订单。
     * @param orderId 订单 ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
        try {
            boolean result = orderService.deleteOrderByOid(orderId);
            if (result) {
                logger.info("Order deleted successfully with ID: {}", orderId);
                return ResponseEntity.noContent().build();  // 204
            } else {
                logger.warn("Failed to delete order with ID: {}", orderId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
            }
        } catch (Exception e) {
            logger.error("Error deleting order with ID {}: {}", orderId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误：" + e.getMessage());
        }
    }
}

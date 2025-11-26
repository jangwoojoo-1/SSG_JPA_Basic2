package com.ssg.lp.order.service;

import com.ssg.lp.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAll(Integer orderId);

    OrderItem find(Integer orderId, Integer itemId);

    void saveAll(List<OrderItem> orderItems);
}

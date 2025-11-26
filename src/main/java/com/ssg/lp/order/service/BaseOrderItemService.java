package com.ssg.lp.order.service;

import com.ssg.lp.order.entity.OrderItem;
import com.ssg.lp.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseOrderItemService implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll(Integer orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    @Override
    public OrderItem find(Integer orderId, Integer itemId) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderId);

        return orderItem.orElse(null);
    }

    @Override
    public void saveAll(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}

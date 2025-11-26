package com.ssg.lp.order.repository;

import com.ssg.lp.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findAllByOrderId(Integer orderId);

    Optional<OrderItem> findByOrderIdAndItemId(Integer orderId, Integer itemId);
}

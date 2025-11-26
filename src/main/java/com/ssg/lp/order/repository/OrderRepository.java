package com.ssg.lp.order.repository;

import com.ssg.lp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByMemberIdOrderByIdDesc(Integer memberId);
}


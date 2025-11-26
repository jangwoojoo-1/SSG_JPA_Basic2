package com.ssg.lp.order.service;

import com.ssg.lp.order.dto.OrderRead;
import com.ssg.lp.order.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    List<OrderRead> findAll(Integer memberId); // ①

    OrderRead find(Integer id, Integer memberId); // ②

    void order(OrderRequest orderReq, Integer memberId);
}

package com.ssg.lp.order.controller;

import com.ssg.lp.account.helper.AccountHelper;
import com.ssg.lp.order.dto.OrderRead;
import com.ssg.lp.order.dto.OrderRequest;
import com.ssg.lp.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lp")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AccountHelper accountHelper;

    @GetMapping("/api/orders")
    public ResponseEntity<?> readAll(HttpServletRequest request) {
        Integer memberId = accountHelper.getAccountId(request);

        List<OrderRead> orders = orderService.findAll(memberId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<?> read(HttpServletRequest request, @PathVariable Integer id) {
        Integer memberId = accountHelper.getAccountId(request);

        OrderRead order = orderService.find(id, memberId);

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/api/orders")
    public ResponseEntity<?> add(HttpServletRequest request, @RequestBody OrderRequest orderRequest) {
        Integer memberId = accountHelper.getAccountId(request);

        orderService.order(orderRequest, memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

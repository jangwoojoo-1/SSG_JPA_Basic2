package com.ssg.lp.order.service;

import com.ssg.lp.cart.dto.CartRead;
import com.ssg.lp.cart.service.CartService;
import com.ssg.lp.item.dto.ItemRead;
import com.ssg.lp.item.service.ItemService;
import com.ssg.lp.order.dto.OrderRead;
import com.ssg.lp.order.dto.OrderRequest;
import com.ssg.lp.order.entity.Order;
import com.ssg.lp.order.entity.OrderItem;
import com.ssg.lp.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final CartService cartService;

    @Override
    public List<OrderRead> findAll(Integer memberId) {
        return orderRepository.findAllByMemberIdOrderByIdDesc(memberId).stream().map(Order::toRead).toList();
    }

    @Override
    public OrderRead find(Integer id, Integer memberId) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            OrderRead order = orderOptional.get().toRead();

            List<OrderItem> orderItems = orderItemService.findAll(order.getId());

            List<Integer> orderItemIds = orderItems.stream().map(OrderItem::getItemId).toList();

            List<ItemRead> items = itemService.findAll(orderItemIds);

            order.setItems(items);
            return order;
        }
        return null;
    }

    @Override
    @Transactional
    public void order(OrderRequest orderRequest, Integer memberId) {
        List<CartRead> cart = cartService.findAll(memberId);
        orderRequest.setItemIds(cartService.findAll(memberId).stream().map(CartRead::getItemId).toList());
        List<ItemRead> items = itemService.findAll(orderRequest.getItemIds());
        long amount = 0L;

        for (ItemRead item : items) {
            amount += item.getPrice() - item.getPrice().longValue() * item.getDiscountPer() / 100;
        }

        orderRequest.setAmount(amount);

        Order order = orderRepository.save(orderRequest.toEntity(memberId));

        List<OrderItem> newOrderItems = new ArrayList<>();

        orderRequest.getItemIds().forEach((itemId) -> {
            OrderItem newOrderItem = new OrderItem(order.getId(), itemId);
            newOrderItems.add(newOrderItem);
        });

        orderItemService.saveAll(newOrderItems);

        cartService.removeAll(order.getMemberId());
    }
}

package com.ssg.lp.cart.service;

import com.ssg.lp.cart.dto.CartRead;
import com.ssg.lp.cart.entity.Cart;

import java.util.List;

public interface CartService {
    List<CartRead> findAll(Integer memberId);

    CartRead find(Integer memberId, Integer itemId);

    void removeAll(Integer memberId);

    void remove(Integer memberId, Integer itemId);

    void save(Cart cart);
}

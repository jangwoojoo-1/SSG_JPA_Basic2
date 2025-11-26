package com.ssg.lp.cart.entity;

import lombok.Getter;

@Getter
public class CartRequest {
    private Integer itemId;

    public Cart toEntity(Integer memberId) {
        return new Cart(itemId, memberId);
    }
}

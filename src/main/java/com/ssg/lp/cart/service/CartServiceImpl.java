package com.ssg.lp.cart.service;

import com.ssg.lp.cart.dto.CartRead;
import com.ssg.lp.cart.entity.Cart;
import com.ssg.lp.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    public List<CartRead> findAll(Integer memberId) {
        return cartRepository.findByMemberId(memberId).stream().map(Cart::toRead).toList();
    }

    @Override
    public CartRead find(Integer memberId, Integer itemId) {
        Optional<Cart> cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        return cart.map(Cart::toRead).orElse(null);
    }

    @Override
    public void removeAll(Integer memberId) {
        cartRepository.deleteByMemberId(memberId);
    }

    @Override
    public void remove(Integer memberId, Integer itemId) {
        cartRepository.deleteByMemberIdAndItemId(memberId, itemId);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}

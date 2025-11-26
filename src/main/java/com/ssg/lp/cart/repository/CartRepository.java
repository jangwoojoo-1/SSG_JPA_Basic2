package com.ssg.lp.cart.repository;

import com.ssg.lp.cart.entity.Cart;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByMemberId(Integer memberId);

    Optional<Cart> findByMemberIdAndItemId(Integer memberId, Integer itemId);

    void deleteByMemberId(Integer memberId);

    void deleteByMemberIdAndItemId(Integer memberId, Integer itemId);
}

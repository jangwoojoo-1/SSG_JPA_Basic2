package com.ssg.lp.item.repository;

import com.ssg.lp.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByIdIn(List<Integer> ids);

    Optional<Item> findById(Integer id);
}

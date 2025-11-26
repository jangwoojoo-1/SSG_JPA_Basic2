package com.ssg.lp.item.service;

import com.ssg.lp.item.dto.ItemRead;

import java.util.List;

public interface ItemService {
    List<ItemRead> findAll();

    List<ItemRead> findAll(List<Integer> ids);

    ItemRead find(Integer id);
}

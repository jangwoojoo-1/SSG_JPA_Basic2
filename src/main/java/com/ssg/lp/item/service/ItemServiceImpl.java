package com.ssg.lp.item.service;

import com.ssg.lp.item.dto.ItemRead;
import com.ssg.lp.item.entity.Item;
import com.ssg.lp.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;


    @Override
    public List<ItemRead> findAll() {
        return itemRepository.findAll().stream().map(Item::toRead).toList();
    }

    @Override
    public List<ItemRead> findAll(List<Integer> ids) {
        return itemRepository.findAllByIdIn(ids).stream().map(Item::toRead).toList();
    }

    @Override
    public ItemRead find(Integer id) {
        Optional<Item> item = itemRepository.findById(id);

        return item.map(Item::toRead).orElse(null);
    }
}

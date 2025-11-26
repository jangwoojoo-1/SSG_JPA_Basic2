package com.ssg.lp.item.entity;

import com.ssg.lp.item.dto.ItemRead;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer discountPer;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    public Item() {}

    public Item(String name, String imgPath, Integer price, Integer discountPer, String comment) {
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.discountPer = discountPer;
        this.comment = comment;
    }

    public ItemRead toRead(){
        return ItemRead.builder()
                .id(id)
                .name(name)
                .imgPath(imgPath)
                .price(price)
                .discountPer(discountPer)
                .comment(comment)
                .build();
    }
}

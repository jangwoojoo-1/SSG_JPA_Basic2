package com.ssg.lp.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemRead {
    private Integer id;
    private String name;
    private String imgPath;
    private Integer price;
    private Integer discountPer;
    private String comment;
}

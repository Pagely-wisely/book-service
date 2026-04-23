package com.pagely.bookservice.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    //    @Column(name = "category_id", nullable = false)
    private Long id;

    //    @Column(name = "category_name", nullable = false, length = 50)
    private String name;

    public static Category of(Long categoryId, String categoryName) {
        return new Category(categoryId, categoryName);
    }
}

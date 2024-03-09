package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "category")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String categoryName;

    public Category() {

    }
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

}

package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도시에 존재하는 랜드마크 건물
 */
@Entity
@Getter
@Table(name = "landmark")
@NoArgsConstructor
public class Landmark extends Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String landmarkName;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    //id 검증용으로만 쓰이기 때문에 LAZY를 주었다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    public Landmark(String landmarkName, String title, String description, City city, String optionName, Long cost) {
        super(optionName, cost, OptionType.LANDMARK);
        this.landmarkName = landmarkName;
        this.title = title;
        this.description = description;
        this.city = city;
    }
}

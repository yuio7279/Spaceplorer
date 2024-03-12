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
public class Landmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String landmarkName;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Landmark(String landmarkName, String title, String description, City city) {
        this.landmarkName = landmarkName;
        this.title = title;
        this.description = description;
        this.city = city;
    }
}

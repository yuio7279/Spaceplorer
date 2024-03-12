package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 각 도시에서 할 수 있는 특별한 즐길거리
 */
@Entity
@Getter
@Table(name = "entertainment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Entertainment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String entertainmentName;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String description;

    //id 검증용으로만 쓰이기 때문에 LAZY를 주었다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    public Entertainment(String entertainmentName, String title, String description, City city) {
        this.entertainmentName = entertainmentName;
        this.title = title;
        this.description = description;
        this.city = city;
    }
}

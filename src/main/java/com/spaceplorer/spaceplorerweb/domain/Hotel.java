package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "hotel")
@NoArgsConstructor
public class Hotel extends Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, unique = true)
    private  String hotelName;

    //호텔 점수 1~5
    @Column(nullable = false)
    private  Long rating;

    //설명
    @Column(nullable = false, unique = true)
    private  String description;

    private Long nights = 0L;


    //id 검증용으로만 쓰이기 때문에 LAZY를 주었다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;


    public Hotel(String hotelName, Long rating, String description, City city, String optionName, Long cost) {
        super(optionName, cost, OptionType.HOTEL);
        this.hotelName = hotelName;
        this.rating = rating;
        this.description = description;
        this.city = city;
    }

}

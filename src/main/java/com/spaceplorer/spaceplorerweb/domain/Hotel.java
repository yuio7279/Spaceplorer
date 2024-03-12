package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "hotel")
@NoArgsConstructor
public class Hotel {

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

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;


    public Hotel(String hotelName, Long rating, String description, City city) {
        this.hotelName = hotelName;
        this.rating = rating;
        this.description = description;
        this.city = city;
    }

}

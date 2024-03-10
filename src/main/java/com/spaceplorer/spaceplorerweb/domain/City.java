package com.spaceplorer.spaceplorerweb.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//행성 내에 존재하는 도시
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //도시 명
    @Column(nullable = false, unique = true)
    private String cityName;

    //도시 설명
    @Column(nullable = false, unique = true)
    private String description;

    //소속 planet과 1:N
    @ManyToOne
    @JoinColumn(name = "planet_id")
    private Planet planet;


    public City(String cityName, String description, Planet planet) {
        this.cityName = cityName;
        this.description = description;
        this.planet = planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

}

package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "planet")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    //행성명
    @Column(unique = true, nullable = false)
    private  String planetName;

    //온도
    @Column(nullable = false)
    private  String temperature;

    //자전주기
    @Column(nullable = false)
    private  String cycle;

    //지구와의 거리
    @Column(nullable = false)
    private  Long distanceFromEarth;

    //행성에 대한 설명
    @Column(nullable = false)
    private  String description;

    //hhms로만 도착할 수 있는 행성인지 여부
    @Column(nullable = false)
    private Boolean requiredHhms;

    //별칭 ex) 화성
    private String alias;

    //행성에 존재하는 도시
    //항상 cityList를 조회하기 때문에 eager로 설정
    @OneToMany(mappedBy = "planet")
    private final List<City> cityList = new ArrayList<>();


    //행성 생성자
    public Planet(String planetName, String temperature, String cycle, Long distanceFromEarth, String description, Boolean requiredHhms) {
        this.planetName = planetName;
        this.temperature = temperature;
        this.cycle = cycle;
        this.distanceFromEarth = distanceFromEarth;
        this.description = description;
        this.requiredHhms = requiredHhms;
        this.alias = getAlias(planetName);
    }

    public void addCityList(City city){
        cityList.add(city);
        city.setPlanet(this);
    }

    private String getAlias(String planetName){
        int index = planetName.indexOf("(");
        return planetName.substring(0, index);
    }

}

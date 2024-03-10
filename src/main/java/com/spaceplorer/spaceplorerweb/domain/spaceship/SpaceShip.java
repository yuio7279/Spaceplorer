package com.spaceplorer.spaceplorerweb.domain.spaceship;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "space_ship")
@NoArgsConstructor
public class SpaceShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true, nullable = false)
    private  String shipName;

    //우주선 속도
    @Column(nullable = false)
    private  Long shipSpeed;

    //우주선 기본 비용
    @Column(nullable = false)
    private  Long shipDefaultCost;

    //우주선 하루당 추가 비용
    @Column(nullable = false)
    private Long shipPerDayCost;

    //최대 수용인원
    @Column(nullable = false)
    private  Long capacity;

    //우주선에 대한 설명
    @Column(nullable = false)
    private  String description;



    //기본 우주선 생성
    public SpaceShip(String shipName, Long shipSpeed, Long shipDefaultCost, Long shipPerDayCost, Long capacity, String description) {
        this.shipName = shipName;
        this.shipSpeed = shipSpeed;
        this.shipPerDayCost = shipPerDayCost;
        this.shipDefaultCost = shipDefaultCost;
        this.capacity = capacity;
        this.description = description;
    }


}

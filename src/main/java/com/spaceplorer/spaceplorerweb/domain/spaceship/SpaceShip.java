package com.spaceplorer.spaceplorerweb.domain.spaceship;

import com.spaceplorer.spaceplorerweb.domain.Option;
import com.spaceplorer.spaceplorerweb.domain.OptionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "space_ship")
@NoArgsConstructor
public class SpaceShip extends Option {

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

    //Hyper high-speed movement system : 장거리용 고속 이동 시스템 탑재 우주선
    @OneToOne(mappedBy = "spaceShip",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Hhms hhms;

    //비행기 좌석 등급, 가격을 유연하게 하기 위해 만들었다.
    @OneToMany(mappedBy = "spaceShip", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpaceShipClass> spaceShipClassList = new ArrayList<>();


    //기본 우주선 생성
    public SpaceShip(String shipName, Long shipSpeed, Long shipDefaultCost, Long shipPerDayCost, Long capacity, String description, String optionName, Long cost) {
        super(optionName, cost, OptionType.SPACESHIP);
        this.shipName = shipName;
        this.shipSpeed = shipSpeed;
        this.shipPerDayCost = shipPerDayCost;
        this.shipDefaultCost = shipDefaultCost;
        this.capacity = capacity;
        this.description = description;
    }

    public void setHhms(Hhms hhms){
        this.hhms = hhms;
        hhms.setSpaceShip(this);
    }

    public void addSpaceShipClass(SpaceShipClass spaceShipClass){
        this.spaceShipClassList.add(spaceShipClass);
        spaceShipClass.setSpaceShip(this);
    }

}

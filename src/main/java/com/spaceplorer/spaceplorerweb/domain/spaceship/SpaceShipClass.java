package com.spaceplorer.spaceplorerweb.domain.spaceship;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 우주선 좌석 등급 엔티티
 */
@Entity
@NoArgsConstructor
@Getter
@Table(name = "space_ship_class")
public class SpaceShipClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ShipClass shipClass;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_ship_id")
    private SpaceShip spaceShip;


    public SpaceShipClass(ShipClass shipClass, Integer price, SpaceShip spaceShip) {
        this.shipClass = shipClass;
        this.price = price;
        this.spaceShip = spaceShip;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }
}

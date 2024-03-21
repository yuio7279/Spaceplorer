package com.spaceplorer.spaceplorerweb.domain.spaceship;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "space_booking")
public class SpaceBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //EX10, B8, A7 등
    //EX는 프리미엄 등급
    private String seatCode;

    private boolean isBooked;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_ship_class_id")
    private SpaceShipClass spaceShipClass;


    public SpaceBooking(String seatCode, boolean isBooked, SpaceShipClass spaceShipClass) {
        this.seatCode = seatCode;
        this.isBooked = isBooked;
        this.spaceShipClass = spaceShipClass;
    }


}

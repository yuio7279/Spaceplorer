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

    @OneToOne(mappedBy = "spaceBooking", cascade = CascadeType.ALL, orphanRemoval = true)
    private SpaceShipClass spaceShipClass;

    public SpaceBooking(String seatCode, boolean isBooked) {
        this.seatCode = seatCode;
        this.isBooked = isBooked;
    }

    public void addSpaceShipClass(SpaceShipClass spaceShipClass){
        this.spaceShipClass = spaceShipClass;
        spaceShipClass.setSpaceBooking(this);
    }

}

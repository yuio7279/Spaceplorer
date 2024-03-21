package com.spaceplorer.spaceplorerweb.domain.spaceship;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "hhms")
public class Hhms {
    //Hyper high-speed movement system : 장거리용 고속 이동 시스템 탑재 우주선
    //이 기능이 탑재 된 우주선은 엄청난 속도로 도착하게 된다. 장거리 비행시 유용하다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //hhms 작동 기본비용
    @Column(nullable = false)
    private Long hhmsDefaultCost;

    //hhms 하루당 추가비용
    @Column(nullable = false)
    private Long hhmsPerDayCost;

    //hhms 스피드
    @Column(nullable = false)
    private  Long hhmsSpeed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_ship_id")
    private SpaceShip spaceShip;

    //hhms탑재하기 생성자
    public Hhms(Long hhmsSpeed, Long hhmsDefaultCost, Long hhmsPerDayCost, SpaceShip spaceShip) {
        this.hhmsSpeed = hhmsSpeed;
        this.hhmsDefaultCost = hhmsDefaultCost;
        this.hhmsPerDayCost = hhmsPerDayCost;
        this.spaceShip = spaceShip;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }
}

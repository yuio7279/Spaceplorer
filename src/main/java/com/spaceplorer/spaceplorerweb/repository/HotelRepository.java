package com.spaceplorer.spaceplorerweb.repository;

import com.spaceplorer.spaceplorerweb.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("select c from Hotel c where c.city.id = :cityId and c.city.planet.id = :planetId")
    List<Hotel> findByPlanetIdAndCityId(@Param("planetId") Long pId, @Param("cityId") Long cId);
}

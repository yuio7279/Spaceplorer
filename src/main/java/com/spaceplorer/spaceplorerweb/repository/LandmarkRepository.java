package com.spaceplorer.spaceplorerweb.repository;

import com.spaceplorer.spaceplorerweb.domain.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LandmarkRepository extends JpaRepository<Landmark, Long> {

    @Query("select c from Landmark c where c.city.id = :cityId and c.city.planet.id = :planetId")
    List<Landmark> findByPlanetIdAndCityId(@Param("planetId") Long pId, @Param("cityId") Long cId);
}

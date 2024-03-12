package com.spaceplorer.spaceplorerweb.repository;

import com.spaceplorer.spaceplorerweb.domain.Entertainment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntertainmentRepository extends JpaRepository<Entertainment, Long> {

        @Query("SELECT e FROM Entertainment e WHERE e.city.id = :cityId AND e.city.planet.id = :planetId")
        List<Entertainment> findByPlanetAndCityIdId(@Param("planetId") Long planetId, @Param("cityId") Long cityId);
    }

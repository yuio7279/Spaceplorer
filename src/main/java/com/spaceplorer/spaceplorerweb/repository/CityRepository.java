package com.spaceplorer.spaceplorerweb.repository;

import com.spaceplorer.spaceplorerweb.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.planet.id = :planetId AND c.id = :cityId")
    Optional<City> findByPlanetIdAndCityId(@Param("planetId") Long pId, @Param("cityId") Long cId);



    Optional<City> findByPlanetAliasAndCityName(String planetName, String cityName);
}

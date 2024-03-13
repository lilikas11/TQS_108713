package tqs.lab3_2.data;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import tqs.lab3_2.Service.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

    public Optional<Car> findByCarld(Long carld);

    public List<Car> findAll();

    
}

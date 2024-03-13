package tqs.lab3_2.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.lab3_2.Service.Car;
import tqs.lab3_2.data.CarRepository;

/**
 * DataJpaTest limits the test scope to the data access context (no web environment loaded, for example)
 * tries to autoconfigure the database, if possible (e.g.: in memory db)
 * 
 * *@DataJpaTest includes the 
    @AutoConfigureTestDatabase. If a 
    dependency to an embedded 
    database is available, an in memory database is set up. Be 
    sure to include H2 in the POM.
 * 
 * ou seja, verifica se não há nenhuma dependencia que só está instalada localmente e que falta no pom :/
 */

@DataJpaTest
class CarRepositoryTest {

    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindBmwByName_thenReturnAlexCar() {
        // arrange a new car and insert into db
        Car car = new Car("car", "white");
        entityManager.persistAndFlush(car); //ensure data is persisted at this point
        
        // test the query method of interest
        Optional<Car> found = carRepository.findByCarld(car.getCarld());
        Car foundTypeCar = found.get();
        assertThat(foundTypeCar).isEqualTo(car);
    }

    @Test
    void whenInvalidCarName_thenReturnNull() {
        Optional<Car> fromDb = carRepository.findByCarld(11L);
        assertThat(fromDb).isEmpty();
    }


    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car car1 = new Car("car1", "white1");
        Car car2 = new Car("car2", "white2");
        Car car3 = new Car("car3", "white3");

        entityManager.persist(car1);
        entityManager.persist(car3);
        entityManager.persist(car2);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getCarld).containsOnly(car1.getCarld(), car2.getCarld(), car3.getCarld());
    }

}
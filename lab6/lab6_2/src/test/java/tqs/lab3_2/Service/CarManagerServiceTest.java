package tqs.lab3_2.Service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays; // Add this import

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito; // Add this import
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import tqs.lab3_2.data.CarRepository;
import tqs.lab3_2.Service.Car;
import tqs.lab3_2.Service.CarManagerService;

import org.mockito.Mockito.*;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarManagerServiceTest {

    // mocking the responses of the repository (i.e., no database will be used)
    // lenient is required because we load more expectations in the setup
    // than those used in some tests. As an alternative, the expectations
    // could move into each test method and be trimmed (no need for lenient then)
    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService CarService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Car bmw = new Car("bmw", "white");
        bmw.setCarld(333L);

        Car audi = new Car("audi", "black");
        Car mercedes = new Car("mercedes", "blue");
        List<Car> allCars = Arrays.asList(bmw, audi, mercedes);

        Mockito.when(carRepository.findByCarld(bmw.getCarld())).thenReturn(Optional.of(bmw));
        Mockito.when(carRepository.findByCarld(audi.getCarld())).thenReturn(Optional.of(audi));
        Mockito.when(carRepository.findByCarld(111L)).thenReturn(null);
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findByCarld(-99L)).thenReturn(Optional.empty());

    }

    @Test 
    void whenSerachValidId_thenCarShouldBeFound() {
        Optional<Car> fromDb = CarService.getCarDetails(333L);
        Car car = fromDb.get();
        assertThat(car.getMaker()).isEqualTo("bmw");
        assertThat(car.getModel()).isEqualTo("white");
        verifyFindByIdIsCalledOnce();
    }


    @Test 
    void whenSerachInvalidId_thenCarShouldNotBeFound() {
        Optional<Car> fromDb = CarService.getCarDetails(111L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test 
    void whenSaveCar_thenCarShouldBeReturned() {
        Car bmw = new Car("bmw", "white");
        Mockito.when(carRepository.save(bmw)).thenReturn(bmw);
        assertThat(CarService.save(bmw)).isEqualTo(bmw);
    }

    @Test 
    void given3Cars_whengetAll_thenReturn3Records() {
        Car bmw = new Car("bmw", "white");
        Car audi = new Car("audi", "black");
        Car mercedes = new Car("mercedes", "blue");

        List<Car> allCars = CarService.getAllCars();
        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(bmw.getMaker(), audi.getMaker(), mercedes.getMaker());
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarld(Mockito.anyLong());
        Mockito.reset(carRepository);
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(carRepository);
    }


}

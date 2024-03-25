package tqs.lab3_2.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.lab3_2.Service.Car;
import tqs.lab3_2.Service.CarManagerService;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarManagerService carManagerService;


    @PostMapping("/CreateCar" )
    public ResponseEntity<Car> createCar(Car car){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.carManagerService.save(car));
    }

    @GetMapping("/cars" )
    public List<Car> getAllCar(){
        return carManagerService.getAllCars();

    }

    @GetMapping("/cars/{carId}" )
    public ResponseEntity<Car> getCarById(@PathVariable(value = "carId") Long carId){

        Optional<Car> car = this.carManagerService.getCarDetails(carId);
        if (car.isPresent()) {
            return ResponseEntity.ok().body(car.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

package tqs.lab3_2.Controller;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import tqs.lab3_2.Controller.*;
import tqs.lab3_2.Service.*;
import tqs.lab3_2.data.*;

import java.util.List;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;    //entry point to the web framework

    // inject required beans as "mockeable" objects
    // note that @AutoWire would result in NoSuchBeanDefinitionException
    @MockBean
    private CarManagerService carManagerService;


    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car bmw = new Car("bmw", "black");

        when( carManagerService.save(Mockito.any()) ).thenReturn(bmw);

        mvc.perform(
                post("/api/CreateCar").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(bmw)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("bmw")));

        verify(carManagerService, times(1)).save(Mockito.any());

    }

    @Test
    void whenGetAllCar_givesAListWithAllCreatedCars() throws Exception {
        Car bmw = new Car("bmw", "black");
        Car audi = new Car("audi", "white");
        Car mercedes = new Car("mercedes", "blue");

        List<Car> allCars = Arrays.asList(bmw, audi, mercedes);

        when( carManagerService.getAllCars()).thenReturn(allCars);

        mvc.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(bmw.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(audi.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(mercedes.getMaker())));
        verify(carManagerService, times(1)).getAllCars();

    }
    
}

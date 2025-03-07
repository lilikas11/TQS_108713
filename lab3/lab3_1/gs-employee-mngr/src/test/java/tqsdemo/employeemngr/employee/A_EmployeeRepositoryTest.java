package tqsdemo.employeemngr.employee;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqsdemo.employeemngr.data.Employee;
import tqsdemo.employeemngr.data.EmployeeRepository;

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
class A_EmployeeRepositoryTest {

    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void whenFindAlexByName_thenReturnAlexEmployee() {
        // arrange a new employee and insert into db
        Employee alex = new Employee("alex", "alex@deti.com");
        entityManager.persistAndFlush(alex); //ensure data is persisted at this point

        // test the query method of interest
        Employee found = employeeRepository.findByName(alex.getName());
        assertThat(found).isEqualTo(alex);
    }

    @Test
    void whenInvalidEmployeeName_thenReturnNull() {
        Employee fromDb = employeeRepository.findByName("Does Not Exist");
        assertThat(fromDb).isNull();
    }

    @Test
    void whenFindEmployedByExistingId_thenReturnEmployee() {
        Employee emp = new Employee("test", "test@deti.com");
        entityManager.persistAndFlush(emp);

        Employee fromDb = employeeRepository.findById(emp.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Employee fromDb = employeeRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {
        Employee alex = new Employee("alex", "alex@deti.com");
        Employee ron = new Employee("ron", "ron@deti.com");
        Employee bob = new Employee("bob", "bob@deti.com");

        entityManager.persist(alex);
        entityManager.persist(bob);
        entityManager.persist(ron);
        entityManager.flush();

        List<Employee> allEmployees = employeeRepository.findAll();

        assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
    }

}
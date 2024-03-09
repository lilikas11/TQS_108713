a) Identify a couple of examples that use AssertJ expressive methods chaining.

On A_EmployeeRepositoryTest:

```python
assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());
```

On B_EmployeeService_UnitTest:

```python
assertThat(found.getName()).isEqualTo(name);
```

On E_EmployeeController_IntegrationTest:

```python
assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
```

#

b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

On B_EmployeeService_UnitTest:

```python
Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
```

#

c) What is the difference between standard @Mock and @MockBean?

@Mock is used to create a mock object of a class or an interface, while @MockBean is used to add mock objects to the Spring application context.


#

d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

The “application-integrationtest.properties” is used to get the right conetext to the integration tests. It has information like the database url, username, password, etc. It will be used when the integration tests are running.


#

e) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with 
SpringBoot. Which are the main/key differences?

The difference is that:
 - C_EmployeeController : tests the behavior of the API, mocking the service layer.
 - D_EmployeeService : tests the full web context, Service and Data. Using MockMvc to 'mock' the API.
 - E_EmployeeController_IntegrationTest : tests the full web context, like C, but addicionaly uses an API client to test the API. This is the most complete test, but also the slowest.
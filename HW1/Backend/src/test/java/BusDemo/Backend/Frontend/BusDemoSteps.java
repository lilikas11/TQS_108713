package BusDemo.Backend.Frontend;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@SelectClasspathResource("BusDemo/Backend/Frontend")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "BusDemo.Backend.Frontend")
public class BusDemoSteps {
    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;
    private Map<String, Object> vars;
  
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Sintaxe do Selenium 4
        vars = new HashMap<>();
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("Estou na página inicial do sistema de reservas de bus")
    public void estou_na_pagina_inicial() {
        setUp();  // Inicializa o WebDriver e outras configurações
        driver.get("http://localhost:5173/");
        driver.manage().window().setSize(new Dimension(1599, 999));
    }

    @When("Eu navego para a seção de viagens")
    public void eu_navego_para_a_secao_de_viagens() {
        WebElement thirdButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button:nth-child(3)")));
        thirdButton.click();
        log.info("Navegação para a seção de viagens foi clicada.");
        assertThat(driver.getCurrentUrl(), containsString("/viagens")); // Assuma que a URL deve conter '/viagens' após clicar no botão.
    }
    
    @When("Eu seleciono {string} como minha cidade de origem")
    public void eu_seleciono_como_minha_cidade_de_origem(String origem) {
        WebElement origemDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("origem")));
        origemDropdown.click();
        new Select(origemDropdown).selectByVisibleText(origem);
        assertThat(new Select(origemDropdown).getFirstSelectedOption().getText(), is(origem));
        log.info("Cidade de origem selecionada: " + origem);
    }

    @When("Eu seleciono {string} como meu destino")
    public void eu_seleciono_como_meu_destino(String destino) {
        WebElement destinoDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("destino")));
        destinoDropdown.click();
        new Select(destinoDropdown).selectByVisibleText(destino);
        String selectedDestino = new Select(destinoDropdown).getFirstSelectedOption().getText();
        assertThat(selectedDestino, is(equalTo(destino)));
        log.info("Destino selecionado: " + selectedDestino);
    }

    @When("Eu insiro {string} como a data de viagem")
    public void eu_insiro_como_a_data_de_viagem(String data) {
        WebElement dataField = wait.until(ExpectedConditions.elementToBeClickable(By.id("data")));
        dataField.click();
        dataField.clear();
        dataField.sendKeys(data);
        dataField.sendKeys(Keys.TAB);
    }
    

    @When("Eu submeto a busca por viagens")
    public void eu_submeto_a_busca_por_viagens() {
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        searchButton.click();
        log.info("Busca por viagens submetida.");
    }

    @When("Eu escolho pagar com {string}")
    public void eu_escolho_pagar_com(String moeda) {
        WebElement currencyDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select")));
        new Select(currencyDropdown).selectByVisibleText(moeda);
        String selectedCurrency = new Select(currencyDropdown).getFirstSelectedOption().getText();
        assertThat(selectedCurrency, is(equalTo(moeda)));
        log.info("Moeda selecionada para pagamento: " + selectedCurrency);
    }

    @When("Eu seleciono a primeira viagem disponível")
    public void eu_seleciono_a_primeira_viagem_disponivel() {
        WebElement firstTableRowButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("tr:nth-child(1) button")));
        firstTableRowButton.click();
        log.info("Primeira viagem disponível selecionada.");
    }

    @When("Eu preencho as informações de contato e endereço com {string}, {string}, {string}, {string} e {string}")
    public void eu_preencho_as_informacoes_de_contato_e_endereco_com(String nome, String endereco, String cidade, String numeroTelemovel, String numeroCartaoCredito) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form input:nth-of-type(1)")));
        nameInput.clear();
        nameInput.sendKeys(nome);
        assertThat(nameInput.getAttribute("value"), is(equalTo(nome)));
        log.info("Informações de contato e endereço preenchidas: " + nome);

        WebElement addressInput = driver.findElement(By.cssSelector("input:nth-child(2)"));
        addressInput.sendKeys(endereco);

        WebElement cityInput = driver.findElement(By.cssSelector("input:nth-child(3)"));
        cityInput.sendKeys(cidade);

        WebElement inputElement = driver.findElement(By.cssSelector("input:nth-child(4)"));
        inputElement.click(); // Este clique pode ser redundante
        Actions actions = new Actions(driver);
        actions.doubleClick(inputElement).perform();
        inputElement.sendKeys(numeroTelemovel);

        WebElement anotherInput = driver.findElement(By.cssSelector("input:nth-child(5)"));
        anotherInput.click();
        anotherInput.sendKeys(numeroCartaoCredito);

    }

    @When("Eu confirmo a reserva")
    public void eu_confirmo_a_reserva() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
        submitButton.click();
        log.info("Reserva confirmada.");
    }


    @Then("Eu deveria ser capaz de visualizar o ticket da minha reserva")
    public void eu_deveria_ser_capaz_de_visualizar_o_ticket_da_minha_reserva() {
        WebElement thankYouHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Obrigado por Comprar o seu Ticket Connosco!')]")));
        assertThat("O cabeçalho de agradecimento não foi encontrado na página", thankYouHeader, is(notNullValue()));    
        WebElement reservationTokenMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Guarde este token para procurar a sua viagem futuramente!')]")));
        assertThat("A mensagem sobre o token de reserva não foi encontrada na página", reservationTokenMessage, is(notNullValue()));
    }
    
}

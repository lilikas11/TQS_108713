// package BusDemo.Backend.Frontend;

// import org.junit.Test;
// import org.junit.Before;
// import org.junit.After;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.Dimension;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.Select;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.Keys;

// import java.time.Duration;
// import java.util.*;

// public class BusDemoTest {
//   private WebDriver driver;
//   private Map<String, Object> vars;
//   JavascriptExecutor js;
//   WebDriverWait wait;

//   @Before
//   public void setUp() {
//       driver = new ChromeDriver();
//       js = (JavascriptExecutor) driver;
//       wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Selenium 4 syntax
//       vars = new HashMap<String, Object>();
//   }
  

//   @After
//   public void tearDown() {
//     driver.quit();
//   }

//   @Test
//   public void busDemo3() {

//       // 1 - Entrada
//       // Abre a página no navegador
//       driver.get("http://localhost:5173/");
//       driver.manage().window().setSize(new Dimension(1599, 999));
  
//       // 2 - Viagens
//       WebElement thirdButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button:nth-child(3)")));
//       thirdButton.click();

//       // Espera pelo elemento dropdown de origem e seleciona 'Aveiro'
//       WebElement origemDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("origem")));
//       origemDropdown.click(); // Abre o dropdown
//       new Select(origemDropdown).selectByVisibleText("Aveiro");
  
//       // Espera pelo elemento dropdown de destino e seleciona 'Braga'
//       WebElement destinoDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("destino")));
//       destinoDropdown.click(); // Abre o dropdown
//       new Select(destinoDropdown).selectByVisibleText("Braga");
  
//       // Espera pelo elemento do campo de data, clica e envia a data
//       WebElement dataField = wait.until(ExpectedConditions.elementToBeClickable(By.id("data")));
//       dataField.click();
//       dataField.clear();
//       dataField.sendKeys("04-24-2024");
//       dataField.sendKeys(Keys.TAB);
      
//       // Submete o formulário
//       WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
//       searchButton.click();



//       // 3 - Passagens
//         // Selecione 'Dólar Canadense (CAD)' do dropdown
//       WebElement currencyDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select")));
//       new Select(currencyDropdown).selectByVisibleText("Dólar Canadense (CAD)");

//       // Clique no primeiro botão em uma linha de tabela
//       WebElement firstTableRowButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("tr:nth-child(1) button")));
//       firstTableRowButton.click();

//       // 4 - Preenchemento de formulário
//       // Preencha os campos de texto com informações
//       WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input:nth-child(1)")));
//       nameInput.click();
//       nameInput.clear();
//       nameInput.sendKeys("Liliana Paula Cruz Ribeiro");

//       WebElement addressInput = driver.findElement(By.cssSelector("input:nth-child(2)"));
//       addressInput.sendKeys("rua da conceição, nº22B, canto de calvão");

//       WebElement cityInput = driver.findElement(By.cssSelector("input:nth-child(3)"));
//       cityInput.sendKeys("CALVÃO VGS");

//       // O código a seguir simula múltiplos cliques e uma digitação em um campo de input,
//       // que parece ser o quarto elemento filho do tipo 'input' em algum contêiner
//       WebElement inputElement = driver.findElement(By.cssSelector("input:nth-child(4)"));
//       inputElement.click(); // Este clique pode ser redundante
//       Actions actions = new Actions(driver);
//       actions.doubleClick(inputElement).perform();
//       inputElement.sendKeys("adadasdas");

//       WebElement anotherInput = driver.findElement(By.cssSelector("input:nth-child(5)"));
//       anotherInput.click();
//       anotherInput.sendKeys("adadasdas");

//       // Clique em um botão, possivelmente para submeter o formulário ou para alguma outra ação
//       WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
//       submitButton.click();

//       // 5 - Ver o ticket comprado
//       // Clique no corpo da página, talvez para fechar um modal ou desfocar um elemento
//       driver.findElement(By.cssSelector("body")).click();

//       // 6 - voltar à homepage
//       // Isto parece ser repetido e talvez desnecessário, mas está replicando o código Python dado
//       submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
//       submitButton.click();

//       // 7 - Buscar ticket pelo token
//       // Digite um token e clique no botão de busca
//       WebElement tokenInput = driver.findElement(By.cssSelector(".token-input"));
//       tokenInput.click();
//       tokenInput.sendKeys("1d08c0e5-3010-4144-b6e4-9790e5af3bf7");
//   }
// }
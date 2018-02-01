package br.com.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MainPrograma {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("programa.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("Nome");
		sb.append(';');
		sb.append("Categoria");
		sb.append('\n');
		pw.write(sb.toString());
		WebDriver driver;
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
		driver = new ChromeDriver(capabilities);
		driver.get("https://sucupira.capes.gov.br/sucupira/public/consultas/coleta/programa/listaPrograma.xhtml");
		driver.findElement(By.name("form:inst:input")).click();
		WebElement uf = driver.findElement(By.name("form:inst:input"));
		uf.sendKeys("ufjf");
		Thread.sleep(500);
		driver.findElement(By.xpath("//*/select[1]/option[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.name("form:consultar")).click();
		Thread.sleep(15000);

		while (driver.findElements(By.xpath("//*/span/div/div/table/tbody/tr")).size() > 0) {
			int linhas = driver.findElements(By.xpath("//*/span/div/div/table/tbody/tr")).size();
			System.out.println(linhas);
			for (int i = 1; i <= linhas; i++) {
				String codigo = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[1]"))
						.getText();
				String programa = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[2]"))
						.getText();
				String areaAvaliacao = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[4]"))
						.getText();
				String areaBasica = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[5]"))
						.getText();
				String situacao = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[6]"))
						.getText();
				String modalidade = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[7]"))
						.getText();
				String me = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[8]"))
						.getText();
				String doutorado = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[9]"))
						.getText();
				String mp = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[10]"))
						.getText();
				StringBuilder sb3 = new StringBuilder();
				System.out.println(programa);
				sb3.append(codigo);
				sb3.append(";");
				sb3.append(programa);
				sb3.append(";");
				sb3.append(areaAvaliacao);
				sb3.append(";");
				sb3.append(areaBasica);
				sb3.append(";");
				sb3.append(situacao);
				sb3.append(";");
				sb3.append(modalidade);
				sb3.append(";");
				sb3.append(me);
				sb3.append(";");
				sb3.append(doutorado);
				sb3.append(";");
				sb3.append(mp);
				sb3.append('\n');
				pw.write(sb3.toString());
			}
			if (driver.findElements(By.xpath("//*/span/div/div/div[1]/ul/li[4]/a")).size() > 0) {
				driver.findElement(By.xpath("//*/span/div/div/div[1]/ul/li[4]/a")).click();
				Thread.sleep(2000);
			} else {
				break;
			}
		}
		pw.close();
		System.out.println("done!");

	}

}

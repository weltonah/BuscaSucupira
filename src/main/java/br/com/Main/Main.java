package br.com.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Main {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("test.csv"));
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
		driver.get("https://sucupira.capes.gov.br/sucupira/public/consultas/coleta/docente/listaDocente.jsf");
		WebElement uf = driver.findElement(By.name("form:j_idt22:inst:input"));
		uf.sendKeys("ufjf");
		Thread.sleep(500);
		driver.findElement(By.xpath("//*/select[1]/option[1]")).click();
		Thread.sleep(500);
		List<WebElement> listbeta = driver.findElements(By.xpath("//*/select[1]/option"));
		for (int j = 3; j < listbeta.size(); j++) {
			System.out.println("OI");
			if (listbeta.get(j).getText().contentEquals("--SELECIONE--"))
				break;
			listbeta.get(j).click();
			System.out.println(listbeta.get(j).getText());

			StringBuilder sb2 = new StringBuilder();
			sb2.append(listbeta.get(j).getText());
			sb2.append('\n');
			pw.write(sb2.toString());

			Thread.sleep(500);
			driver.findElement(By.name("form:consultar")).click();
			Thread.sleep(500);
			while (driver.findElements(By.xpath("//*/span/div/div/table/tbody/tr")).size() > 0) {

				int linhas = driver.findElements(By.xpath("//*/span/div/div/table/tbody/tr")).size();
				System.out.println(linhas);
				for (int i = 1; i <= linhas; i++) {
					String categoria = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[2]"))
							.getText();
					String nome = driver.findElement(By.xpath("//*/span/div/div/table/tbody/tr[" + i + "]/td[1]"))
							.getText();
					StringBuilder sb3 = new StringBuilder();
					sb3.append(nome);
					sb3.append(";");
					sb3.append(categoria);
					sb3.append('\n');
					pw.write(sb3.toString());
					System.out.println(nome + "  " + categoria);
				}
				if (driver.findElements(By.xpath("//*/span/div/div/div[1]/ul/li[4]/a")).size() > 0) {
					driver.findElement(By.xpath("//*/span/div/div/div[1]/ul/li[4]/a")).click();
					Thread.sleep(2000);
				} else {
					break;
				}
			}
			listbeta = driver.findElements(By.xpath("//*/select[1]/option"));
		}
		pw.close();
		System.out.println("done!");

	}

}

package sample.tianye.metrics;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {

		MonitorDriver driver = new MonitorDriver("src\\main\\resouces\\config.property");
		driver.call();
	}
}

package sample.tianye.metrics;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {
		
		MonitorDriver driver = new MonitorDriver("config.property");
		driver.call();
	}
}

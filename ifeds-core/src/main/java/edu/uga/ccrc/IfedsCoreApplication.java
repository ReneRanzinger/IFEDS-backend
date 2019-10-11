/**
 * @author Susan George
 */
package edu.uga.ccrc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IfedsCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(IfedsCoreApplication.class, args);
	}
	
	
	/*@Bean
	public CommandLineRunner demo(DatasetRepository repository) {
		return (args) -> {
			for (Dataset ds : repository.findAll()) {
				System.out.println(ds.getDatasetId());
				System.out.println(ds.getName());
				System.out.println(ds.getDescription());
				System.out.println(ds.getProvider().getName());
				System.out.println(ds.getSample().getName());
				System.out.println(ds.toString());
			}
		};
	}*/

}


package edu.uga.ccrc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import edu.uga.ccrc.exception.NoResponeException;
import edu.uga.ccrc.exception.SQLException;

@SpringBootApplication
@CrossOrigin
public class IfedsCoreApplication {

	public static void main(String[] args) throws NoResponeException {
		try {
		SpringApplication.run(IfedsCoreApplication.class, args);
		}catch(Exception e){
			throw new NoResponeException("Application cannot start");
		}
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

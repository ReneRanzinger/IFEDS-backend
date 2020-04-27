
package edu.uga.ccrc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.RequestContextListener;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;

import edu.uga.ccrc.exception.NoResposneException;
import edu.uga.ccrc.exception.SQLException;

@SpringBootApplication
@CrossOrigin
public class IfedsCoreApplication {
	
	@Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
	
	public static void main(String[] args) throws NoResposneException {
		try {
			
			new SpringApplicationBuilder()
			.environment(new StandardEncryptableEnvironment())
		    .sources(IfedsCoreApplication.class).run(args);
		}catch(Exception e){
			throw new NoResposneException("Application cannot start");
		}
	}
	
	


}

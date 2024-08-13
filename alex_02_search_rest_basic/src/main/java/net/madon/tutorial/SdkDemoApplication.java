package net.madon.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

// import javax.annotation.PostConstruct;

@SpringBootApplication
public class SdkDemoApplication  implements CommandLineRunner {

    
    private static final Logger LOGGER = LoggerFactory.getLogger(SdkDemoApplication.class);

    @Autowired
    SearchCmd searchCmd;    

    public static void main(String[] args) throws Exception {
	SpringApplication.run(SdkDemoApplication.class, args);
	LOGGER.info("Entering main()");
	Thread.sleep(2000);
	LOGGER.info("Ending main()");
    }
    public void run(String... args) throws Exception {
	LOGGER.info("Entering run()");
	searchCmd.execute();
	LOGGER.info("Leaving run()");
	
    }
}

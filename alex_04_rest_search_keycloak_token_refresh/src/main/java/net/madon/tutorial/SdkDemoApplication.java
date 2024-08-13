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
	LOGGER.info("Entering main()");
	SpringApplication.run(SdkDemoApplication.class, args);
	LOGGER.info("Exiting main()");
    }
    public void run(String... args) throws Exception {
	LOGGER.info("Entering run()");
	int sleepTimeMs=10000;
	for (int i = 0; i < 5; i++) {
	    LOGGER.info("Sending search request number: "+i);
	    searchCmd.execute();
	    LOGGER.info("Sleeping for: "+sleepTimeMs+" ms");
	    Thread.sleep(2000);
	}
	LOGGER.info("Leaving run()");
	
    }
}

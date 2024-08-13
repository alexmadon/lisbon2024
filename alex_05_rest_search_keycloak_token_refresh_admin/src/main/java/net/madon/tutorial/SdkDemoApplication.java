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
	int sleepTimeMs=6000;
	for (int i = 0; i < 10; i++) {
	    LOGGER.info("Sending search request number: "+i);
	    try {
		searchCmd.execute();
	    } catch (Exception e) {
		LOGGER.error("Something went wrong: "+e.getCause());
		// e.printStackTrace();
	    }
	    LOGGER.info("Sleeping for: "+sleepTimeMs+" ms");
	    Thread.sleep(sleepTimeMs);
	}
	LOGGER.info("Leaving run()");
	
    }
}

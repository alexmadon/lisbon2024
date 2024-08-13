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
public class SdkDiscoveryApplication  implements CommandLineRunner {

    
    private static final Logger LOGGER = LoggerFactory.getLogger(SdkDiscoveryApplication.class);

    @Autowired
    DiscoveryCmd discoveryCmd;    

    public static void main(String[] args) throws Exception {
	LOGGER.info("Entering main()");
	SpringApplication.run(SdkDiscoveryApplication.class, args);
	LOGGER.info("Exiting main()");
    }
    public void run(String... args) throws Exception {
	LOGGER.info("Entering run()");
	int sleepTimeMs=6000;
	for (int i = 0; i < 50; i++) {
	    LOGGER.info("Sending discovery request number: "+i);
	    try {
		discoveryCmd.execute();
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

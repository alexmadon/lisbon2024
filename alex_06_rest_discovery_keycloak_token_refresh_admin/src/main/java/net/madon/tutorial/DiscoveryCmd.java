package net.madon.tutorial;

import org.alfresco.discovery.handler.DiscoveryApi;
import org.alfresco.discovery.model.DiscoveryEntry;
// import org.alfresco.search.model.RequestQuery;
// import org.alfresco.search.model.ResultSetPaging;
// import org.alfresco.search.model.SearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DiscoveryCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryCmd.class);

    @Autowired
    DiscoveryApi discoveryApi;

    public void execute() throws IOException {
	LOGGER.info("Executing the discovery");
	ResponseEntity<DiscoveryEntry> result = discoveryApi.getRepositoryInformation();
	LOGGER.info("Discovery response StatusCode: {}", result.getStatusCode());
	LOGGER.debug("Discovery result: {}", result.getBody());
    }
}

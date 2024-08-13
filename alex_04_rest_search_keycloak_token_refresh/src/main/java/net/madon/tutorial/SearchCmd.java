package net.madon.tutorial;

import org.alfresco.search.handler.SearchApi;
import org.alfresco.search.model.RequestQuery;
import org.alfresco.search.model.ResultSetPaging;
import org.alfresco.search.model.SearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SearchCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(SearchCmd.class);

    @Autowired
    SearchApi searchApi;

    public void execute() throws IOException {
	LOGGER.info("Executing the search");
        ResponseEntity<ResultSetPaging> result = searchApi.search(new SearchRequest()
                .query(new RequestQuery()
                        .language(RequestQuery.LanguageEnum.AFTS)
                        .query("TEXT:*")));

	LOGGER.info("Search response StatusCode: {}", result.getStatusCode());
        LOGGER.debug("Search result: {}", result.getBody().getList().getEntries());
    }
}

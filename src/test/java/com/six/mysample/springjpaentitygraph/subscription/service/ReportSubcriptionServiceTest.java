package com.six.mysample.springjpaentitygraph.subscription.service;

import com.six.mysample.springjpaentitygraph.AbstractSpringEntityGraphTest;
import com.six.mysample.springjpaentitygraph.configuration.DomainConfiguration;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportSubscription;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
public class ReportSubcriptionServiceTest extends AbstractSpringEntityGraphTest {

    @Autowired
    ReportSubcriptionService reportSubcriptionService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void givenServiceEGWhenReportSubscritionLoadedThenOneReportSubscriptionIncludingChildredIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        ReportSubscription rs = reportSubcriptionService.getbyIdEG("1");
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        assertReportSubcription(rs);
    }

    @Test
    public void givenServiceNoEGWhenReportSubscritionLoadedThenOneReportSubscriptionIncludingChildredIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        ReportSubscription rs = reportSubcriptionService.getbyIdNoEG("1");
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        assertReportSubcription(rs);
    }
}
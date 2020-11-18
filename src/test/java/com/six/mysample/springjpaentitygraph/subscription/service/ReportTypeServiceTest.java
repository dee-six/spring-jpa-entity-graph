package com.six.mysample.springjpaentitygraph.subscription.service;

import com.six.mysample.springjpaentitygraph.AbstractSpringEntityGraphTest;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportType;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ReportTypeServiceTest extends AbstractSpringEntityGraphTest {

    @Autowired
    ReportTypeService reportTypeService;


    @Test
    public void givenReportTypeWhenLoadedWithEGThenOneEntityIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        ReportType rt = reportTypeService.getTypeByIdEG("900001");
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        assertReportType(rt);
    }

    @Test
    public void givenReportTypeWhenLoadedWithoutEGThenOneEntityIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");

        ReportType rt = reportTypeService.getTypeByIdNoEG("900001");

        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        assertReportType(rt);
    }

    @Test
    public void givenServiceWhenAllLoadedWithEGThenAllEntitiesIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        List<ReportType> rt = reportTypeService.getAllReportTypes();
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        rt.forEach(this::assertReportType);

    }

    @Test
    public void givenServiceWhenAllLoadedWithoutEGThenAllEntitiesIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        List<ReportType> rt = reportTypeService.getAllReportTypesWithoutEG();
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        rt.forEach(this::assertReportType);
    }

    @Test
    public void givenServiceWhenAllLoadedAgainAndAgainWithEGThenAllEntitiesIncludingChildrenIsLoadedOnlyOnce() {

        long startTime = System.currentTimeMillis();
        log.info("--- First Start----");
        List<ReportType> rts1 = reportTypeService.getAllReportTypes();
        long end = System.currentTimeMillis();
        log.info("--- First ended in {} milliseconds ----", end - startTime);
        rts1.forEach(this::assertReportType);

        startTime = System.currentTimeMillis();
        log.info("--- Second Start----");
        List<ReportType> rts2 = reportTypeService.getAllReportTypes();
        end = System.currentTimeMillis();
        log.info("---Second  ended in {} milliseconds ----", end - startTime);
        rts2.forEach(this::assertReportType);

    }
}

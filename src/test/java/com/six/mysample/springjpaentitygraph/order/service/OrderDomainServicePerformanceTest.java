package com.six.mysample.springjpaentitygraph.order.service;

import com.six.mysample.springjpaentitygraph.AbstractSpringEntityGraphTest;
import com.six.mysample.springjpaentitygraph.order.domain.Order;
import com.six.mysample.springjpaentitygraph.order.repository.OrderRepositoryEG;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportSubscription;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportType;
import com.six.mysample.springjpaentitygraph.subscription.repository.ReportTypeRepositoryEG;
import com.six.mysample.springjpaentitygraph.subscription.service.ReportSubcriptionService;
import com.six.mysample.springjpaentitygraph.subscription.service.ReportTypeService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

@Slf4j
public class OrderDomainServicePerformanceTest extends AbstractSpringEntityGraphTest {

    @Autowired
    OrderDomainService orderDomainService;

    @Autowired
    ReportTypeService reportTypeService;

    @Autowired
    OrderRepositoryEG orderRepositoryEG;

    @Autowired
    ReportTypeRepositoryEG reportTypeRepositoryEG;

    @Autowired
    ReportSubcriptionService reportSubcriptionService;

    @Before
    @Commit
    public void setUp() throws Exception {

        log.info("-- Setup Start --");

        ReportSubscription rs = reportSubcriptionService.getbyIdEG("1");

        ReportType reportType = reportTypeService.getTypeByIdEG("1");

        List<Order> orders = OrderTestDataBuilder.buildReportOrders("CH9090909", reportType, 200);

        orders.forEach(order -> {
                    OrderTestDataBuilder.buildOrderForTest(order, reportType, "DEEPAK_FILE_NAME_153531407.CSV");
                    order.setReportSubscription(rs);
                }
        );

        orderRepositoryEG.saveAll(orders);

        log.info("-- Object Created end --");

    }

    @After
    public void tearDown() throws Exception {
        List<Order> orders = orderDomainService.getOrdersEG();
        orders.forEach(orderRepositoryEG::delete);
    }


    @Test
    public void givenOrdersWhenLoadedWithEGThenOneEntityIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        List<Order> rt = orderDomainService.getOrdersEG();
        log.info(" No of order loaded {}", rt.size());
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        rt.forEach(this::assertOrder);
    }

    @Test
    public void givenOrdersWhenLoadedNoEGThenOneEntityIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        List<Order> rt = orderDomainService.getOrdersNoEG();
        long end = System.currentTimeMillis();
        log.info(" No of order loaded {}", rt.size());
        log.info("--- ended in {} milliseconds ----", end - startTime);

        rt.forEach(this::assertOrder);
    }

}

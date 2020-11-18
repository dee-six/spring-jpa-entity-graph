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
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

@Slf4j
public class OrderDomainServiceTest extends AbstractSpringEntityGraphTest {

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
    private UUID primarykey;

    @Before
    @Commit
    public void setUp() throws Exception {

        log.info("-- Setup Start --");

        ReportSubscription rs = reportSubcriptionService.getbyIdEG("1");

        ReportType reportType = reportTypeService.getTypeByIdEG("1");

        List<Order> orders = OrderTestDataBuilder.buildReportOrders("CH9090909", reportType, 1);

        orders.forEach(order -> {
                    OrderTestDataBuilder.buildOrderForTest(order, reportType, "DEEPAK_FILE_NAME_153531407.CSV");
                    order.setReportSubscription(rs);
                }
        );

        List<Order> savedOrders = orderRepositoryEG.saveAll(orders);

        primarykey = savedOrders.stream().filter(order -> order.getBusinessId().equals(1)).findFirst().get()
                .getId();
        log.info("-- Object Created end --");

    }

    @After
    public void tearDown() throws Exception {
        List<Order> orders = orderDomainService.getOrdersEG();
        orders.forEach(orderRepositoryEG::delete);
    }

    @Test
    public void givenOrderWhenLoadedWithEGThenOneEntityIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        Order rt = orderDomainService.getOrderByIdEG(1);
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        assertOrder(rt);
    }

    @Test
    public void givenOrderWhenLoadedWithPrimayrKeyEGThenOneEntityIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        Order rt = orderDomainService.getOrderByPrimayrKeyEG(primarykey);
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        assertOrder(rt);
    }

    @Test
    public void givenOrdersWhenLoadedWithEGThenOneEntityIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        List<Order> rt = orderDomainService.getOrdersEG();
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
        log.info("--- ended in {} milliseconds ----", end - startTime);

        rt.forEach(this::assertOrder);
    }

    @Test
    public void givenOrdersWhenLoadedEGAdvancedThenOneEntityIncludingChildrenIsLoaded() {

        long startTime = System.currentTimeMillis();
        log.info("--- Start----");
        List<Order> rt = orderDomainService.getOrdersEGAdvanced();
        long end = System.currentTimeMillis();
        log.info("--- ended in {} milliseconds ----", end - startTime);

        rt.forEach(this::assertOrder);
    }

}

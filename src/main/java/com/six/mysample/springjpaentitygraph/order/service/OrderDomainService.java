package com.six.mysample.springjpaentitygraph.order.service;

import com.six.mysample.springjpaentitygraph.order.domain.Order;
import com.six.mysample.springjpaentitygraph.order.repository.OrderRepositoryEG;
import com.six.mysample.springjpaentitygraph.order.repository.OrderRepositoryNoEG;
import com.six.mysample.springjpaentitygraph.subscription.service.ReportSubcriptionService;
import com.six.mysample.springjpaentitygraph.subscription.service.ReportTypeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OrderDomainService {

    OrderRepositoryEG orderRepositoryEG;
    OrderRepositoryNoEG orderRepositoryNoEG;

    ReportSubcriptionService reportSubcriptionService;
    ReportTypeService reportTypeService;

    public Order getOrderByIdEG(Integer id) {
        Order order = orderRepositoryEG.findByBusinessId(id);
        loadChildren(order);
        return order;
    }

    public Order getOrderByPrimayrKeyEG(UUID id) {
        Optional<Order> order = orderRepositoryEG.findById(id);
        order.ifPresent(this::loadChildren);
        order.orElseThrow(() ->  new IllegalArgumentException("Entiy not found"));
        return order.get();
    }

    private void loadChildren(Order order) {
        reportTypeService.getTypeByIdEG(order.getReportType().getBusinessId().toString());
        log.debug(" ReporType is loaded for EG");
        Optional.ofNullable(order.getReportSubscription()).ifPresent(reportSubscription -> reportSubcriptionService
                .getbyIdEG(reportSubscription.getBusinessId().toString()));
        log.debug(" reportSubscription is loaded for EG");
    }

    public Order getOrderByIdNoEG(Integer id) {
        Order order = orderRepositoryNoEG.findByBusinessId(id);

        loadChildredNoEG(order);
        return order;
    }

    public List<Order> getOrdersNoEG() {
        List<Order> orders = orderRepositoryNoEG.findAll();

        orders.forEach(order -> {
            order.getOrderSteps().size();

            order.getOrderSteps().forEach(orderStep -> {
                orderStep.getOrderInstructions().size();
                orderStep.getOrderStepStatuses().size();
                orderStep.getOrderArtifacts().size();

                orderStep.getOrderInstructions().forEach(orderInstruction -> {
                    orderInstruction.getOrderParameterValues().size();
                });
            });
        });

        orders.forEach(this::loadChildredNoEG);

        return orders;
    }

    private void loadChildredNoEG(Order order) {
        reportTypeService.getTypeByIdNoEG(order.getReportType().getBusinessId().toString());
        Optional.ofNullable(order.getReportSubscription()).ifPresent(reportSubscription -> reportSubcriptionService
                .getbyIdNoEG(reportSubscription.getBusinessId().toString()));
    }

    public List<Order> save(List<Order> orderList) {
        return orderRepositoryEG.saveAll(orderList);
    }

    public List<Order> getOrdersEG() {
        List<Order> orders = orderRepositoryEG.findAll();

        orders.forEach(this::loadChildren);

        return orders;
    }

    public List<Order> getOrdersEGAdvanced() {
        List<Order> orders = orderRepositoryNoEG.findAll();

        orders.forEach(order -> {
            orderRepositoryEG.findByBusinessId(order.getBusinessId());
        });

        orders.forEach(this::loadChildren);

        return orders;
    }
}

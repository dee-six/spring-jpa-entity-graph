package com.six.mysample.springjpaentitygraph.order.service;

import com.google.common.collect.Sets;
import com.six.mysample.springjpaentitygraph.order.domain.Order;
import com.six.mysample.springjpaentitygraph.order.domain.OrderArtifact;
import com.six.mysample.springjpaentitygraph.order.domain.OrderInstruction;
import com.six.mysample.springjpaentitygraph.order.domain.OrderParameterValue;
import com.six.mysample.springjpaentitygraph.order.domain.OrderStatusDto;
import com.six.mysample.springjpaentitygraph.order.domain.OrderStep;
import com.six.mysample.springjpaentitygraph.order.domain.OrderStepExecutionStatusDto;
import com.six.mysample.springjpaentitygraph.order.domain.OrderStepStatus;
import com.six.mysample.springjpaentitygraph.order.domain.OrderStepTaskTypeDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportChannelDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportFormatDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportFrequencyDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportParameterTypeDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportTimeDto;
import com.six.mysample.springjpaentitygraph.subscription.domain.ReportType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.util.Lists;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Provide a description of the class
 * <p>
 * Created by tkr0d on 22.09.2017.
 */
public class OrderTestDataBuilder {

    public static List<Order> buildReportOrders(ReportType reportType) {

        List<Order> _s = new ArrayList<Order>();

        _s.addAll(IntStream.of(1, 2)
                .mapToObj(i -> buildOrder(String.valueOf(i), OrderStatusDto.SCHEDULED, reportType))
                .collect(Collectors.toList()));

        _s.addAll(IntStream.of(3, 4, 5)
                .mapToObj(i -> buildOrder(String.valueOf(i), OrderStatusDto.PROCESSING, reportType))
                .collect(Collectors.toList()));

        _s.addAll(IntStream.of(6, 7, 8, 9)
                .mapToObj(i -> buildOrder(String.valueOf(i), OrderStatusDto.COMPLETED, reportType))
                .collect(Collectors.toList()));

        _s.addAll(IntStream.of(10)
                .mapToObj(i -> buildOrder(String.valueOf(i), OrderStatusDto.FAILED, reportType))
                .collect(Collectors.toList()));

        return _s;
    }

    public static List<Order> buildReportOrders(String businessPartner, ReportType reportType) {

        List<Order> _s = new ArrayList<Order>();

        _s.addAll(IntStream.of(1, 2)
                .mapToObj(i -> buildOrder(String.valueOf(i), businessPartner, OrderStatusDto.SCHEDULED,
                        reportType, "john"))
                .collect(Collectors.toList()));

        _s.addAll(IntStream.of(3, 4, 5)
                .mapToObj(i -> buildOrder(String.valueOf(i), businessPartner, OrderStatusDto.PROCESSING,
                        reportType, "john"))
                .collect(Collectors.toList()));

        _s.addAll(IntStream.of(6, 7, 8, 9)
                .mapToObj(i -> buildOrder(String.valueOf(i), businessPartner, OrderStatusDto.COMPLETED,
                        reportType, "john"))
                .collect(Collectors.toList()));

        _s.addAll(IntStream.of(10)
                .mapToObj(i -> buildOrder(String.valueOf(i), businessPartner, OrderStatusDto.FAILED, reportType,
                        "john"))
                .collect(Collectors.toList()));

        _s.addAll(IntStream.rangeClosed(11, 1000).mapToObj(i -> buildOrder(String.valueOf(i), businessPartner, OrderStatusDto.FAILED, reportType,
                "john"))
                .collect(Collectors.toList()));
        return _s;
    }

    public static List<Order> buildReportOrders(String businessPartner, ReportType reportType, int noOfInstance) {

        List<Order> _s = IntStream.rangeClosed(1, noOfInstance)
                .mapToObj(i -> buildOrder(String.valueOf(i), businessPartner, OrderStatusDto.SCHEDULED,
                        reportType, "john")).collect(Collectors.toList());

        return _s;
    }

    public static List<Order> buildReportOrdersWithOrderArtifacts(String businessPartner, ReportType reportType) {
        return buildReportOrders(businessPartner, reportType).stream()
                .map(OrderTestDataBuilder::buildReportOrderWithOrderArtifact)
                .collect(Collectors.toList());
    }

    public static Order buildReportOrderWithOrderArtifact(Order Order) {

        OrderStep dto = OrderStep.builder()
                .taskType(OrderStepTaskTypeDto.GENERATE)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .lastExecutionStatus(OrderStepExecutionStatusDto.COMPLETED)
                .build();
        dto.addOrderArtifact(buildReportOrderArtifacts());
        Order.addOrderStep(dto);
        return Order;
    }

    public static Set<OrderArtifact> buildReportOrderArtifacts() {
        return IntStream.rangeClosed(1, 2)
                .mapToObj(OrderTestDataBuilder::buildOrderArtifact)
                .collect(Collectors.toSet());
    }

    private static OrderArtifact buildOrderArtifact(int i) {
        return OrderArtifact.builder()
                .location("mnt/reports")
                .artifactFileName("reportFileName-" + i)
                .creationTime(LocalDateTime.now())
                .format(ReportFormatDto.CSV)
                .channel(ReportChannelDto.WebBox)
                .build();
    }

    public static Order buildOrder(String id, OrderStatusDto status, ReportType ReportType) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy H:mm:ssSSS");
        return buildOrder(id, "CH000042", status, ReportType, "john");
    }

    public static Order buildOrder(String id, String businessReference, OrderStatusDto status,
            ReportType ReportType, String username) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy H:mm:ssSSS");

        return Order.builder()
                .id(UUID.randomUUID())
                .businessId(Integer.valueOf(id))
                .businessPartnerReference(businessReference)
                .shortName("TradeDetails" + id)
                .name("TRADE Details - " + id)
                .creationTime(LocalDateTime.now()
                        .withHour(12)
                        .withMinute(30)
                        .withSecond(30)
                        .withNano(999000000))
                .modificationTime(LocalDateTime.now()
                        .withHour(12)
                        .withMinute(30)
                        .withSecond(30)
                        .withNano(999000000))
                .reportType(ReportType)
                .orderStatusDto(status)
                .createdBy(username)
                .build();
    }

    public static List<Order> buildReportOrders(int count, String businessReference, OrderStatusDto status,
            ReportType ReportType) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(
                        i -> buildOrder(String.valueOf(i), businessReference, OrderStatusDto.FAILED, ReportType,
                                "john"))
                .collect(Collectors.toList());
    }

    public static OrderParameterValue buildOrderParameterValue(String name, ReportParameterTypeDto type, String value,
            int sortOrder) {
        return OrderParameterValue.builder()
                .parameterName(name)
                .parameterValue(value)
                .parameterType(type)
                .sortOrder(sortOrder)
                .build();
    }

    public static OrderStep buildOrderStep(OrderStepTaskTypeDto taskType) {
        return OrderStep.builder()
                .taskType(taskType)
                .creationTime(LocalDateTime.now())
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .lastExecutionStatus(OrderStepExecutionStatusDto.COMPLETED)
                .build();
    }

    public static OrderInstruction buildOrderInstruction(ReportType ReportType,
            ReportFrequencyDto frequencyDto, ReportTimeDto timeDto,
            ReportFormatDto formatDto, ReportChannelDto channelDto,
            String fileName) {
        return OrderInstruction.builder()
                .channel(channelDto)
                .format(formatDto)
                .frequency(frequencyDto)
                .reportKey(ReportType.getReportKey())
                .reportTypeId(ReportType.getId().toString())
                .time(timeDto)
                .fileName(fileName)
                .location("")
                .build();
    }

    public static OrderInstruction orderInstruction_DAILY_MORNING_CSV_WEBBOX(ReportType ReportType, String fileName) {
        return OrderTestDataBuilder.buildOrderInstruction(ReportType, ReportFrequencyDto.DAILY, ReportTimeDto.MORNING,
                ReportFormatDto.CSV, ReportChannelDto.WebBox,
                fileName);
    }

    public static OrderInstruction orderInstruction_DAILY_MORNING_CSV_COC(ReportType ReportType, String fileName) {
        return OrderTestDataBuilder.buildOrderInstruction(ReportType, ReportFrequencyDto.DAILY, ReportTimeDto.MORNING,
                ReportFormatDto.CSV, ReportChannelDto.COCO,
                fileName);
    }

    public static OrderInstruction orderInstruction_DAILY_MORNING_SWIFT_SWIFTALLIANCE(ReportType ReportType,
            String fileName) {
        return OrderTestDataBuilder.buildOrderInstruction(ReportType, ReportFrequencyDto.DAILY, ReportTimeDto.MORNING,
                ReportFormatDto.SWIFT, ReportChannelDto.SWIFT,
                fileName);
    }

    public static OrderInstruction orderInstruction_DAILY_MORNING_PDF_WEBBOX(ReportType ReportType,
            String fileName) {
        return OrderTestDataBuilder.buildOrderInstruction(ReportType, ReportFrequencyDto.DAILY, ReportTimeDto.MORNING,
                ReportFormatDto.PDF, ReportChannelDto.WebBox,
                fileName);
    }

    public static OrderInstruction orderInstruction_DAILY_MORNING_PDF_COC(ReportType ReportType,
            String fileName) {
        return OrderTestDataBuilder.buildOrderInstruction(ReportType, ReportFrequencyDto.DAILY, ReportTimeDto.MORNING,
                ReportFormatDto.PDF, ReportChannelDto.COCO,
                fileName);
    }

    public static OrderStep OrderStep_for_requested(OrderStepTaskTypeDto taskTypeDto,
            OrderInstruction orderInstruction,
            List<OrderParameterValue> parameters) {
        OrderStep step = OrderTestDataBuilder.buildOrderStep(taskTypeDto);

        step.addOrderArtifact(buildReportOrderArtifacts());
        if (orderInstruction != null) {
            step.addOrderInstruction(orderInstruction);
        }

        if (parameters != null) {
            orderInstruction.addOrderParameterValue(Sets.newHashSet(parameters));
        }


        return step;
    }

    public static OrderStep OrderStep_FOR_GENERATE(OrderInstruction OrderInstruction,
            List<OrderParameterValue> parameters) {
        return OrderStep_for_requested(OrderStepTaskTypeDto.GENERATE, OrderInstruction, parameters);
    }

    public static OrderStep OrderStep_FOR_CONVERT(OrderInstruction OrderInstruction,
            List<OrderParameterValue> parameters) {
        return OrderStep_for_requested(OrderStepTaskTypeDto.CONVERT, OrderInstruction, parameters);
    }

    public static OrderStep OrderStep_FOR_DELIVER(OrderInstruction OrderInstruction,
            List<OrderParameterValue> parameters) {
        return OrderStep_for_requested(OrderStepTaskTypeDto.DELIVER, OrderInstruction, parameters);
    }

    public static Order buildOrderForTest(Order order, ReportType ReportType, String fileName) {

        order.addOrderStep(
                OrderTestDataBuilder.OrderStep_FOR_GENERATE(
                        OrderTestDataBuilder.orderInstruction_DAILY_MORNING_CSV_WEBBOX(ReportType, fileName),
                        OrderTestDataBuilder.buildReportOrderParameterValue()));

        order.addOrderStep(
                OrderTestDataBuilder.OrderStep_FOR_CONVERT(
                        OrderTestDataBuilder.orderInstruction_DAILY_MORNING_PDF_WEBBOX(ReportType, fileName),
                        OrderTestDataBuilder.buildReportOrderParameterValue()));

        order.addOrderStep(
                OrderTestDataBuilder.OrderStep_FOR_DELIVER(
                        OrderTestDataBuilder.orderInstruction_DAILY_MORNING_PDF_WEBBOX(ReportType, fileName),
                        OrderTestDataBuilder.buildReportOrderParameterValue()));

        for (OrderStep stepDto : order.getOrderSteps()) {
            stepDto.addOrderStepStatus(
                    OrderStepStatus.builder().lastExecutionStatus(OrderStepExecutionStatusDto.COMPLETED).executionMessage("TEST")
                            .build());
        }

        return order;
    }

    /**
     * Used by ReportDto
     */
    public static List<OrderParameterValue> buildReportOrderParameterValue() {
        return Arrays.asList(
                OrderParameterValue.builder()
                        .parameterName("FromTradeSettleDate")
                        .parameterValue("10.12.2017")
                        .parameterType(ReportParameterTypeDto.DATE)
                        .sortOrder(1)
                        .build(),
                OrderParameterValue.builder()
                        .parameterName("ToTradeSettleDate")
                        .parameterValue("11.12.2017")
                        .parameterType(ReportParameterTypeDto.DATE)
                        .sortOrder(1)
                        .build(),
                OrderParameterValue.builder()
                        .parameterName("TradeCcy")
                        .parameterValue("CHF")
                        .parameterType(ReportParameterTypeDto.STRING)
                        .sortOrder(1)
                        .build());
    }

}

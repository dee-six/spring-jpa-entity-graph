package com.six.mysample.springjpaentitygraph.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderSummary {

    private String businessId;
    private String shortName;
    private String name;
    private String summary;
    private String reportTypeName;
    private OrderStatusDto orderStatus;

}

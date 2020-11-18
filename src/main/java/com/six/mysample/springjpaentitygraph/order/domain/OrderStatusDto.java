package com.six.mysample.springjpaentitygraph.order.domain;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum OrderStatusDto {
	SCHEDULED,
	PROCESSING,
	CANCELLED,
	COMPLETED,
	FAILED
}
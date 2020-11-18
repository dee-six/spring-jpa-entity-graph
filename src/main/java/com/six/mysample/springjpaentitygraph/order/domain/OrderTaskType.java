package com.six.mysample.springjpaentitygraph.order.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum OrderTaskType {
	GENERATE("generate"),
	CONVERT("convert"),
	DELIVER("deliver");

	private String value;

}
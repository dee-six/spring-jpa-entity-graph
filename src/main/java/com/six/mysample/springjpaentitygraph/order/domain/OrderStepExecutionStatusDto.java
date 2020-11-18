package com.six.mysample.springjpaentitygraph.order.domain;

import lombok.AllArgsConstructor;

/**
 * Provide a description of the class
 * Created by tkr0d on 20.09.2017.
 *
 * @author tkr0d
 * @version 1.0
 * @created 09-Nov-2017 09:10:21
 */
@AllArgsConstructor
public enum OrderStepExecutionStatusDto {
	START,
	RESTART,
	PROCESSING,
	CANCELLED,
	COMPLETED,
	FAILED,
}
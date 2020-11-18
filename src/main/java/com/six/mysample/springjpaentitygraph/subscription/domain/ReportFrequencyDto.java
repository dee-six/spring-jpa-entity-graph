package com.six.mysample.springjpaentitygraph.subscription.domain;

import lombok.AllArgsConstructor;

/**
 * @author tkr0d
 * @version 1.0
 * @created 15-Sep-2017 14:00:29
 */
@AllArgsConstructor
public enum ReportFrequencyDto {

	DAILY("Daily"),
	MONTHLY("Monthly"),
	QUARTERLY("Quarterly"),
	YEARLY("Yearly");

	private String name;

}
package com.six.mysample.springjpaentitygraph.subscription.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tkr0d
 * @version 1.0
 * @created 15-Sep-2017 14:00:33
 */
@AllArgsConstructor
@Getter
public enum ReportTimeDto {

	MORNING("Morning", "10:00"),
	MIDDAY("Midday", "12:00"),
	END_OF_BUSINESS("End of business", "20:00"),
	AT_10_PM("At 10 PM", "22:00");

	private String shortCode;
	private String name;
}
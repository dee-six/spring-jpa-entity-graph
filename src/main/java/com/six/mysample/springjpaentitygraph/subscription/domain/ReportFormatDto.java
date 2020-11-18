package com.six.mysample.springjpaentitygraph.subscription.domain;

/**
 * @author tkke3
 * @version 1.0
 * @created 04-Sep-2017
 */
public enum ReportFormatDto {
	PDF("PDF"),
	TXT("TXT"),
	HTML("HTML"),
	CSV("CSV"),
	SWIFT("SWIFT");

	public final String value;

	ReportFormatDto(String value) {
		this.value = value;
	}
}
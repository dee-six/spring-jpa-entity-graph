package com.six.mysample.springjpaentitygraph.subscription.domain;

import lombok.Getter;

/**
 * @author tkke3
 * @version 1.0
 * @created 19-Sep-2017
 */
@Getter
public enum ReportChannelDto {

	WebBox("WebBox", false),
	FTP("FTP", false),
	COCO("COCO", true),
	CCLINK("CC-link", false),
	SWIFT("Swift Alliance", false);

	private final String  value;
	private final boolean autoDelivered;


	ReportChannelDto(String value, boolean isAutoDelivery) {
		this.value = value;
		this.autoDelivered = isAutoDelivery;
	}
}

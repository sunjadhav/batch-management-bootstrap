package com.batch.dto;

import lombok.Data;

@Data
public class BatchSearchDTO {

	private String batchName;
	private String startDate;
	private String endDate;
	private String batchType;
	private String noRecord;
}

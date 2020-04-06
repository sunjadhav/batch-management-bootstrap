package com.batch.dto;

import lombok.Data;

@Data
public class BatchDTO {

	private Long id;
	private String batchId;
	private String name;
	private String timing;
	private String duration;
	private String startedAt;
	private String isActive;
	private Double collection;
	private Double pendingCollection;
	private Integer studentsCount;
}

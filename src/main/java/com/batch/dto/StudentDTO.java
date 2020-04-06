package com.batch.dto;

import lombok.Data;

@Data
public class StudentDTO {

	private Long id;
	private String surname;
	private String name;
	private String phoneNumber;
	private String address;
	private String college;
	private String education;
	private String paidAmount;
	private String pendingAmount;
	private String batchName;
	private String batchStatus;
}

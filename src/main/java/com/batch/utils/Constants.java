package com.batch.utils;

public class Constants {
	public static final String LOGIN_FAILED = "Please login again :( ";
	public static final String MESSAGE = "message";
	public static final String LOGIN = "login";
	public static final String MM_DD_YYYY = "MM/dd/yyyy";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "fail";
	
	private Constants() {
		
	}
	
	public class BatchType{
		public static final String ALL = "All";
		public static final String ACTIVE = "Active";
		public static final String INACTIVE = "Inactive";
		public static final String ARCHIVED = "Archived";
	}
	
	public class FolderNames{
		
		public static final String BASE_FOLDER = "D:\\Batch-Management Reports\\";
		public static final String BATCH_FOLDER = "All Batches\\";
		public static final String STUDENT_FOLDER = "Active Batches Students\\";
	}
}

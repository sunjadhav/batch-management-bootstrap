package com.batch.task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

import com.batch.dto.BatchDTO;
import com.batch.dto.StudentDTO;

@Component
public class WeeklyReportGenerator {

	
	public void generateBatchReport(String csvFile, List<BatchDTO> batchdto) throws IOException {
		FileWriter writer = new FileWriter(csvFile);

		// for header
		CSVUtils.writeLine(writer, Arrays.asList("ID", "Batch ID", "Batch Name", "Timing", "Duration",
				"Batch Started On", "isActive", "Batch Collection", "Pending Collection"));
		int id = 1;

		for (BatchDTO batch : batchdto) {

			List<String> list = new ArrayList<>();
			list.add(String.valueOf(id));
			list.add(batch.getBatchId());
			list.add(batch.getName());
			list.add(batch.getTiming());
			list.add(batch.getDuration());
			list.add(batch.getStartedAt());
			list.add(batch.getIsActive());
			list.add(String.valueOf(batch.getCollection()));
			list.add(String.valueOf(batch.getPendingCollection()));

			CSVUtils.writeLine(writer, list);
			id++;

		}

		writer.flush();
		writer.close();

	}

	public void generateStudentReport(String csvFile, List<StudentDTO> studentDTOs) throws IOException {
		FileWriter writer = new FileWriter(csvFile);

		// for header
		CSVUtils.writeLine(writer, Arrays.asList("ID", "First Name", "Last Name", "Address", "Mobile Number",
				"College/Company", "Education", "Paid Amount", "Pending Amount", "Batch", "Batch Status"));
		int id = 1;

		for (StudentDTO studentDTO : studentDTOs) {

			List<String> list = new ArrayList<>();
			list.add(String.valueOf(id));
			list.add(studentDTO.getName());
			list.add(studentDTO.getSurname());
			list.add(studentDTO.getAddress());
			list.add(studentDTO.getPhoneNumber());
			list.add(studentDTO.getCollege());
			list.add(studentDTO.getEducation());
			list.add(String.valueOf(studentDTO.getPaidAmount()));
			list.add(String.valueOf(studentDTO.getPaidAmount()));
			list.add(studentDTO.getBatchName());
			list.add(studentDTO.getBatchStatus());
			
			CSVUtils.writeLine(writer, list);
			id++;

		}

		writer.flush();
		writer.close();

	}

}

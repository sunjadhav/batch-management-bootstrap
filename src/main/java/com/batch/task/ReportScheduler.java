package com.batch.task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.batch.dto.BatchDTO;
import com.batch.dto.StudentDTO;
import com.batch.model.Batch;
import com.batch.model.Student;
import com.batch.repository.BatchRepository;
import com.batch.repository.StudentRepository;
import com.batch.service.BatchService;
import com.batch.service.StudentService;
import com.batch.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReportScheduler {

	@Autowired
	WeeklyReportGenerator weeklyReportGenerator;
	@Autowired
	BatchService batchService;
	@Autowired
	StudentService studentService;
	@Autowired
	BatchRepository batchRepository;
	@Autowired
	private StudentRepository studentRepository;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh-mm");

	@Scheduled(cron = "0 30 09 * * 1") //every Monday at 9:30 AM
	//@Scheduled(cron = "0/5 * * * * *") 
	public void performTaskUsingCron() throws IOException {
		prepareFolder();
		prepareBatchReport();
		prepareStudentReport();
		log.info("report generated...");
	}

	private void prepareBatchReport() throws IOException {
		List<BatchDTO> allBatches = batchService.getAllBatches();
		List<BatchDTO> activeBatches = batchService.getActiveBatches();
		List<BatchDTO> inactiveBatches = batchService.getInactiveBatches();
		String allBatchesFileName = getBatchFileName(Constants.BatchType.ALL);
		String activeBatchesFileName = getBatchFileName(Constants.BatchType.ACTIVE);
		String inactiveBatchesFileName = getBatchFileName(Constants.BatchType.INACTIVE);

		weeklyReportGenerator.generateBatchReport(allBatchesFileName, allBatches);
		weeklyReportGenerator.generateBatchReport(activeBatchesFileName, activeBatches);
		weeklyReportGenerator.generateBatchReport(inactiveBatchesFileName, inactiveBatches);
	}

	private void prepareStudentReport() throws IOException {
		List<Batch> activeBatches = batchRepository.findActiveBatches();

		for (Batch batch : activeBatches) {
			List<Student> students = studentRepository.findByBatch(batch);
			List<StudentDTO> studentDtos = new ArrayList<>();
			for (Student student : students) {
				StudentDTO studentDTO = studentService.studentEntityToDto(student);
				studentDtos.add(studentDTO);
			}
			String fileName = getStudentFileName(batch.getName());
			weeklyReportGenerator.generateStudentReport(fileName, studentDtos);
		}
	}

	private String getBatchFileName(String batchType) {
		Date date = new Date();
		String formattedDate = dateFormat.format(date);
		String fileName = Constants.FolderNames.BASE_FOLDER.concat(Constants.FolderNames.BATCH_FOLDER);
		log.info(fileName + formattedDate);
		fileName = fileName.concat(formattedDate);
		File file = createFolder(fileName);

		return file.getPath() + "/" + batchType + "_Batches_" + formattedDate + ".csv";
	}

	private String getStudentFileName(String batchName) {
		Date date = new Date();
		String formattedDate = dateFormat.format(date);
		String fileName = Constants.FolderNames.BASE_FOLDER.concat(Constants.FolderNames.STUDENT_FOLDER);
		log.info(fileName + formattedDate);
		fileName = fileName.concat(formattedDate);
		File file = createFolder(fileName);

		return file.getPath() + "/" + batchName + "_Student_List_" + formattedDate + ".csv";
	}

	private void prepareFolder() {
		String baseFolder = Constants.FolderNames.BASE_FOLDER;
		createFolder(baseFolder);
		
		String batchesFolder = Constants.FolderNames.BASE_FOLDER.concat(Constants.FolderNames.BATCH_FOLDER);
		createFolder(batchesFolder);
		
		String studentsFolder = Constants.FolderNames.BASE_FOLDER.concat(Constants.FolderNames.STUDENT_FOLDER);
		createFolder(studentsFolder);
	}
	
	private File createFolder(String folderName) {
		File file = new File(folderName);
		if (!file.exists()) {
			if (file.mkdir()) {
				log.info(folderName + " directory created!");
			} else {
				log.info("Failed to create " + folderName + " directory!");
			}
		}
		return file;
	}
	
}

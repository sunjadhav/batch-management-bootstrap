package com.batch.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.batch.dto.BatchDTO;
import com.batch.dto.BatchSearchDTO;
import com.batch.model.Batch;
import com.batch.model.Student;
import com.batch.repository.BatchRepository;
import com.batch.repository.StudentRepository;
import com.batch.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class BatchService {

	@Autowired
	private BatchRepository repo;

	@Autowired
	private StudentRepository studentRepository;

	private SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(Constants.MM_DD_YYYY);

	public List<Batch> listAll() {
		return repo.findAllBatches();
	}

	public Boolean save(BatchDTO batchDto) {
		try {

			Batch batch = repo.findByBatchIdOrName(batchDto.getBatchId(), batchDto.getName());
			if (batch == null) {
				Batch bat = batchDtoToEntity(batchDto, batch);
				bat.setIsArchived(false);
				repo.save(bat);
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (Exception ex) {
			return Boolean.FALSE;
		}
	}

	public Boolean updateBatch(BatchDTO batchDTO) {
		try {
			Batch batch = get(batchDTO.getId());
			Batch bat = batchDtoToEntity(batchDTO, batch);
			repo.save(bat);
			return Boolean.TRUE;
		} catch (Exception ex) {
			return Boolean.FALSE;
		}

	}

	public Batch get(long id) {
		Optional<Batch> batch = repo.findById(id);
		if (batch.isPresent())
			return batch.get();
		else
			return null;
	}

	public void delete(long id) {
		repo.deleteById(id);
	}

	public void archive(long id) {
		Batch batch = get(id);
		if (batch != null) {
			batch.setIsArchived(true);
			batch.setIsActive("Archived");
			repo.save(batch);
		}
	}
	
	public void undo(long id) {
		Batch batch = get(id);
		if (batch != null) {
			batch.setIsArchived(false);
			batch.setIsActive("No");
			repo.save(batch);
		}
	}
	
	public Batch getByBatchName(String batchName) {
		return repo.findByName(batchName);
	}

	public List<BatchDTO> getAllBatches() {
		List<Batch> batches = repo.findAllBatches();
		List<BatchDTO> listbatches = new ArrayList<>();
		batches.forEach(batch -> listbatches.add(batchEntityToDto(batch)));
		return listbatches;
	}

	public List<BatchDTO> getActiveBatches() {
		List<Batch> batches = repo.findActiveBatches();
		List<BatchDTO> listbatches = new ArrayList<>();
		batches.forEach(batch -> listbatches.add(batchEntityToDto(batch)));
		return listbatches;
	}

	public List<BatchDTO> getInactiveBatches() {
		List<Batch> batches = repo.findInactiveBatches();
		List<BatchDTO> listbatches = new ArrayList<>();
		batches.forEach(batch -> listbatches.add(batchEntityToDto(batch)));
		return listbatches;
	}

	public List<BatchDTO> getArchivedBatches() {
		List<Batch> batches = repo.findArchivedBatches();
		List<BatchDTO> listbatches = new ArrayList<>();
		batches.forEach(batch -> listbatches.add(batchEntityToDto(batch)));
		return listbatches;
	}
	
	public List<Batch> searchByDate(BatchSearchDTO batchSearchDTO) {
		List<Batch> batches = new ArrayList<>();
		long startDate = 0;
		long endDate = 0;
		long currentDate = System.currentTimeMillis();
		log.info("currentDate : "+currentDate);
		try {
			startDate = ((batchSearchDTO.getStartDate() != null && !batchSearchDTO.getStartDate().equals(""))
					? simpleDateFormat.parse(batchSearchDTO.getStartDate()).getTime() / 1000
					: 946684800); // 946684800 = Saturday, January 1, 2000 12:00:00 AM
			endDate = ((batchSearchDTO.getEndDate() != null && !batchSearchDTO.getEndDate().equals(""))
					? simpleDateFormat.parse(batchSearchDTO.getEndDate()).getTime() / 1000
					: currentDate);
		} catch (ParseException e) {
			log.info("Error : " + e.getMessage());
		}
		log.info("startDate : "+startDate);
		log.info("endDate : "+endDate);
		if (batchSearchDTO.getBatchType().equalsIgnoreCase("All")) {
			batches.addAll(repo.findByStartedAtBetween(startDate, endDate));
		} else {
			batches.addAll(repo.findByStartedAtBetweenAndIsActive(startDate, endDate,
					statusConverter(batchSearchDTO.getBatchType())));
		}
		return batches;
	}

	public List<BatchDTO> searchByBatchType(String batchType) {

		switch (batchType) {
		case Constants.BatchType.ALL:
			return getAllBatches();
		case Constants.BatchType.ACTIVE:
			return getActiveBatches();
		case Constants.BatchType.INACTIVE:
			return getInactiveBatches();
		default:
			return getAllBatches();
		}
	}

	public List<BatchDTO> search(BatchSearchDTO batchSearchDTO) {
		List<Batch> batches = new ArrayList<>();
		List<BatchDTO> listbatches = new ArrayList<>();
		if (batchSearchDTO.getBatchName() != null && !batchSearchDTO.getBatchName().equals("")) {
			Batch batch = getByBatchName(batchSearchDTO.getBatchName());
			if (batch != null)
				batches.add(batch);
		} else if ((batchSearchDTO.getStartDate() != null && !batchSearchDTO.getStartDate().equals(""))
				|| (batchSearchDTO.getEndDate() != null && !batchSearchDTO.getEndDate().equals(""))
						&& batchSearchDTO.getBatchType() != null) {
			batches = searchByDate(batchSearchDTO);
		} else {
			return searchByBatchType(batchSearchDTO.getBatchType());
		}
		batches.forEach(batch -> listbatches.add(batchEntityToDto(batch)));

		return listbatches;
	}

	public Batch batchDtoToEntity(BatchDTO batchDTO, Batch bat) {
		Batch batch = (bat == null ? new Batch() : bat);
		long startedAt = 0;
		try {
			startedAt = simpleDateFormat.parse(batchDTO.getStartedAt()).getTime() / 1000;
		} catch (ParseException e) {
			log.info("Error : " + e.getMessage());
		}
		batch.setBatchId(batchDTO.getBatchId());
		batch.setName(batchDTO.getName());
		batch.setTiming(batchDTO.getTiming());
		batch.setDuration(batchDTO.getDuration());
		batch.setStartedAt(startedAt);
		batch.setIsActive(batchDTO.getIsActive());
		return batch;
	}

	public BatchDTO batchEntityToDto(Batch batch) {
		BatchDTO batchDTO = new BatchDTO();
		Double collection = 0.0;
		Double pendingCollection = 0.0;
		String date = simpleDateFormat.format(new java.util.Date(batch.getStartedAt() * 1000));
		batchDTO.setId(batch.getId());
		batchDTO.setBatchId(batch.getBatchId());
		batchDTO.setName(batch.getName());
		batchDTO.setTiming(batch.getTiming());
		batchDTO.setDuration(batch.getDuration());
		batchDTO.setStartedAt(date);
		batchDTO.setIsActive(batch.getIsActive());

		List<Student> students = studentRepository.findByBatch(batch);
		int studentsCount = 0;
		for (Student student : students) {
			collection = collection + Double.valueOf(student.getPaidAmount());
			pendingCollection = pendingCollection + Double.valueOf(student.getPendingAmount());
			studentsCount++;
		}
		batchDTO.setCollection(collection);
		batchDTO.setPendingCollection(pendingCollection);
		batchDTO.setStudentsCount(studentsCount);
		return batchDTO;
	}

	public String statusConverter(String status) {
		if (status.equalsIgnoreCase(Constants.BatchType.ACTIVE))
			return "Yes";
		else if (status.equalsIgnoreCase(Constants.BatchType.INACTIVE))
			return "No";
		else if (status.equalsIgnoreCase("Yes"))
			return Constants.BatchType.ACTIVE;
		else if (status.equalsIgnoreCase("No"))
			return Constants.BatchType.INACTIVE;
		else if (status.equalsIgnoreCase("Archived"))
			return Constants.BatchType.ARCHIVED;
		
		return "";
	}

}
package com.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.batch.model.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long> {

	@Query("SELECT a FROM Batch a where a.batchId= (?1) and a.isArchived = FALSE")
    Batch findByBatchId(String batchId);
	
	@Query("SELECT a FROM Batch a where a.name= (?1) and a.isArchived = FALSE")
    Batch findByName(String batchName);
	
	@Query("SELECT a FROM Batch a where a.batchId= (?1) or a.name= (?2)")
    Batch findByBatchIdOrName(String batchId, String batchName);
	
	@Query("SELECT a FROM Batch a where a.startedAt Between (?1) and (?2) and a.isArchived = FALSE")
    List<Batch> findByStartedAtBetween(Long startDate, Long endDate);
	
	@Query("SELECT a FROM Batch a where a.startedAt Between (?1) and (?2) and a.isActive = (?3) and a.isArchived = FALSE")
    List<Batch> findByStartedAtBetweenAndIsActive(Long startDate, Long endDate, String isActive);
	
	@Query("SELECT a FROM Batch a where a.isArchived = FALSE")
    List<Batch> findAllBatches();
	
	@Query("SELECT a FROM Batch a where a.isActive = 'Yes' and a.isArchived = FALSE")
    List<Batch> findActiveBatches();
	
	@Query("SELECT a FROM Batch a where a.isActive = 'No' and a.isArchived = FALSE")
    List<Batch> findInactiveBatches();
	
	@Query("SELECT a FROM Batch a where a.isArchived = TRUE")
    List<Batch> findArchivedBatches();
	
	
}

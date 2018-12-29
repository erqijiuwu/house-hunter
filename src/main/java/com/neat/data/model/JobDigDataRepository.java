package com.neat.data.model;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDigDataRepository extends CrudRepository<JobDigDataBean, Long>{

}

package com.neat.data.model;

// import java.util.List;

 import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestfulServletRepository extends JpaRepository<JobDigDataBean, Long>{

}
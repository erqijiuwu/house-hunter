package com.neat.data.control;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neat.data.model.JobDigDataBean;
import com.neat.data.model.JobDigDataRepository;

@RestController
public class JobDigDataListController {
    @Autowired
    private JobDigDataRepository jobDigDataRepository;
    
	@PostMapping("/jobdigdata_old")
    public JobDigDataBean createJob(@Valid @RequestBody JobDigDataBean job) {
        return jobDigDataRepository.save(job);
    }
}

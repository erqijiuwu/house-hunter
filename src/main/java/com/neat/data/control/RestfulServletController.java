package com.neat.data.control;

import java.io.IOException;
import java.util.ArrayList;
// import java.util.Iterator;
import java.util.Iterator;
import java.util.List;

// import javax.validation.Valid;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;// in play 2.3

import com.neat.data.model.JobDigDataBean;
// import com.neat.data.model.JobDigDataRepository;
import com.neat.data.model.RestfulServletRepository;

@RestController
public class RestfulServletController {
    @Autowired
    private RestfulServletRepository jobDigDataRepository;

    @PostMapping("/jobdigdata")
    public List<JobDigDataBean> createJob(HttpEntity<String> httpEntity) throws IOException {
        List<JobDigDataBean> jobs = new ArrayList<JobDigDataBean>();
        String reqBody = httpEntity.getBody();
//		System.out.println(reqBody);
        ObjectMapper mapper = new ObjectMapper();

        JsonNode nodeRoot = mapper.readTree(reqBody);
        for (JsonNode nodeItem : nodeRoot) {
//            System.out.println(nodeItem.toString());
            long id = nodeItem.get("id").asLong();

            JobDigDataBean job = new JobDigDataBean();
            job.setId(id);
            job.setJsonBody(nodeItem.toString());
            jobs.add(job);
            JobDigDataBean t = jobDigDataRepository.saveAndFlush(job);

            System.out.println(t.toString());
        }

//		List<JobDigDataBean> jobs = null;
//		try {
//			jobs = mapper.readValue(reqBody, new TypeReference<List<JobDigDataBean>>() {
//			});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

        // JobDigDataBean job=new JobDigDataBean();

//        return jobDigDataRepository.saveAll(jobs);
        // return null;
        return jobs;
    }
}

package com.neat.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "job_dig_data_overview")
public class JobDigDataBean extends CommentModel{
	private static final long serialVersionUID = 1L;

	@Id
//    @GeneratedValue(generator = "job_dig_data_generator")
//    @SequenceGenerator(
//            name = "job_dig_data_generator",
//            sequenceName = "job_dig_data_sequence",
//            initialValue = 1000
//    )
    private Long id;
	
	@Column(columnDefinition = "text")
	private String jsonBody;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJsonBody() {
		return jsonBody;
	}

	public void setJsonBody(String jsonBody) {
		this.jsonBody = jsonBody;
	}
}

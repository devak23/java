package com.pearson.registrationassistant.service;

import com.pearson.registrationassistant.vo.Applicant;
import com.pearson.registrationassistant.vo.Outcome;

/**
 * Specification for the DataService
 * Created by abhaykulkarni on 03/12/16.
 */
public interface DataService {
	Applicant readCandidate(String client);

	void persistCandidate(Applicant applicant, String client, String fileName);

	void persistCandidateMetadata(Outcome outcome, String client, String fileName);
}

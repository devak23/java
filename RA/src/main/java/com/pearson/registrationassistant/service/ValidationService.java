package com.pearson.registrationassistant.service;

import com.pearson.registrationassistant.vo.Applicant;
import com.pearson.registrationassistant.vo.Outcome;

/**
 * Created by abhaykulkarni on 03/12/16.
 */
public interface ValidationService {
	Outcome validateApplicant(Applicant applicant);
}

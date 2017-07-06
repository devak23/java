package com.pearson.registrationassistant.service.impl;

import com.pearson.registrationassistant.service.CacheService;
import com.pearson.registrationassistant.service.DataService;
import com.pearson.registrationassistant.service.RegistrationService;
import com.pearson.registrationassistant.service.ValidationService;
import com.pearson.registrationassistant.util.CommonUtil;
import com.pearson.registrationassistant.vo.Applicant;
import com.pearson.registrationassistant.vo.Outcome;
import com.pearson.registrationassistant.ws.RegistrationAssistantWS;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.File;

/**
 * The service layer that reads the client information and performs validation
 *
 * Created by abhaykulkarni on 03/12/16.
 */
public class RegistrationServiceImpl implements RegistrationService {
	public static final String APPLICANT = "applicant";
	private Logger logger = Logger.getLogger(RegistrationServiceImpl.class);

	@Inject
	private CacheService cacheService;
	@Inject
	private ValidationService validationService; // solely containing validation
	@Inject
	private DataService dataService; // just to fetch the candidate data

	@Override
	public Outcome registerCandidateData(Applicant applicant, String client, String fileName) {
		logger.info("processing applicant data.." + applicant);
		Outcome outcome = validationService.validateApplicant(applicant);
		outcome.setFirstName(applicant.getPerson().getFirstName());
		outcome.setMiddleName(applicant.getPerson().getMiddleName());
		outcome.setLastName(applicant.getPerson().getLastName());


		if (outcome.isValid()) {
			// payment gateway integration

			// generateUniqueID
			applicant.setUniqueId(generateUniqueID(client));
			outcome.setUniqueId(applicant.getUniqueId());
			logger.info("Unique ID generated: " + applicant.getUniqueId());

			dataService.persistCandidate(applicant, client, fileName);
		}

		// if clients are not individuals write the validation outcome to FTP folders for them to read.
		if (!client.startsWith(RegistrationAssistantWS.INDIVIDUALS)) {
			dataService.persistCandidateMetadata(outcome, client, "outcome");
		}

		return outcome;
	}

	@Override
	public Outcome processClientData(String client) {
		Applicant applicant = dataService.readCandidate(client + File.separator + APPLICANT);
		return registerCandidateData(applicant, client, APPLICANT);
	}

	@Override
	public String generateUniqueID(String client) {
		String abbrv = cacheService.getClientAbbrv(client);
		int randNo = CommonUtil.generateRandomNumber();
		return abbrv + "-" + randNo;
	}
}

package com.pearson.registrationassistant.service.impl;

import com.pearson.registrationassistant.service.DataService;
import com.pearson.registrationassistant.service.data.DataAdapter;
import com.pearson.registrationassistant.service.data.impl.DataAdapterFactory;
import com.pearson.registrationassistant.util.AppConstantsEnum;
import com.pearson.registrationassistant.util.PropertiesHelper;
import com.pearson.registrationassistant.vo.Applicant;
import com.pearson.registrationassistant.vo.Outcome;
import com.pearson.registrationassistant.vo.UserSelection;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.File;

/**
 * THe class that fetches and saves data
 *
 * Created by abhaykulkarni on 03/12/16.
 */
public class DataServiceImpl implements DataService {
	private Logger logger = Logger.getLogger(DataServiceImpl.class);
	@Inject
	private PropertiesHelper propertiesHelper;
	@Inject
	private DataAdapterFactory adapterFactory;


	@Override
	public Applicant readCandidate(String client) {
		String ftpRoot = propertiesHelper.getProperty(AppConstantsEnum.DATA_FTP_ROOT.getValue());
		Applicant applicant = getDataAdapter().readData(ftpRoot + File.separator + client);
		return applicant;
	}

	@Override
	public void persistCandidate(Applicant applicant, String client, String fileName) {
		String ftpRoot = propertiesHelper.getProperty(AppConstantsEnum.DATA_FTP_ROOT.getValue());
		UserSelection userSelection = new UserSelection();
		userSelection.setApplicant(applicant);
		getDataAdapter().writeData(userSelection, ftpRoot + File.separator + client + File.separator + fileName);
	}

	@Override
	public void persistCandidateMetadata(Outcome outcome, String client, String fileName) {
		String ftpRoot = propertiesHelper.getProperty(AppConstantsEnum.DATA_FTP_ROOT.getValue());
		getDataAdapter().writeData(outcome, ftpRoot + File.separator + client + File.separator + fileName);
	}

	/**
	 * Helper method to get the DataAdapter
	 * @return
	 */
	private DataAdapter getDataAdapter() {
		String dataMode = propertiesHelper.getProperty(AppConstantsEnum.DATA_STORAGE_MODE.getValue());
		return adapterFactory.getAdapter(dataMode);
	}
}

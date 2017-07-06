package com.pearson.registrationassistant.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pearson.registrationassistant.service.RegistrationService;
import com.pearson.registrationassistant.util.CommonUtil;
import com.pearson.registrationassistant.util.JsonHelper;
import com.pearson.registrationassistant.vo.Applicant;
import com.pearson.registrationassistant.vo.Outcome;
import com.pearson.registrationassistant.vo.Person;
import com.pearson.registrationassistant.vo.UserSelection;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This is a front controller to all the processing logic. The purpose is to just capture
 * all data and send it to the service layer for processing.
 *
 * Created by abhaykulkarni on 03/12/16.
 */

@Path("/assistant")
public class RegistrationAssistantWS {
	private Logger logger = Logger.getLogger(RegistrationAssistantWS.class);
	public static final String INDIVIDUALS = "individuals";

	@Inject
	private RegistrationService registrationService;

	/**
	 * This API is invoked by the registration registerCandidateData which gets invoked
	 * via Pearson VUE's site.
	 * @param json - the details of the candidate entered by the
	 * @return a JSON response indicating the status of the request
	 */
	@POST
	@Path("/register/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON})
	public Response registerCandidate(String json) {
		logger.info("Registering candidate data...: " + json);
		UserSelection userSelection = JsonHelper.getUserSelection(json);

		// create the client string - used to write the data with "this" file name
		String fileName = getIdentifier(userSelection.getApplicant());
		Outcome outcome =
				registrationService
						.registerCandidateData(userSelection.getApplicant(),INDIVIDUALS, fileName);
		return Response.ok().entity(JsonHelper.convertToJson(outcome)).build();
	}

	private String getIdentifier(Applicant applicant) {
		Person person = applicant.getPerson();
		String firstName = person.getFirstName();
		String middleName = person.getMiddleName();
		String lastName = person.getLastName();

		return firstName.toLowerCase()
				+ (CommonUtil.isEmpty(middleName) ? "_" : middleName.toLowerCase())
				+ (CommonUtil.isEmpty(lastName) ? "_" : lastName.toLowerCase());
	}


	/**
	 * This API will be invoked by any cert org who want to process their data via FTP
	 * @param client - the client who has ftp'ed the candidate information and wishes to
	 *               process the information
	 * @return a JSON response indicating the status of the request
	 */
	@Path("/process/{client}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response processClient(@PathParam("client") String client) {
		logger.info("Processing client data..." + client);
		Outcome outcome = registrationService.processClientData(client);
		Gson gson = new GsonBuilder().create();
		return Response.ok()
				.entity(gson.toJson(outcome))
				.header("Access-Control-Allow-Origin","*")
				.header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials","true")
				.header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age","1209600")
				.build();
	}
}

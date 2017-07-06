package com.pearson.registrationassistant.service.data.impl;

import com.pearson.registrationassistant.service.data.DataAdapter;
import com.pearson.registrationassistant.util.CommonUtil;
import com.pearson.registrationassistant.util.JsonHelper;
import com.pearson.registrationassistant.vo.Applicant;
import com.pearson.registrationassistant.vo.UserSelection;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Reads and stores the data in JSON format
 *
 * Created by abhaykulkarni on 08/12/16.
 */
public class JsonFileAdapter implements DataAdapter {
    private Logger logger = Logger.getLogger(JsonFileAdapter.class);
    /**
     * This method only knows to read a file from a given path.
     * @param filePath - the location where the file needs to be read from
     * @return - a model object representing the applicant
     */
    @Override
    public Applicant readData(String filePath) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath + ".json"));
            String jsonString = new String(bytes);
            UserSelection userSelection = JsonHelper.getUserSelection(jsonString);
            return userSelection.getApplicant();
        } catch (IOException e) {
            logger.error("ERROR while reading from file: " + filePath, e);
            throw new RuntimeException(String.format("Couldn't read file: %s", filePath));
        }
    }

    /**
     * This method only knows to write data to a file at a given path.
     * @param object - the model object to write to
     * @param filePath - the location where the file needs to be writtten
     */
    @Override
    public <T> void writeData(T object, String filePath) {
        String jsonString = JsonHelper.convertToJson(object);
        try {
            CommonUtil.createFile(jsonString.getBytes(),filePath + ".json");
        } catch (Exception e) {
            logger.error("ERROR while writing to file: " + filePath,e);
            throw new RuntimeException(String.format("Couldn't write to path: %s\nData: %s", filePath, jsonString));
        }
    }
}

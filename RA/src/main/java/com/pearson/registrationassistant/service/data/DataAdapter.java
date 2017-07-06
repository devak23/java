package com.pearson.registrationassistant.service.data;

import com.pearson.registrationassistant.vo.Applicant;

/**
 * Interface for reading and writing data
 *
 * Created by abhaykulkarni on 08/12/16.
 */
public interface DataAdapter {
    Applicant readData(String filePath);

    <T> void writeData(T type, String filePath);
}

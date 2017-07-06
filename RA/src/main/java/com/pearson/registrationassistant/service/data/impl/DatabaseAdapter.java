package com.pearson.registrationassistant.service.data.impl;

import com.pearson.registrationassistant.service.data.DataAdapter;
import com.pearson.registrationassistant.vo.Applicant;

/**
 * Reads and stores the file in the database
 *
 * Created by abhaykulkarni on 08/12/16.
 */
public class DatabaseAdapter implements DataAdapter {
    @Override
    public Applicant readData(String filePath) {
        throw new UnsupportedOperationException("Method readData is not implemented yet");
    }

    @Override
    public <T> void writeData(T type, String filePath) {
        throw new UnsupportedOperationException("Method writeData is not implemented yet");
    }
}

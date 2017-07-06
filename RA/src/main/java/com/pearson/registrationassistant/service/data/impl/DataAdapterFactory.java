package com.pearson.registrationassistant.service.data.impl;

import com.pearson.registrationassistant.service.data.DataAdapter;
import com.pearson.registrationassistant.util.CommonUtil;
import com.pearson.registrationassistant.util.AppConstantsEnum;
import com.pearson.registrationassistant.util.PropertiesHelper;
import org.apache.log4j.Logger;

import javax.inject.Inject;

/**
 * A factory class deciding which adapter to use while persisting or reading
 * data
 *
 * Created by abhaykulkarni on 08/12/16.
 */
public final class DataAdapterFactory {
    private Logger logger = Logger.getLogger(DataAdapterFactory.class);

    @Inject
    private PropertiesHelper propertiesHelper;
    @Inject
    private JsonFileAdapter jsonFileAdapter;
    @Inject
    private XMLFileAdapter xmlFileAdapter;
    @Inject
    private DatabaseAdapter databaseAdapter;

    public DataAdapter getAdapter(String mode) {
        if (CommonUtil.isEmpty(mode)) {
            throw new RuntimeException("Cannot return an instance of an Adapter for 'null' mode of storage");
        }

        if (mode.equalsIgnoreCase("database")) {
            return databaseAdapter;
        }

        if (mode.equalsIgnoreCase("file")) {
            String format = propertiesHelper.getProperty(AppConstantsEnum.DATA_FORMAT.getValue());
            if (format.equalsIgnoreCase("json")) {
                return jsonFileAdapter;
            }

            if (format.equalsIgnoreCase("xml")) {
                return xmlFileAdapter;
            }
        }

        String mesg = String.format("No Adapter could be instantiated as the mode of the storage (%s) couldn't be determined.",mode);
        throw new RuntimeException(mesg);
    }
}

package com.pearson.registrationassistant.util;

/**
 * A file of all constants
 * Created by abhaykulkarni on 08/12/16.
 */
public enum AppConstantsEnum {
    DATA_FTP_ROOT("pearson.ra.data.ftp.location.root"),
    DATA_STORAGE_MODE("pearson.ra.data.storage.mode"),
    DATA_FORMAT("pearson.ra.data.format"),
    CLIENT_ABBR("pearson.ra.unqId.clientAbbr");

    private String value;
    AppConstantsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

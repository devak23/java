package com.ak.rnd.jsonsurferexamples.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

public interface Generator {
    /**
     * Generates CSV content from JSON input stream supplier and writes to output stream
     * This method allows for multiple passes over the JSON data by providing fresh input streams
     * 
     * @param inputStreamSupplier Supplier that provides fresh input streams for each processing pass
     * @param outputStream The output stream to write CSV data to
     * @throws Exception if any error occurs during generation
     */
    void generate(Supplier<InputStream> inputStreamSupplier, OutputStream outputStream) throws Exception;
}
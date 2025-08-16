package com.ak.rnd.jsonsurferexamples.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

public interface Generator {
    /**
     * Generates CSV content from JSON input stream using single-pass processing
     * Memory efficient approach suitable for large files and limited memory environments
     * 
     * @param jsonInputStream The input stream containing JSON data
     * @param outputStream The output stream to write CSV data to
     * @throws Exception if any error occurs during generation
     */
    void generate(InputStream jsonInputStream, OutputStream outputStream) throws Exception;
    
    /**
     * Generates CSV content from JSON input stream supplier and writes to output stream
     * This method allows for multiple passes over the JSON data by providing fresh input streams
     * Use only when you have repeatable data sources (like local files)
     * 
     * @param inputStreamSupplier Supplier that provides fresh input streams for each processing pass
     * @param outputStream The output stream to write CSV data to
     * @throws Exception if any error occurs during generation
     * @deprecated Use generate(InputStream, OutputStream) for better memory efficiency
     */
    @Deprecated
    void generate(Supplier<InputStream> inputStreamSupplier, OutputStream outputStream) throws Exception;
}
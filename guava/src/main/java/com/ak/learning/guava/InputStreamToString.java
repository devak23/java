package com.ak.learning.guava;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// convert InputStream to String
public class InputStreamToString {
    public static void main(String[] args) {
        String textWritten = "This article will provide you with 5 tips that can help you determine optimal Java heap size, as a starting point, for your current or new production environment. Some of these tips are also very useful regarding the prevention and resolution of java.lang.OutOfMemoryError problems; including memory leaks.";
        try (InputStream is = new ByteArrayInputStream(textWritten.getBytes())) {

            ByteSource source = new ByteSource() {
                @Override
                public InputStream openStream() throws IOException {
                    return is;
                }
            };

            String textRead = source.asCharSource(Charsets.UTF_8).read();
            System.out.println("text written into the stream: " + textWritten);
            System.out.println("text read from the stream: " + textRead);
            System.out.println(textWritten.equals(textRead));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

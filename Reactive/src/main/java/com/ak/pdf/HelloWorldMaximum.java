package com.ak.pdf;

import com.ak.reactive.utils.Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class HelloWorldMaximum {

    public static void main(String[] args) {
        HelloWorldMaximum h = new HelloWorldMaximum();
        h.createPdf("./out/03HelloWorldMax.pdf");
    }

    private void createPdf(String path) {
        Document doc = new Document(new Rectangle(14400, 14400));
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
            writer.setUserunit(75000f);
            doc.open();
            final String[] content = new String[1];
            Util.getQuotes().subscribe(item -> content[0] = item);
            doc.add(new Paragraph(content[0]));
            doc.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.ak.pdf;

import com.ak.reactive.utils.Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class HelloWithRectangle {

    public static void main(String[] args) {
        HelloWithRectangle h = new HelloWithRectangle();
        h.createPdf("./out/02HelloWithRectangle.pdf");
    }

    private void createPdf(String filename) {
        // Define the page size with a rectangle
        Rectangle pageSize = new Rectangle(600f, 920f);
        Document doc = new Document(pageSize, 20f, 20f, 50f, 50f);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(filename));
            final String[] content = new String[1];
            Util.getQuotes().subscribe(item -> content[0] = item);
            doc.open();
            doc.add(new Paragraph(content[0]));
            doc.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

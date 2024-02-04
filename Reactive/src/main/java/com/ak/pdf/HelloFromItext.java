package com.ak.pdf;

import com.ak.reactive.utils.Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class HelloFromItext {

    public static void main(String[] args) throws Exception {
        HelloFromItext h = new HelloFromItext();
        h.createSimplePdf("./out/01Hello.pdf");
    }

    public void createSimplePdf(String filename) throws DocumentException, FileNotFoundException {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(filename));
        doc.open();
        doc.add(new Paragraph(Util.faker().backToTheFuture().quote()));
        doc.close();
    }
}

package com.ak.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloWithLetterPageSize {
    private static final Path PATH = Paths.get("src/main/resources");

    public static void main(String[] args) throws Exception {
        Document doc = new Document(PageSize.LETTER.rotate());
        PdfWriter.getInstance(doc, new FileOutputStream("./out/04A4HelloWorld.pdf"));
        doc.open();
        doc.add(new Paragraph(Files.readString(PATH.resolve("quotes.txt"))));
        doc.close();
    }
}

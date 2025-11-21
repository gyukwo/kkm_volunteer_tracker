package kkm.pages;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javafx.stage.Stage;

//Got original shell from https://www.geeksforgeeks.org/java/creating-an-empty-pdf-document-using-java/
public class PDFPage {

    public static void createPDF (Stage stage) {
        // Path where the new PDF will be created
        Path path = Path.of("example2.pdf");

        // Create the PDF
        try (PDDocument doc = new PDDocument()) {

            // Add a blank page (equivalent to pdfDoc.addNewPage())
            PDPage page = new PDPage();
            doc.addPage(page);

            // Save the document
            doc.save(path.toFile());

            System.out.println("Your PDF has been created: " + path.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
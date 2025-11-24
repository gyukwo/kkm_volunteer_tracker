package kkm.pages;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javafx.stage.Stage;

public class PDFPage {

    public static void createPDF(Stage stage) {
        Path path = Path.of("example2.pdf");

        try (PDDocument doc = new PDDocument()) {

            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {

                contentStream.beginText();

                Standard14Fonts.FontName font_name = Standard14Fonts.FontName.HELVETICA_BOLD;
                PDFont pdfFont = new PDType1Font(font_name);
                contentStream.setFont(pdfFont, 16);

                contentStream.newLineAtOffset(70, 725);

                String text = "This is an example of adding text to a PDF using PDFBox.";
                contentStream.showText(text);

                contentStream.endText();
            }

            // Retrieving the page
            PDPage imagePage = doc.getPage(0);

            // Create image
            PDImageXObject pdImage = PDImageXObject.createFromFile(
                    "/Users/ryankwon/Documents/kkm_volunteer_tracker/kkm_logo.jpg",
                    doc);

            // Add image to existing page
            try (PDPageContentStream contents = new PDPageContentStream(doc, imagePage,
                    PDPageContentStream.AppendMode.APPEND, true, true)) {

                contents.drawImage(pdImage, 25, 250);
                System.out.println("Image inserted");
            }

            doc.save(path.toFile());
            System.out.println("Your PDF has been created: " + path.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

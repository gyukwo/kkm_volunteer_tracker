package kkm.pages;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javafx.stage.Stage;

public class PDFPage {

    public static void createPDF(Stage stage) {
        Path path = Path.of("example7.pdf");

        try (PDDocument doc = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            PDImageXObject logo = PDImageXObject.createFromFile(
                    "/Users/ryankwon/Documents/kkm_volunteer_tracker/kkm_logo.jpg",
                    doc);

            PDRectangle mediaBox = page.getMediaBox();
            float pageWidth = mediaBox.getWidth();
            float pageHeight = mediaBox.getHeight();

            float scale = 0.5f;
            float imgWidth = logo.getWidth() * scale;
            float imgHeight = logo.getHeight() * scale;

            float imgX = (pageWidth - imgWidth) / 2f;
            float imgY = pageHeight - imgHeight - 40f;

            Standard14Fonts.FontName fontName = Standard14Fonts.FontName.HELVETICA_BOLD;
            PDFont pdfFont = new PDType1Font(fontName);
            float fontSize = 16;
            float maxWidth = 500;

            List<String> wrapped = wrapText("This is an example of adding text to a PDF using PDFBox with a centered "
                    + "header image. The text is wrapped so that it does not spill off the "
                    + "right edge of the page. You can keep writing as much as you want here "
                    + "and it will automatically break into new lines.", pdfFont, fontSize, maxWidth);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {

                contentStream.drawImage(logo, imgX, imgY, imgWidth, imgHeight);

                contentStream.beginText();

                // drawWrappedText(contentStream, pdfFont, 16, 70, 650, 450, text);
                // writeWrappedText(contentStream, pdfFont, 16, text, 70, 650, 460);

                contentStream.setFont(pdfFont, fontSize);
                contentStream.newLineAtOffset(50, imgY - 20);

                for (String line : wrapped) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -fontSize);
                }

                contentStream.endText();

            }

            doc.save(path.toFile());
            System.out.println("Your PDF has been created: " + path.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> wrapText(String text, PDFont font, float fontSize, float maxWidth)
            throws IOException {

        List<String> lines = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            return lines;
        }

        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {

            String testLine = currentLine.length() == 0
                    ? word
                    : currentLine + " " + word;

            float size = font.getStringWidth(testLine) / 1000 * fontSize;

            if (size > maxWidth) {
                // current line full → push it & start new
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                // safe to append word
                currentLine = new StringBuilder(testLine);
            }
        }

        // Add the last line
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

}

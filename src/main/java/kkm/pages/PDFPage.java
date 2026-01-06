package kkm.pages;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import kkm.DB;
import kkm.Session;

public class PDFPage {

    public static void createPDF(Stage stage, int volunteerId) {
        Path path = Path.of("volunteer_certificate_" + volunteerId + ".pdf");

        String volunteerName = DB.getUserNameByUserId(volunteerId);
        if (volunteerName == null || volunteerName.trim().isEmpty()) {
            volunteerName = "Volunteer #" + volunteerId;
        }
        
        String adminName = Session.getUserName();

        double totalHours = Session.getTotalHours(volunteerId);

        LocalDate lastVolunteerDate = null;

        LocalDate issueDate = LocalDate.now();
        DateTimeFormatter issueFmt = DateTimeFormatter.ofPattern("MMMM d, yyyy");

        String issueDateText = "Issue Date: " + issueDate.format(issueFmt);
        String nameText = "Student Name: " + volunteerName;
        String supervisorText = "Name of Supervisor(s): " + adminName;
        String orgInfoText = "Issuing Organization: Kingdom Kids Ministry, 100 Rockland Ave, Norwood, NJ 07648, (201) 767-0400";

        String descriptionText =
            "Description of Volunteer Services: " +
            volunteerName +
            " has faithfully served with Kingdom Kids Ministry in various roles, " +
            "including assisting with weekly programs, special events, and other service activities.";

        String totalHoursText =
            "Total Hours Served: " + String.format("%.2f", totalHours) + " hours.";

        String lastDateText = "Last Date Volunteered: " +
            (lastVolunteerDate == null ? "N/A" : lastVolunteerDate.format(issueFmt));

        String evaluationText =
            "Evaluation: " +
            volunteerName +
            " has demonstrated reliability, a positive attitude, and a strong commitment to serving others. " +
            "Their contribution has been a blessing to the children and families in our community.";

        try (PDDocument doc = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            PDImageXObject logo = PDImageXObject.createFromFile(
                    "/Users/ryankwon/Documents/kkm_volunteer_tracker/kkm_logo.jpg",
                    doc);

            PDRectangle mediaBox = page.getMediaBox();
            float pageWidth = mediaBox.getWidth();
            float pageHeight = mediaBox.getHeight();

            float scale = 0.5f; // adjust as needed
            float imgWidth = logo.getWidth() * scale;
            float imgHeight = logo.getHeight() * scale;

            float imgX = (pageWidth - imgWidth) / 2f;
            float imgY = pageHeight - imgHeight - 40f; 

            PDFont titleFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDFont bodyFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            float titleFontSize = 20f;
            float bodyFontSize = 12f;

            float marginLeft = 50f;
            float maxTextWidth = pageWidth - 2 * marginLeft;

            List<String> titleLines = wrapText(
                    "Letter of Verification for Student Community Service",
                    titleFont, titleFontSize, maxTextWidth);
            
            List<String> issueLines = wrapText(issueDateText, bodyFont, bodyFontSize, maxTextWidth);
            List<String> nameLines = wrapText(nameText, bodyFont, bodyFontSize, maxTextWidth);
            List<String> supervisorLines = wrapText(supervisorText, bodyFont, bodyFontSize, maxTextWidth);
            List<String> orgLines = wrapText(orgInfoText, bodyFont, bodyFontSize, maxTextWidth);
            List<String> descLines = wrapText(descriptionText, bodyFont, bodyFontSize, maxTextWidth);
            List<String> totalLines = wrapText(totalHoursText, bodyFont, bodyFontSize, maxTextWidth);
            List<String> lastDateLines = wrapText(lastDateText, bodyFont, bodyFontSize, maxTextWidth);
            List<String> evalLines = wrapText(evaluationText, bodyFont, bodyFontSize, maxTextWidth);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                contentStream.drawImage(logo, imgX, imgY, imgWidth, imgHeight);

                float textStartY = imgY - 60f; 

                contentStream.beginText();
                contentStream.newLineAtOffset(marginLeft, textStartY);

                contentStream.setFont(titleFont, titleFontSize);
                for (String line : titleLines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, - (titleFontSize + 4));
                }
                contentStream.newLineAtOffset(0, - (bodyFontSize + 4));

                contentStream.setFont(bodyFont, bodyFontSize);

                printLines(contentStream, issueLines, bodyFontSize);
                printLines(contentStream, nameLines, bodyFontSize);
                printLines(contentStream, supervisorLines, bodyFontSize);
                printLines(contentStream, orgLines, bodyFontSize);
                printLines(contentStream, descLines, bodyFontSize);
                printLines(contentStream, totalLines, bodyFontSize);
                printLines(contentStream, lastDateLines, bodyFontSize);
                printLines(contentStream, evalLines, bodyFontSize);

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

            String testLine;
            if (currentLine.length() == 0) {
                testLine = word;
            } else {
                testLine = currentLine + " " + word;
            }

            float size = font.getStringWidth(testLine) / 1000 * fontSize;

            if (size > maxWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    private static void printLines(PDPageContentStream cs, List<String> lines, float fontSize) throws IOException {
        for (String line : lines) {
            cs.showText(line);
            cs.newLineAtOffset(0, - (fontSize + 2));
        }
        cs.newLineAtOffset(0, - (fontSize + 4));
    }

}

package com.project.telemedicine.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generatePrescriptionPdf(String patientName, String doctorName, String date, String prescription) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            addWatermark(writer);

            // Adding Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Telemedicine Prescription", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Adding Patient Details
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            document.add(new Paragraph("Patient: " + patientName, boldFont));
            document.add(new Paragraph("Doctor: " + doctorName, boldFont));
            document.add(new Paragraph("Date: " + date, boldFont));
            document.add(new Paragraph("\n"));

            // Adding Prescription
            Font prescriptionFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Prescription:", boldFont));
            document.add(new Paragraph(prescription, prescriptionFont));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    private static void addWatermark(PdfWriter writer) {
        PdfContentByte canvas = writer.getDirectContentUnder();
        Font watermarkFont = new Font(Font.HELVETICA, 50, Font.BOLD, new GrayColor(0.9f)); // Light gray
        Phrase watermark = new Phrase("TELEMEDICINE", watermarkFont);

        PdfGState gState = new PdfGState();
        gState.setFillOpacity(0.2f); // Adjust transparency

        canvas.setGState(gState);
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                watermark, 297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45); // Rotate the watermark
    }

}


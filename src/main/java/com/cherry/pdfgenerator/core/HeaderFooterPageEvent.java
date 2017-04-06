package com.cherry.pdfgenerator.core;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    private PdfTemplate template;
    private Image total;

    public void onOpenDocument(PdfWriter writer, Document document) {
    	template = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(template);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }


@Override
public void onEndPage(PdfWriter writer, Document document) {
    addHeader(writer);
    addFooter(writer);
}

private void addFooter(PdfWriter writer){
    PdfPTable footer = new PdfPTable(3);
    try {
        // set defaults
        footer.setWidths(new int[]{24, 2, 1});
        footer.setTotalWidth(527);
        footer.setLockedWidth(true);
        footer.getDefaultCell().setFixedHeight(40);
        footer.getDefaultCell().setBorder(Rectangle.TOP);
        footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

        // add copyright
        footer.addCell(new Phrase("\u00A9 cherrylabs.com", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

        // add current page count
        footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

        // add placeholder for total page count
        PdfPCell totalPageCount = new PdfPCell(total);
        totalPageCount.setBorder(Rectangle.TOP);
        totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
        footer.addCell(totalPageCount);

        // write page
        PdfContentByte canvas = writer.getDirectContent();
        canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
        footer.writeSelectedRows(0, -1, 34, 50, canvas);
        canvas.endMarkedContentSequence();
    } catch(DocumentException de) {
        throw new ExceptionConverter(de);
    }
}


private void addHeader(PdfWriter writer){
    PdfPTable header = new PdfPTable(2);
    try {
        // set defaults
        header.setWidths(new int[]{2, 24});
        header.setTotalWidth(527);
        header.setLockedWidth(true);
        header.getDefaultCell().setFixedHeight(40);
        header.getDefaultCell().setBorder(Rectangle.BOTTOM);
        header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

//        // add image
//        Image logo = Image.getInstance(HeaderFooterPageEvent.class.getResource("/memorynotfound-logo.jpg"));
//        header.addCell(logo);
        Image image = Image.getInstance ("image/cherrylabs-logo.png");
     	image.scaleAbsolute(160f, 140f);//image width,height
     	header.addCell(image);
     	
     	// add text
        PdfPCell text = new PdfPCell();
        text.setPaddingBottom(15);
        text.setPaddingLeft(10);
        text.setBorder(Rectangle.BOTTOM);
        text.setBorderColor(BaseColor.LIGHT_GRAY);
        text.addElement(new Phrase("iText PDF Header Footer Example", new Font(Font.FontFamily.HELVETICA, 12)));
        text.addElement(new Phrase("http://cherrylabs.com", new Font(Font.FontFamily.HELVETICA, 8)));
        header.addCell(text);

        // write content
        header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
    } catch(DocumentException de) {
        throw new ExceptionConverter(de);
    } catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void onCloseDocument(PdfWriter writer, Document document) {
    int totalLength = String.valueOf(writer.getPageNumber()).length();
    int totalWidth = totalLength * 5;
    ColumnText.showTextAligned(template, Element.ALIGN_RIGHT,
            new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
            totalWidth, 6, 0);
}
}

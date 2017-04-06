package com.cherry.pdfgenerator.core;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class PdfGen {

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
 
        try {
 
              		OutputStream file = new FileOutputStream(new File("result.pdf"));
	          		Document document = new Document(PageSize.A4, 36, 36, 90, 36);
	          		PdfWriter writer = PdfWriter.getInstance(document, file);  		
	          		 HeaderFooterPageEvent event = new HeaderFooterPageEvent();
	                 writer.setPageEvent(event);
	          		
	          		//Inserting Image in PDF
			     	
			//Inserting Table in PDF
			     	  PdfPTable table=new PdfPTable(3);
	                  PdfPCell cell = new PdfPCell (new Paragraph ("CHERRY_LABS_REPORT"));
				      cell.setColspan (3);
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPadding (10.0f);
				      cell.setBackgroundColor (new BaseColor (140, 221, 8));					               
 
				      table.addCell(cell);						               
				      table.addCell("Name");
				      table.addCell("Address");
				      table.addCell("Country");
                      table.addCell("cherry");
				      table.addCell("NC");
				      table.addCell("United States");
				      table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
				      table.setSpacingAfter(30.0f);        // Space After table starts, like margin-Bottom in CSS								          
			 //Text formating in PDF
				      Chunk chunk=new Chunk("Welecome To cherry labs");
					chunk.setUnderline(+1f,-2f);//1st co-ordinate is for line width,2nd is space between
					Chunk chunk1=new Chunk("cherrylabs.com");
					chunk1.setUnderline(+4f,-8f);
					chunk1.setBackground(new BaseColor (17, 46, 193));      
 
			 //Now Insert Every Thing Into PDF Document
					document.open();//PDF document opened........			       
					//document.add(image);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
                    document.add(new Paragraph("Dear cherry.com"));
	                document.add(new Paragraph("Document Generated On - "+new Date().toString()));	
			     	document.newPage();

					document.add(table);
 
					document.add(chunk);
					document.add(chunk1);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)							    
  
					document.close();
 
			        file.close();
 
            System.out.println("Pdf created successfully..");
 
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}


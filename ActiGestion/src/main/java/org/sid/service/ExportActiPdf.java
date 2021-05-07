package org.sid.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ExportActiPdf {
	public static ByteArrayInputStream actiPDFreport(List Acti) {
		Document document = new Document ( );
		ByteArrayOutputStream out = new ByteArrayOutputStream ( );
		try {
			PdfWriter.getInstance ( document , out );
			document.open ( );
			com.itextpdf.text.Font font = FontFactory.getFont ( FontFactory.COURIER , 14 , BaseColor.BLACK );
			Paragraph para = new Paragraph ( "Actis List" , font );
			para.setAlignment ( Element.ALIGN_CENTER );
			document.add ( para );
			document.add ( Chunk.NEWLINE );
			document.close ( );
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ByteArrayInputStream ( out.toByteArray ( ) );
	}
}

package cn.itext.envoice;

import java.io.FileNotFoundException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

public class EnvoiceTest {

	public static void main(String[] args) {
		getEnvoice();

	}

	public static void getEnvoice() {
		String path = "envoice.pdf";
		try {
			PdfWriter pw = new PdfWriter(path);
			PdfDocument pd = new PdfDocument(pw);
			pd.setDefaultPageSize(PageSize.A4);
			Document doc = new Document(pd);

			float twocol = 285f;
			float twocol150 = twocol + 150f;
			float twocolumnwidth[] = { twocol150, twocol };

			Table tbl = new Table(twocolumnwidth);
			tbl.addCell(new Cell().add("Incoice").setBorder(Border.NO_BORDER).setBold());
			Table nesttable = new Table(new float[] { twocol / 2, twocol / 2 });
			nesttable.addCell(new Cell().add("Invoice No").setBold());
			nesttable.addCell(new Cell().add("50936044338"));
			nesttable.addCell(new Cell().add("Invoice date"));
			nesttable.addCell(new Cell().add("Invoice No"));
			tbl.addCell(new Cell().add("2nd cell").setBorder(Border.NO_BORDER));
			// tbl.addCell(nesttable.setBorder(Border.NO_BORDER));
			doc.add(tbl);
			doc.close();
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		}
	}
}

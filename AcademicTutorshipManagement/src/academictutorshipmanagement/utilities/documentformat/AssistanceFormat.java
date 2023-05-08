/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academictutorshipmanagement.utilities.documentformat;


import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author oband
 */
public class AssistanceFormat {
    private ArrayList<InnerAsistance> asistanceList = new ArrayList();
    
    public void addAsistance(AcademicPersonnel academicPersonnel,ArrayList<Integer> asistance){
        InnerAsistance newAsistance = new InnerAsistance(academicPersonnel,asistance);
        asistanceList.add(newAsistance);   
    }
    
    public void generateDocument(File signatureFile) throws IOException{
        PdfWriter pdfWriter = new PdfWriter(signatureFile);
        PdfDocument documentPDF = new PdfDocument(pdfWriter);
        documentPDF.setDefaultPageSize(PageSize.A4.rotate());
        Document asistanceDocument = new Document(documentPDF);
        this.loadHeaderData(asistanceDocument);
        this.loadAsistance(asistanceDocument);
        asistanceDocument.close();
    }
    
    private void loadHeaderData(Document asistanceDocument){
    }
    
    private void loadAsistance(Document asistanceDocument){
        Table tableAsistance = this.generateFormatTable();
        for(InnerAsistance asistance: asistanceList){
            Cell studentName = new Cell().add(new Paragraph(asistance.getTutor().getFullName()));
            Cell asistanceFirst = new Cell().add(new Paragraph(Integer.toString(asistance.getAsistance().get(0))));
            Cell asistanceSecond = new Cell().add(new Paragraph(Integer.toString(asistance.getAsistance().get(1))));
            Cell asistanceThird = new Cell().add(new Paragraph(Integer.toString(asistance.getAsistance().get(2))));
            tableAsistance.addCell(studentName);
            tableAsistance.addCell(asistanceFirst);
            tableAsistance.addCell(asistanceSecond);
            tableAsistance.addCell(asistanceThird);
        }
        asistanceDocument.add(tableAsistance);
    }
    
    private Table generateFormatTable(){
        final float COLUMNS_SIZE[] = {250f,200f,200f,200f};
        Table tableAsistance = new Table(COLUMNS_SIZE);
        Cell headerName = new Cell().add(new Paragraph("Academico")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureFirst = new Cell().add(new Paragraph("1er Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureSecond = new Cell().add(new Paragraph("2da Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureThird = new Cell().add(new Paragraph("3ra Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        tableAsistance.addCell(headerName);
        tableAsistance.addCell(headerSignatureFirst);
        tableAsistance.addCell(headerSignatureSecond);
        tableAsistance.addCell(headerSignatureThird);
        return tableAsistance;
    }
    
    
    
    
    
    private class InnerAsistance{
        private AcademicPersonnel tutor = new AcademicPersonnel();
        private ArrayList<Integer> asistance = new ArrayList();

        public AcademicPersonnel getTutor() {
            return tutor;
        }

        public void setTutor(AcademicPersonnel tutor) {
            this.tutor = tutor;
        }

        public ArrayList<Integer> getAsistance() {
            return asistance;
        }

        public void setAsistance(ArrayList<Integer> asistance) {
            this.asistance = asistance;
        }

        public InnerAsistance(AcademicPersonnel academicPersonnel,ArrayList<Integer> asistance) {
            this.tutor = academicPersonnel;
            this.asistance = asistance;
        }
        
    
        
    
    }
}

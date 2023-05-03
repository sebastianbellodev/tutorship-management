/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academictutorshipmanagement.utilities.DocumentFormat;

import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
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
public class SignatureList {
    
    private ArrayList<Student> studentsList = new ArrayList<>();
    private AcademicPersonnel academicPersonnel = new AcademicPersonnel();
    private SchoolPeriod schoolPeriod = new SchoolPeriod();

    public SignatureList(){}
    
    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(ArrayList<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public AcademicPersonnel getAcademicPersonnel() {
        return academicPersonnel;
    }

    public void setAcademicPersonnel(AcademicPersonnel academicPersonnel) {
        this.academicPersonnel = academicPersonnel;
    }

    public SchoolPeriod getSchoolPeriod() {
        return schoolPeriod;
    }

    public void setSchoolPeriod(SchoolPeriod schoolPeriod) {
        this.schoolPeriod = schoolPeriod;
    }
    
    
    
    public void generateDocument(File signatureFile) throws IOException{
        PdfWriter pdfWriter = new PdfWriter(signatureFile);
        PdfDocument documentPDF = new PdfDocument(pdfWriter);
        documentPDF.setDefaultPageSize(PageSize.A4.rotate());
        Document signatureDocument = new Document(documentPDF);
        this.loadHeaderData(signatureDocument);
        this.loadStudents(signatureDocument);
        signatureDocument.close();
    }
    
    
    private void loadHeaderData(Document signatureDocument){
        signatureDocument.add(
            new Paragraph ("Tutor: "+ this.academicPersonnel.getFullName()+"\tPrograma Educativo: "+ this.academicPersonnel.getUser().getEducationalProgram().getName())).setBold().setTextAlignment(TextAlignment.LEFT);
        /*signatureDocument.add(
            new Paragraph ("\tPrograma Educativo: "+ this.academicPersonnel.getUser().getEducationalProgram().getName())).setBold().setTextAlignment(TextAlignment.LEFT);*/
        signatureDocument.add(
            new Paragraph ("\tPeriodo Escolar: "+ this.schoolPeriod.toString())).setBold().setTextAlignment(TextAlignment.LEFT);
    }
    
    private void loadStudents(Document signatureDocument){
        signatureDocument.add(
            new Paragraph ("\nLista de Estudiantes:")).setBold().setTextAlignment(TextAlignment.LEFT);
            Table tableSignatureStudents = this.generateFormatTable();
        this.studentsList.forEach(student->{
                Cell studentMatricula = new Cell().add(new Paragraph(student.getRegistrationNumber()));
                Cell studentName = new Cell().add(new Paragraph(student.getFullName()));
                Cell studentSignatureFirst = new Cell();
                Cell studentSignatureSecond = new Cell();
                Cell studentSignatureThird = new Cell();
                Cell studentEspecial = new Cell();
                Cell studentNotation = new Cell();
                Cell studentRisk = new Cell();
                tableSignatureStudents.addCell(studentMatricula);
                tableSignatureStudents.addCell(studentName);
                tableSignatureStudents.addCell(studentSignatureFirst);
                tableSignatureStudents.addCell(studentSignatureSecond);
                tableSignatureStudents.addCell(studentSignatureThird);
                tableSignatureStudents.addCell(studentEspecial);
                tableSignatureStudents.addCell(studentNotation);
                tableSignatureStudents.addCell(studentRisk);
        });
        signatureDocument.add(tableSignatureStudents);   
    }
    
    private Table generateFormatTable(){
        final float COLUMNS_SIZE[] = {100f,150f,100f,100f,100f,100f,225f,75f};
        Table tableSignatureStudents = new Table(COLUMNS_SIZE);
        Cell headerStudentMatricula = new Cell().add(new Paragraph("Matricula")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerStudentName = new Cell().add(new Paragraph("Estudiante")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureFirst = new Cell().add(new Paragraph("1er Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureSecond = new Cell().add(new Paragraph("2da Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureThird = new Cell().add(new Paragraph("3ra Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureEspecial = new Cell().add(new Paragraph("Fecha Especial")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerNotations = new Cell().add(new Paragraph("Observaciones")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerRisk = new Cell().add(new Paragraph("Riesgo")).setBold().setTextAlignment(TextAlignment.CENTER);
        tableSignatureStudents.addCell(headerStudentMatricula);
        tableSignatureStudents.addCell(headerStudentName);
        tableSignatureStudents.addCell(headerSignatureFirst);
        tableSignatureStudents.addCell(headerSignatureSecond);
        tableSignatureStudents.addCell(headerSignatureThird);
        tableSignatureStudents.addCell(headerSignatureEspecial);
        tableSignatureStudents.addCell(headerNotations);
        tableSignatureStudents.addCell(headerRisk);
        return tableSignatureStudents;
    }
}

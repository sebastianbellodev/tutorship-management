/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.utilities.documentformat;

import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.SessionInformation;
import academictutorshipmanagement.views.QueryAcademicTutorshipGeneralReportFXMLController.InnerAcademicProblem;
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
 * @author sebtr
 */
public class AcademicTutorshipGeneralReport {

    private ArrayList<AcademicTutorshipReport> academicTutorshipReports;
    private ArrayList<InnerAcademicProblem> academicProblems;
    private AcademicTutorshipSession academicTutorshipSession;
    private String numberOfStudentsAttending;
    private String numberOfStudentsAtRisk;

    public AcademicTutorshipGeneralReport() {}

    public void generateDocument(File signatureFile) throws IOException {
        PdfWriter pdfWriter = new PdfWriter(signatureFile);
        PdfDocument documentPDF = new PdfDocument(pdfWriter);
        documentPDF.setDefaultPageSize(PageSize.A4.rotate());
        Document academicTutorshipGeneralReportDocument = new Document(documentPDF);
        loadHeaderData(academicTutorshipGeneralReportDocument);
        loadAcademicProblems(academicTutorshipGeneralReportDocument);
        loadAcademicTutorshipReports(academicTutorshipGeneralReportDocument);
        academicTutorshipGeneralReportDocument.close();
    }

    private void loadHeaderData(Document academicTutorshipGeneralReportDocument) {
        academicTutorshipGeneralReportDocument.add(
                new Paragraph("Coordinador de Tutorías Académicas: " + SessionInformation.getSessionInformation().getAcademicPersonnel()
                        + "\tPrograma Educativo: " + SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram())).setBold().setTextAlignment(TextAlignment.LEFT);
        academicTutorshipGeneralReportDocument.add(
                new Paragraph("\tPeriodo Escolar: " + SessionInformation.getSessionInformation().getSchoolPeriod()
                        + "\tFecha de sesión de tutoría académica: " + academicTutorshipSession)).setBold().setTextAlignment(TextAlignment.LEFT);
        academicTutorshipGeneralReportDocument.add(
                new Paragraph("\tNúmero de estudiantes asistentes: " + numberOfStudentsAttending)).setBold().setTextAlignment(TextAlignment.LEFT);
        academicTutorshipGeneralReportDocument.add(
                new Paragraph("\tNúmero de estudiantes en riesgo: " + numberOfStudentsAtRisk)).setBold().setTextAlignment(TextAlignment.LEFT);
    }
    
    private void loadAcademicProblems(Document academicTutorshipGeneralReportDocument){
        academicTutorshipGeneralReportDocument.add(
            new Paragraph ("\nConcentrado de problemáticas académicas reportadas por estudiantes:")).setBold().setTextAlignment(TextAlignment.LEFT);
            Table tableAcademicProblems = this.generateFormatAcademicProblemsTable();
        for (InnerAcademicProblem academicProblem : academicProblems){
            Cell educationalExperience = new Cell().add(new Paragraph(academicProblem.getInnerEducationalExperience()));
            Cell academicPersonnel = new Cell().add(new Paragraph(academicProblem.getInnerAcademicPersonnel()));
            Cell title = new Cell().add(new Paragraph(academicProblem.getTitle()));
            Cell students = new Cell().add(new Paragraph(academicProblem.getInnerNumberOfReports()));
            tableAcademicProblems.addCell(educationalExperience);
            tableAcademicProblems.addCell(academicPersonnel);
            tableAcademicProblems.addCell(title);
            tableAcademicProblems.addCell(students);
        }
        academicTutorshipGeneralReportDocument.add(tableAcademicProblems);   
    }
    
    private Table generateFormatAcademicProblemsTable(){
        final float COLUMNS_SIZE[] = {250f,200f,200f,200f};
        Table tableAcademicProblems = new Table(COLUMNS_SIZE);
        Cell headerEducationalExperience = new Cell().add(new Paragraph("Experiencia educativa")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerAcademicPersonnel = new Cell().add(new Paragraph("Personal académico")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerTitle = new Cell().add(new Paragraph("Título")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerStudents = new Cell().add(new Paragraph("Estudiantes")).setBold().setTextAlignment(TextAlignment.CENTER);
        tableAcademicProblems.addCell(headerEducationalExperience);
        tableAcademicProblems.addCell(headerAcademicPersonnel);
        tableAcademicProblems.addCell(headerTitle);
        tableAcademicProblems.addCell(headerStudents);
        return tableAcademicProblems;
    }
    
    private void loadAcademicTutorshipReports(Document academicTutorshipGeneralReportDocument){
        academicTutorshipGeneralReportDocument.add(
            new Paragraph ("\nComentarios generales por tutor académico:")).setBold().setTextAlignment(TextAlignment.LEFT);
            Table tableAcademicTutorshipReports = this.generateFormatAcademicTutoshipReportsTable();
        for (AcademicTutorshipReport academicTutorshipReport : academicTutorshipReports){
            Cell academicPersonnel = new Cell().add(new Paragraph(academicTutorshipReport.getAcademicPersonnel().toString()));
            Cell generalComment = new Cell().add(new Paragraph(academicTutorshipReport.getGeneralComment()));
            tableAcademicTutorshipReports.addCell(academicPersonnel);
            tableAcademicTutorshipReports.addCell(generalComment);
        }
        academicTutorshipGeneralReportDocument.add(tableAcademicTutorshipReports);   
    }
    
    private Table generateFormatAcademicTutoshipReportsTable(){
        final float COLUMNS_SIZE[] = {250f,600f};
        Table tableAcademicProblems = new Table(COLUMNS_SIZE);
        Cell headerEducationalExperience = new Cell().add(new Paragraph("Personal académico")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerAcademicPersonnel = new Cell().add(new Paragraph("Comentario general")).setBold().setTextAlignment(TextAlignment.CENTER);
        tableAcademicProblems.addCell(headerEducationalExperience);
        tableAcademicProblems.addCell(headerAcademicPersonnel);
        return tableAcademicProblems;
    }

    public ArrayList<AcademicTutorshipReport> getAcademicTutorshipReports() {
        return academicTutorshipReports;
    }

    public void setAcademicTutorshipReports(ArrayList<AcademicTutorshipReport> academicTutorshipReports) {
        this.academicTutorshipReports = academicTutorshipReports;
    }

    public ArrayList<InnerAcademicProblem> getAcademicProblems() {
        return academicProblems;
    }

    public void setAcademicProblems(ArrayList<InnerAcademicProblem> academicProblems) {
        this.academicProblems = academicProblems;
    }

    public AcademicTutorshipSession getAcademicTutorshipSession() {
        return academicTutorshipSession;
    }

    public void setAcademicTutorshipSession(AcademicTutorshipSession academicTutorshipSession) {
        this.academicTutorshipSession = academicTutorshipSession;
    }

    public String getNumberOfStudentsAttending() {
        return numberOfStudentsAttending;
    }

    public void setNumberOfStudentsAttending(String numberOfStudentsAttending) {
        this.numberOfStudentsAttending = numberOfStudentsAttending;
    }

    public String getNumberOfStudentsAtRisk() {
        return numberOfStudentsAtRisk;
    }

    public void setNumberOfStudentsAtRisk(String numberOfStudentsAtRisk) {
        this.numberOfStudentsAtRisk = numberOfStudentsAtRisk;
    }

}

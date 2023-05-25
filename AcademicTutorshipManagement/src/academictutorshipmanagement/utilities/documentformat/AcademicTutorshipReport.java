/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.utilities.documentformat;

import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.SessionInformation;
import academictutorshipmanagement.views.QueryAcademicTutorshipReportByAcademicTutorFXMLController;
import academictutorshipmanagement.views.QueryAcademicTutorshipReportByAcademicTutorFXMLController.InnerStudent;
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
public class AcademicTutorshipReport {

    private ArrayList<QueryAcademicTutorshipReportByAcademicTutorFXMLController.InnerStudent> students;
    private AcademicTutorshipSession academicTutorshipSession;
    private String numberOfStudentsAttending;
    private String numberOfStudentsAtRisk;
    private String generalComment;
    
    private String yes = "Sí";
    private String no = "No";

    public AcademicTutorshipReport() {}

    public void generateDocument(File signatureFile) throws IOException {
        PdfWriter pdfWriter = new PdfWriter(signatureFile);
        PdfDocument documentPDF = new PdfDocument(pdfWriter);
        documentPDF.setDefaultPageSize(PageSize.A4.rotate());
        Document academicTutorshipReportDocument = new Document(documentPDF);
        loadHeaderData(academicTutorshipReportDocument);
        loadStudents(academicTutorshipReportDocument);
        academicTutorshipReportDocument.close();
    }

    private void loadHeaderData(Document academicTutorshipReportDocument) {
        academicTutorshipReportDocument.add(
                new Paragraph("Tutor Académico: " + SessionInformation.getSessionInformation().getAcademicPersonnel()
                        + "\tPrograma Educativo: " + SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram())).setBold().setTextAlignment(TextAlignment.LEFT);
        academicTutorshipReportDocument.add(
                new Paragraph("\tPeriodo Escolar: " + SessionInformation.getSessionInformation().getSchoolPeriod()
                        + "\tFecha de sesión de tutoría académica: " + academicTutorshipSession)).setBold().setTextAlignment(TextAlignment.LEFT);
        academicTutorshipReportDocument.add(
                new Paragraph("\tNúmero de estudiantes asistentes: " + numberOfStudentsAttending)).setBold().setTextAlignment(TextAlignment.LEFT);
        academicTutorshipReportDocument.add(
                new Paragraph("\tNúmero de estudiantes en riesgo: " + numberOfStudentsAtRisk)).setBold().setTextAlignment(TextAlignment.LEFT);
        academicTutorshipReportDocument.add(
                new Paragraph("\n\tComentario general: " + generalComment)).setBold().setTextAlignment(TextAlignment.LEFT);
    }
    
    private void loadStudents(Document academicTutorshipReportDocument){
        academicTutorshipReportDocument.add(
            new Paragraph ("\nConcentrado de estudiantes asistentes a la sesión de tutoría academica y en riesgo:")).setBold().setTextAlignment(TextAlignment.LEFT);
            Table tableStudents = this.generateFormatStudentsTable();
        for (InnerStudent student : students){
            Cell registrationNumber = new Cell().add(new Paragraph(student.getRegistrationNumber()));
            Cell name = new Cell().add(new Paragraph(student.getFullName()));
            Cell attended = (student.getAttendedBy().isSelected()) 
                    ? new Cell().add(new Paragraph(yes)) 
                    : new Cell().add(new Paragraph(no));
            Cell atRisk = (student.getAtRisk().isSelected()) 
                    ? new Cell().add(new Paragraph(yes)) 
                    : new Cell().add(new Paragraph(no));
            tableStudents.addCell(registrationNumber);
            tableStudents.addCell(name);
            tableStudents.addCell(attended);
            tableStudents.addCell(atRisk);
        }
        academicTutorshipReportDocument.add(tableStudents);   
    }
    
    private Table generateFormatStudentsTable(){
        final float COLUMNS_SIZE[] = {250f,400f,100f,100f};
        Table tableAcademicProblems = new Table(COLUMNS_SIZE);
        Cell headerRegistrationNumber = new Cell().add(new Paragraph("Matrícula")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerName = new Cell().add(new Paragraph("Nombre")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerAttended = new Cell().add(new Paragraph("Asistió")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerAtRisk = new Cell().add(new Paragraph("En riesgo")).setBold().setTextAlignment(TextAlignment.CENTER);
        tableAcademicProblems.addCell(headerRegistrationNumber);
        tableAcademicProblems.addCell(headerName);
        tableAcademicProblems.addCell(headerAttended);
        tableAcademicProblems.addCell(headerAtRisk);
        return tableAcademicProblems;
    }

    public ArrayList<InnerStudent> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<InnerStudent> students) {
        this.students = students;
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

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }
    
}

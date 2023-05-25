/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academictutorshipmanagement.utilities.documentformat;


import academictutorshipmanagement.model.pojo.AcademicPersonnel;
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
import javafx.collections.ObservableList;

/**
 *
 * @author oband
 */
public class AssistanceFormat {
    private ArrayList<InnerAsistance> asistanceList = new ArrayList();
    private ArrayList<InnerAsistance> studentsNames = new ArrayList();
    private ArrayList<InnerAsistance> allStudentsList = new ArrayList();

    
    
    public void addAsistance(AcademicPersonnel academicPersonnel,ArrayList<Integer> asistance){
        InnerAsistance newAsistance = new InnerAsistance(academicPersonnel,asistance);
        asistanceList.add(newAsistance);   
    }
    
    public void addStudents(Student student){
        InnerAsistance newStudent = new InnerAsistance(student);
        studentsNames.add(newStudent); 
    }
    
    public void addAllStudents(Student student, int tutorshipSession){
        InnerAsistance newStudent = new InnerAsistance(student, tutorshipSession);
        allStudentsList.add(newStudent);
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
        
    public void generateTutorDocument(File signatureFile)throws IOException{
        PdfWriter pdfWriter = new PdfWriter(signatureFile);
        PdfDocument documentPDF = new PdfDocument(pdfWriter);
        documentPDF.setDefaultPageSize(PageSize.A4.rotate());
        Document asistanceDocument = new Document(documentPDF);
        this.loadHeaderData(asistanceDocument);
        this.loadAsistance(asistanceDocument);
        this.loadStudents(asistanceDocument);
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
         
    private void loadStudents(Document asistanceDocument) {
        Table tableStudents = this.generateFormatTableStudentsTutorship();
        for (InnerAsistance studentNames : studentsNames) {
            loadStudentAsistance(studentNames);
            loadStudentInformation(studentNames, tableStudents);
        }
        asistanceDocument.add(tableStudents);
    }
    
    private void loadStudentAsistance(InnerAsistance studentNames) {
        ArrayList<Boolean> asistance = new ArrayList(3);
        int index = 0;
        
        for (InnerAsistance studentInfo : allStudentsList) {
            if (chechk(studentNames, studentInfo)) {
                if(studentInfo.getStudent().getAttendedBy().isSelected()){
                    asistance.add(true);
                    index++;
                }else{
                    asistance.add(false);
                }
            }            
        }               
        completeArray(index, asistance, studentNames);        
    }
    
    private void completeArray(int index, ArrayList<Boolean> asistance, InnerAsistance studentNames){
        if (index == 0) {
            asistance.add(false);
            asistance.add(false);  
        } else if (index == 1) {
            asistance.add(false);
            asistance.add(false);
        } else if (index == 2) {
            asistance.add(false);
        }
        studentNames.setAsistenceBySession(asistance);
    }
    
    private boolean chechk(InnerAsistance studentName, InnerAsistance studentInfo){
        Boolean check = false;
        String registrationnNumber1 = studentName.getStudent().getRegistrationNumber();
        String registrationNumber2 = studentInfo.getStudent().getRegistrationNumber();
        if(registrationnNumber1.contentEquals(registrationNumber2)){
            check = true;
        }
        return check;
    }
    
    private void loadStudentInformation(InnerAsistance student, Table tableStudents){        
        Cell registrationNumber = new Cell().add(new Paragraph(student.getStudent().getRegistrationNumber()));
        Cell name = new Cell().add(new Paragraph(student.getStudent().getFullName()));
        tableStudents.addCell(registrationNumber);
        tableStudents.addCell(name);
        loadAsistanceByStudent(tableStudents, student);
        
    }


    private void loadAsistanceByStudent(Table tableStudents, InnerAsistance student){
        ArrayList<Boolean> asistence = student.getAsistenceBySession();
        
        /*for(Boolean asistenceValue : asistence){
            if (asistenceValue) {
                Cell asistance = new Cell().add(new Paragraph("Asistio"));
                tableStudents.addCell(asistance);
            } else if(!asistenceValue || asistenceValue == null) {
                Cell asistance = new Cell().add(new Paragraph("No asistio"));
                tableStudents.addCell(asistance);
            }
        }*/
        
        for(int i = 0; i<3; i++){
            if (asistence.get(i)) {
                Cell asistance = new Cell().add(new Paragraph("Asistio"));
                tableStudents.addCell(asistance);
            } else if(!asistence.get(i) || asistence.get(i) == null) {
                Cell asistance = new Cell().add(new Paragraph("No asistio"));
                tableStudents.addCell(asistance);
            }
        }
    }
    
    private Table generateFormatTableStudentsTutorship(){
        final float COLUMNS_SIZE[] = {80f,250f,150f,150f, 150f};
        Table tableStudents = new Table(COLUMNS_SIZE);       
        Cell registrationNumber = new Cell().add(new Paragraph("Matricula")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell studentName = new Cell().add(new Paragraph("Nombre")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureFirst = new Cell().add(new Paragraph("1er Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureSecond = new Cell().add(new Paragraph("2da Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        Cell headerSignatureThird = new Cell().add(new Paragraph("3ra Fecha")).setBold().setTextAlignment(TextAlignment.CENTER);
        tableStudents.addCell(registrationNumber);
        tableStudents.addCell(studentName);
        tableStudents.addCell(headerSignatureFirst);
        tableStudents.addCell(headerSignatureSecond);
        tableStudents.addCell(headerSignatureThird);
        return tableStudents;
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
        private ArrayList<Boolean> asistenceBySession = new ArrayList();
        private Student student = new Student();
        private int tutorshipSession;
        private boolean found;

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

        public int getTutorshipSession() {
            return tutorshipSession;
        }        

        public void setAsistenceBySession(ArrayList<Boolean> asistenceBySession) {
            this.asistenceBySession = asistenceBySession;
        }

        public ArrayList<Boolean> getAsistenceBySession() {
            return asistenceBySession;
        }
                
        
        public InnerAsistance(AcademicPersonnel academicPersonnel,ArrayList<Integer> asistance) {
            this.tutor = academicPersonnel;
            this.asistance = asistance;
        }

        public InnerAsistance(Student student) {
            this.student = student;
            this.found = false;
        }

        public void setFound(boolean found) {
            this.found = found;
        }

        public boolean getFound() {
            return found;
        }
        
        
        public InnerAsistance(Student student, int session) {
            this.student = student;
            this.tutorshipSession = session;
        }
        
        public Student getStudent() {
            return student;
        }    
        
        
    }
}

package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class ServiceStudentTests {

    private static Service service;

    @BeforeEach
    void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    void addStudent1() {
        Iterable<Student> studentsBefore = service.findAllStudents();
        int beforeCount = 0;
        for (Student student : studentsBefore) {
            beforeCount++;
        }

        service.saveStudent("77", "TestMan Test", 533);
        Iterable<Student> studs = service.findAllStudents();
        for (Student student : studentsBefore) {
            beforeCount--;
        }

        assertEquals(-1, beforeCount);
        service.deleteStudent("77");
    }

    @Test
    void addStudent2() {
        service.saveStudent("77", "Ricárdó Kanalas", 522);
        Iterable<Student> studs = service.findAllStudents();
        boolean found = false;
        for (Student stud : studs) {
            if (stud.getID().equals("77")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
        service.deleteStudent("77");
    }

    @Test
    void addAndDeleteStudent() {
        Iterable<Student> studentsBefore = service.findAllStudents();
        int beforeCount = 0;
        for (Student student : studentsBefore) {
            beforeCount++;
        }
        int afterCount = 0;
        service.saveStudent("77", "TestMan Test", 533);
        service.deleteStudent("77");
        Iterable<Student> studs = service.findAllStudents();
        for (Student student : studentsBefore) {
            afterCount++;
        }
        assertEquals(beforeCount, afterCount);

    }
}
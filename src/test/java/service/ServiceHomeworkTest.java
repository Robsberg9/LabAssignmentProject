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

class ServiceHomeworkTest {

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
    void addHomework1() {
        Iterable<Homework> hwBefore = service.findAllHomework();
        int beforeCount = 0;
        for (Homework hw : hwBefore) {
            beforeCount++;
        }

        service.saveHomework("100", "New homework", 9,8);
        hwBefore = service.findAllHomework();
        for (Homework hw : hwBefore) {
            beforeCount--;
        }

        assertEquals(-1, beforeCount);
        service.deleteHomework("100");
    }

    @Test
    void addHomework2() {
        service.saveHomework("101", "Ricárdó Kanalas", 6,4);
        Iterable<Homework> homeworks = service.findAllHomework();
        boolean found = false;
        for (Homework hw : homeworks) {
            if(hw.getID().equals("101")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
        service.deleteHomework("101");
    }

    @Test
    void addAndRemoveHomework() {
        Iterable<Homework> homeworkBefore = service.findAllHomework();
        int beforeCount = 0;
        for (Homework homework : homeworkBefore) {
            beforeCount++;
        }
        int afterCount = 0;
        service.saveHomework("102", "Ricárdó Kanalas", 6,4);
        service.deleteHomework("102");
        homeworkBefore = service.findAllHomework();
        for (Homework homework : homeworkBefore) {
            afterCount++;
        }
        assertEquals(beforeCount, afterCount);

    }
}
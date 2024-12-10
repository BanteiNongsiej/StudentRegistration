package com.example.StudentRegistration.Controller;

import com.example.StudentRegistration.Model.Student;
import com.example.StudentRegistration.Model.Subject;
import com.example.StudentRegistration.Service.CourseService;
import com.example.StudentRegistration.Service.StudentService;
import com.example.StudentRegistration.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/createStudent")
    public String createStudent(@RequestParam(required = false) Long student_id, Model model, Authentication auth) {
        if(auth.isAuthenticated()) {
            // Load student object for the form
            Student student = (student_id != null)
                    ? studentService.getStudentById(student_id).orElse(new Student())
                    : new Student();

            // Populate the model for the form and the list
            System.out.println("STUDENT IN STUDENT CONTROLLER:" + student.toString());
            model.addAttribute("student", student);
//        model.addAttribute("newStudent", student_id == null);
            model.addAttribute("courses", courseService.getAllCourse());
            model.addAttribute("listStudents", studentService.getAllStudent());
            return "/StudentForm"; // Load the same template
        }
        else{
            return "redirect:/loginPage";
        }
    }



//    @PostMapping("/saveStudent")
//    public String saveStudent(@ModelAttribute("student") Student student){
//        studentService.saveStudent(student);
//        return "redirect:/student/createStudent";
//    }
//
//    @PostMapping("/updateStudent")
//    public String updateStudent(Student student){
//        studentService.updateStudent(student);
//        return "redirect:/student/createStudent";
//    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdateStudent(@ModelAttribute("student") Student student) {
        if (student.getStudent_id() == null) {
            studentService.saveStudent(student); // Save new student
        } else {
            studentService.updateStudent(student); // Update existing student
        }
        return "redirect:/student/createStudent"; // Refresh the page to show updates
    }

    @GetMapping("/deleteStudent/{student_id}")
    public String deleteStudent(@PathVariable Long student_id){
        studentService.deleteStudent(student_id);
        return "redirect:/student/createStudent";
    }

    @GetMapping("/getStudent/{student_id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable Long student_id){
        Optional<Student> student=studentService.getStudentById(student_id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/subjectList/{course_id}")
    public ResponseEntity<Set<Subject>> getSubjectByCourseId(@PathVariable int course_id){
        Set<Subject> subjects=courseService.getSubjectsByCourseId(course_id);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/listStudents")
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudent();
        model.addAttribute("listStudents", students);
        return "StudentList";  // Assuming you have a view called "StudentList.html"
    }

}

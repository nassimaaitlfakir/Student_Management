package org.example.studentslist;


import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import javax.naming.NamingException;

import java.util.List;

public class StudentBean {

    private Student student = new Student();
    private List<Student> students;
    private StudentDAO dao ;
    private int editingId = -1;


    public StudentBean() throws NamingException {
        try {
            dao = new StudentDAO();
            loadStudents();   // load data when page opens first time
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getEditingId() {
        return editingId;
    }

    public void setEditingId(int editingId) {
        this.editingId = editingId;
    }

    public String addStudent() {
        String email = student.getEmail();

        if(!(email.contains("@"))) {
            FacesContext.getCurrentInstance().addMessage("addForm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "invalid email", "Please enter a valid email"));
            return null;

        }
        if(dao.studentExists(email)){
            FacesContext.getCurrentInstance().addMessage("addForm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "This student is already registered.",
                            "This student is already registered."));
            return null;
        }

        dao.addStudent(student);
        FacesContext.getCurrentInstance().addMessage("addForm",
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Student added successfully.",
                        "Student added successfully."));
        student = new Student();
        loadStudents();
        return null;
    }

    public String deleteStudent(int id) {
        dao.deleteStudent(id);
        loadStudents();
        return null;
    }


    public String editStudent(int id) {
        editingId = id;
        return null;
    }

    public String saveStudent() {
        dao.updateStudent(student);
        editingId = -1;
        student = new Student();
        loadStudents();
        return null;
    }

    private void loadStudents() {
        students = dao.getStudents();
    }

    public String cancelEdit() {
        editingId = -1;
        student = new Student();
        loadStudents();
        return null;
    }


    }

package org.example.studentslist;


import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import javax.naming.NamingException;

import java.util.List;
import java.util.ResourceBundle;



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
    private String getMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        return bundle.getString(key);
    }
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public String addStudent() {
        if (isEmpty(student.getLastName()) ||
                isEmpty(student.getFirstName()) ||
                isEmpty(student.getEmail()) ||
                student.getBirthday() == null) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            getMessage("emptyField"),
                            getMessage("emptyField")));
            return null;
        }
        String email = student.getEmail();

        if(!(email.contains("@"))) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            getMessage("invalidEmail"),
                            getMessage("invalidEmail")));
            return null;

        }
        if (dao.studentExists(student.getEmail())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            getMessage("studentExists"),
                            getMessage("studentExists")));
            return null;
        }

        dao.addStudent(student);
        student = new Student();
        loadStudents();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        getMessage("studentAdded"),
                        getMessage("studentAdded")));

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

package org.example.studentslist;

import java.time.LocalDate;


public class Student {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private LocalDate birthday;
    private int age;


    public Student() {}
    public Student(String lastName, String firstName, int age, String email,  LocalDate birthday, int id) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.birthday = birthday;
        this.age = age;
    }

    // Getters et Setters
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public LocalDate getBirthday() { return birthday; }

    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}

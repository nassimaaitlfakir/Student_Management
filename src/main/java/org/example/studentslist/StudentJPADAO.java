package org.example.studentslist;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class StudentJPADAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("student_management");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void addStudent(Student student) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(student);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void deleteStudent(long id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void updateStudent(Student student) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(student);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public List<Student> getStudents() {
        EntityManager em = getEntityManager();

        try {
            return em.createQuery(
                    "SELECT s FROM Student s ORDER BY s.lastName",
                    Student.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean studentExists(String email) {
        EntityManager em = getEntityManager();

        try {
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Student s WHERE s.email = :email",
                            Long.class
                    ).setParameter("email", email)
                    .getSingleResult();

            return count > 0;
        } finally {
            em.close();
        }
    }
}

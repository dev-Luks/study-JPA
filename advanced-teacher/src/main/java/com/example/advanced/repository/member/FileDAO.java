package com.example.advanced.repository.member;

import com.example.advanced.entity.member.File;
import com.example.advanced.entity.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class FileDAO {
    @PersistenceContext
    private EntityManager entityManager;

//    추가
    public File save(File file){
        entityManager.persist(file);
        return file;
    }

//    조회
    public Optional<File> findById(Long id){
        String query = "select f from File f join fetch f.member where f.id = :id";
        return Optional.ofNullable(
                entityManager
                        .createQuery(query, File.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }

//    전체 조회
    public List<File> findAll(){
        String query = "select f from File f join fetch f.member";
        return entityManager
                        .createQuery(query, File.class)
                        .getResultList();
    }

//    삭제
    public void delete(File file){
        entityManager.remove(file);
    }

}


















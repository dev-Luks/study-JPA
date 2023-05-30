package com.example.intermediate.repository.file;

import com.example.intermediate.entity.file.File;
import com.example.intermediate.entity.file.MemberFile;
import com.example.intermediate.entity.file.ReviewFile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewFileDAO {
    @PersistenceContext
    private EntityManager entityManager;

//    추가
    public ReviewFile save(ReviewFile reviewFile){
        entityManager.persist(reviewFile);
        return reviewFile;
    }

//    조회
    public Optional<ReviewFile> findById(Long id){
//        상속관계일 경우 자식 엔티티를 조회하면 자동으로 부모 테이블과 JOIN된다.
        return Optional.ofNullable(entityManager.find(ReviewFile.class, id));
    }

//    전체 조회
    public List<File> findAll(){
//        상속관계일 경우 부모 엔티티를 조회하면 자동으로 모든 자식 테이블과 JOIN된다.
        String query = "select f from File f";
        return entityManager.createQuery(query, File.class).getResultList();
    }

//    삭제
    public void delete(ReviewFile reviewFile){
        entityManager.remove(reviewFile);
    }

}


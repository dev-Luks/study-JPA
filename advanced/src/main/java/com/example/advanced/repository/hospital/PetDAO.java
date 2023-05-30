package com.example.advanced.repository.hospital;

import com.example.advanced.entity.hospital.Pet2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class PetDAO {
    @PersistenceContext
    private EntityManager entityManager;

    //    추가
    public Pet2 save(Pet2 pet2){
        entityManager.persist(pet2);
        return pet2;
    }

    //    조회
    public Optional<Pet2> findById(Long id){
//        return Optional.ofNullable(entityManager.find(Pet2.class, id));
        String query = "select p from Pet2 p join fetch p.owner2 where p.id = :id";
        return Optional.ofNullable(
                entityManager
                        .createQuery(query, Pet2.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }

    //    전체 조회
    public List<Pet2> findAll(){
        String query = "select p from Pet2 p join fetch p.owner2";
        return entityManager.createQuery(query, Pet2.class).getResultList();
    }

    //    삭제
    public void delete(Pet2 pet2){
        entityManager.remove(pet2);
    }


}

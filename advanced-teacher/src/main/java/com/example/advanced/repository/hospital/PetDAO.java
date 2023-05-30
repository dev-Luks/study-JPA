package com.example.advanced.repository.hospital;

import com.example.advanced.entity.hospital.Owner;
import com.example.advanced.entity.hospital.Pet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class PetDAO {
    @PersistenceContext
    private EntityManager entityManager;

//    추가
    public Pet save(Pet pet){
        entityManager.persist(pet);
        return pet;
    }

//    엔티티 조회
    public Optional<Pet> findById(Long id){
//        return Optional.ofNullable(entityManager.find(Pet.class, id));
        String query = "select p from Pet p join fetch p.owner where p.id = :id";
        return Optional.ofNullable(
                entityManager
                        .createQuery(query, Pet.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }

//    참조 엔티티 조회
    public Optional<Owner> findOwnerById(Long id){
        return Optional.ofNullable(entityManager.find(Owner.class, id));
    }

//    전체 조회
    public List<Pet> findAll(){
//        String query = "select p from Pet p join fetch p.owner";
        String query = "select p from Pet p inner join p.owner";
        return entityManager.createQuery(query, Pet.class).getResultList();
    }

//    삭제
    public void delete(Pet pet){
        entityManager.remove(pet);
    }

}









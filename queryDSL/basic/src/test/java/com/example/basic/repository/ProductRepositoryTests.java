package com.example.basic.repository;

import com.example.basic.entity.Product;
import com.example.basic.entity.QProduct;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import java.util.List;

import static com.example.basic.entity.QProduct.product;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void saveTest(){
        Product product = new Product("노트북", 2_500_000, 50);
        productRepository.save(product);
    }

    @Test
    public void selectTest(){
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
//        static import를 통해 선언 없이 바로 product를 사용할 수 있다.
//        QProduct product = QProduct.product;

//        select()에 파라미터를 비워놓을 수는 없다.
//        List<Tuple> results = jpaQueryFactory.select(product.id, product.productName)
//                .from(product)
//                .fetch();// 여러 개의 정보를 가져올 때 사용
////                .fetchOne() // 한 개의 정보를 가져올 때 사용
//        log.info(results.toString());
//        log.info(String.valueOf(results.get(0).get(0, Long.class)));

        Product product = jpaQueryFactory
                .select(QProduct.product)
                .from(QProduct.product)
                .where(QProduct.product.id.eq(2691L))
                .fetchOne();

        log.info(product.toString());
    }

    @Test
    public void updateTest(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

//        1차 캐시 등록
        Product foundProduct = query.select(product)
                .from(product)
                .where(product.id.eq(2691L))
                .fetchOne();

//        더티 체크(변경 감지) 수행
//        foundProduct.setProductName("컴퓨터");
        
//        벌크 연산 수행
        query.update(product)
                .set(product.productName, "노트북")
                .where(product.id.eq(2691L))
                .execute();

        entityManager.refresh(foundProduct);
//        entityManager.clear();

//        JPQL을 사용하는 QueryDSL은 무조건 쿼리가 발생한다.
//        가져온 결과를 1차 캐시에 INSERT 한다.
//        만약 동일한 객체가 1차 캐시에 존재하면, 쿼리 실행 결과를 버린다.
        log.info(query.select(product)
                .from(product)
                .where(product.id.eq(2691L))
                .fetchOne().toString());

        transaction.commit();
    }

    @Test
    public void deleteTest(){
        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        query.delete(product)
                .where(product.id.eq(2691L))
                .execute();
    }
}
















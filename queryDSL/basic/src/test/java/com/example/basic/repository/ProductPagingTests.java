package com.example.basic.repository;

import com.example.basic.entity.Product;
import com.example.basic.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.example.basic.entity.QProduct.product;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class ProductPagingTests {
    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void saveTest(){
        Product product = null;
        for (int i=0; i<100; i++){
            product = new Product("상품" + (i + 1), 10000 + i, 10 + i);
            productRepository.save(product);
        }
    }

    @Test
    public void selectWithPagingTest(){
        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        List<Product> foundProducts = query.select(product)
                .from(product)
                .orderBy(product.id.desc())
                .offset(0)
                .limit(10)
                .fetch();

        foundProducts.forEach(foundProduct -> log.info(foundProduct.toString()));

        Long count = query.select(product.count())
                .from(product)
                .fetchOne();
        log.info(count + "건 조회");
    }
}

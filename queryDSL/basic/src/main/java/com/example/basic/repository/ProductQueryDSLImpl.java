package com.example.basic.repository;

import com.example.basic.entity.Product;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.example.basic.entity.QProduct.product;

public class ProductQueryDSLImpl implements ProductQueryDSL {
    @Autowired
    private JPAQueryFactory query;

    @Override
    public Page<Product> findAllWithPaging(Pageable pageable) {
        List<Product> foundProduct = query.select(product)
                .from(product)
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(product.count())
                .from(product)
                .fetchOne();

        return new PageImpl<>(foundProduct, pageable, count);
    }

    @Override
    public void updatePrices() {
        query.update(product)
//                실수를 연산해야 할 때에는 아래와 같이 doubleValue() 및 intValue()를 사용해야 한다.
                .set(product.productPrice, product.productPrice.doubleValue().multiply(1.1).intValue())
                .where(product.productPrice.lt(
//                        서브쿼리(from절 불가능)
                        JPAExpressions.select(product.productPrice.avg()).from(product))
                )
                .execute();
    }
}

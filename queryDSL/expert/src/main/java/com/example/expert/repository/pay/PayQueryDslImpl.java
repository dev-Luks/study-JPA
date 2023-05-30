package com.example.expert.repository.pay;

import com.example.expert.entity.product.Product;
import com.example.expert.entity.product.ProductDTO;
import com.example.expert.entity.product.QProductDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.expert.entity.member.QMember.member;
import static com.example.expert.entity.order.QOrder.order;
import static com.example.expert.entity.pay.QPay.pay;
import static com.example.expert.entity.product.QProduct.product;

import java.util.List;
import java.util.Optional;

public class PayQueryDslImpl implements PayQueryDsl {
    @Autowired
    private JPAQueryFactory query;

//    패치 조인을 사용해야 할 때
//     해당 객체를 select절에 작성한 뒤 JAVA쪽에서 엔티티 그래프 탐색을 진행할 때
//     select(order).from(order).join(order.member).fetchJoin()
    
//    패치 조인을 사용하면 안될 때
//     select절에 원하는 연관 엔티티를 작성하거나 DTO를 작성해야 할 때
//     select(order.member).from(order)

//    ※ 패치 조인 시 추가 적인 조건절은 on절이 아닌 where절에 작성해야 한다.

    @Override
    public List<ProductDTO> findCountOfOrder() {
        return query.select(
                new QProductDTO(
                        pay.product.id,
                        pay.product.productName,
                        pay.product.productStock,
                        pay.product.productPrice,
                        pay.order.count().intValue()))
                .from(pay)
                .groupBy(pay.product.id,
                        pay.product.productName,
                        pay.product.productStock,
                        pay.product.productPrice).fetch();
    }

    @Override
    public Optional<ProductDTO> findTop1ProductByOrder() {
        return Optional.ofNullable(query.select(new QProductDTO(
                pay.product.id,
                pay.product.productName,
                pay.product.productStock,
                pay.product.productPrice,
                pay.order.count().intValue()))
                .from(pay)
                .groupBy(pay.product.id,
                        pay.product.productName,
                        pay.product.productStock,
                        pay.product.productPrice)
                .orderBy(pay.order.count().desc())
                .offset(0)
                .limit(1)
                .fetchOne());
    }

    @Override
    public List<Product> findProductByOrderId(Long id) {
        return query.select(pay.product).from(pay).where(pay.order.id.eq(id)).fetch();
    }
}












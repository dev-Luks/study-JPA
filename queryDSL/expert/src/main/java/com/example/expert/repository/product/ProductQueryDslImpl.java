package com.example.expert.repository.product;

import com.example.expert.entity.pay.Pay;
import com.example.expert.entity.pay.QPay;
import com.example.expert.entity.product.Product;
import com.example.expert.entity.product.ProductSearch;
import com.example.expert.entity.product.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.expert.entity.pay.QPay.pay;
import static com.example.expert.entity.product.QProduct.product;

@RequiredArgsConstructor
public class ProductQueryDslImpl implements ProductQueryDsl {
    private final JPAQueryFactory query;

    @Override
    public List<Pay> findPayByProduct(Long id) {
        return query.select(pay)
                .from(pay)
                .join(pay.product)
                .fetchJoin()
                .where(pay.product.id.eq(id))
                .fetch();
    }

    @Override
    public Page<Product> findAllWithSearch(ProductSearch productSearch, Pageable pageable) {
        BooleanExpression productNameEq = productSearch.getProductName() == null ? null : product.productName.eq(productSearch.getProductName());
        BooleanExpression productPriceEq = productSearch.getProductPrice() == null ? null : product.productPrice.eq(productSearch.getProductPrice());
        BooleanExpression productStockEq = productSearch.getProductStock() == null ? null : product.productStock.eq(productSearch.getProductStock());

        List<Product> products = query.select(product)
                .from(product)
                .where(productNameEq, productPriceEq, productStockEq)
                .orderBy(product.id.desc())
                .offset(pageable.getOffset() - 1)
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(product.count()).from(product).fetchOne();

        return new PageImpl<>(products, pageable, count);
    }
}






















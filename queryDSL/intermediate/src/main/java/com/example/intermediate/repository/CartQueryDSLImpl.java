package com.example.intermediate.repository;

import com.example.intermediate.entity.Cart;
import com.example.intermediate.entity.QCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.intermediate.entity.QCart.cart;

@RequiredArgsConstructor
public class CartQueryDSLImpl implements CartQueryDSL {
    private final JPAQueryFactory query;

    @Override
    public List<Cart> findAll_QueryDSL() {
        return query.select(cart).from(cart).join(cart.product).fetchJoin().fetch();
    }
}

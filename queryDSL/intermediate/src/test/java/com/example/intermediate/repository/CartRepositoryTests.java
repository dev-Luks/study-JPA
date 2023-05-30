package com.example.intermediate.repository;

import com.example.intermediate.entity.Cart;
import com.example.intermediate.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class CartRepositoryTests {
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;

    @Test
    public void saveTest(){
//        장바구니에 담은 만큼 재고 감소
        Product product = new Product("바밤바", 1000, 50);
        Cart cart = new Cart(10L);

        product.setProductStock(product.getProductStock() - cart.getCount().intValue());
        productRepository.save(product);

        cart.setProduct(product);
        cartRepository.save(cart);
    }

    @Test
    public void findAllTest(){
        cartRepository.findAll().forEach(cart -> log.info(cart.getProduct().toString()));
    }

    @Test
    public void deleteTest(){
//        장바구니에서 삭제된 항목의 상품 재고 복구
//        Optional<Product> foundProduct = productRepository.findById(205L);
//        Optional<Cart> foundCart = cartRepository.findById(206L);

//        foundCart.ifPresent(cart ->
//                foundProduct.ifPresent(product ->
//                        product.setProductStock(product.getProductStock() + cart.getCount().intValue())));
//        foundCart.ifPresent(cartRepository::delete);

        cartRepository.findAll_QueryDSL().forEach(cart -> {
            cart.getProduct().setProductStock(cart.getProduct().getProductStock() + cart.getCount().intValue());
            cartRepository.delete(cart);
        });
    }
}









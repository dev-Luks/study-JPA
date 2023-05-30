package com.example.expert.repository;

import com.example.expert.entity.embeddable.Address;
import com.example.expert.entity.member.Member;
import com.example.expert.entity.order.Order;
import com.example.expert.entity.pay.Pay;
import com.example.expert.entity.product.Product;
import com.example.expert.entity.product.ProductDTO;
import com.example.expert.entity.product.ProductSearch;
import com.example.expert.entity.type.PayStatus;
import com.example.expert.repository.member.MemberRepository;
import com.example.expert.repository.order.OrderRepository;
import com.example.expert.repository.pay.PayRepository;
import com.example.expert.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class RepositoryTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PayRepository payRepository;

    @Test
    public void saveTest(){
        Address address = new Address("12345", "서울시 강남구", "역삼동");
//        Member member = new Member("hds1234", "1234", "한동석", 20, address);
//        memberRepository.save(member);

//        for (int i=0; i<5; i++){
//            Product product = new Product("상품" + (i + 1), Long.valueOf(700 + i), Long.valueOf(1000 * (i+1)));
//            productRepository.save(product);
//        }

//        Order order = new Order(address);
//        memberRepository.findById(6L).ifPresent(order::setMember);
//        orderRepository.save(order);

        productRepository.findById(1L).map(Product::getProductPrice).ifPresent(price -> {
            Pay pay = new Pay(Long.valueOf(price), PayStatus.DONE);
            orderRepository.findById(23L).ifPresent(pay::setOrder);
            productRepository.findById(1L).ifPresent(pay::setProduct);
            payRepository.save(pay);
        });


//        productRepository.findAll().forEach(product -> {
//            Pay pay = new Pay(Long.valueOf(totalPrice), PayStatus.DONE);
//            orderRepository.findById(12L).ifPresent(pay::setOrder);
//            pay.setProduct(product);
//            payRepository.save(pay);
//        });
    }

    @Test
    public void findCountOfOrderTest(){
        payRepository.findCountOfOrder().stream().map(ProductDTO::toString).forEach(log::info);
    }

    @Test
    public void findTop1ProductByOrderTest(){
        payRepository.findTop1ProductByOrder().map(ProductDTO::toString).ifPresent(log::info);
    }

    @Test
    public void findProductByOrderIdTest(){
        log.info(payRepository.findProductByOrderId(12L).toString());
    }

    @Test
    public void findMemberByOrderIdTest(){
//        orderRepository.findMemberByOrderId(23L).map(Order::getMember).map(Member::toString).ifPresent(log::info);
        memberRepository.findMemberByOrderId(23L).map(Member::toString).ifPresent(log::info);
    }

    @Test
    public void findPayByProductTest(){
        List<Pay> pays = productRepository.findPayByProduct(1L);
        pays.forEach(pay -> log.info(pay.toString()));
        log.info(pays.get(0).getProduct().toString());
    }

    @Test
    public void findAllWithSearchTest(){
        ProductSearch productSearch = new ProductSearch();
//        productSearch.setProductPrice(4000L);
//        productSearch.setProductStock(702L);
        productRepository.findAllWithSearch(productSearch, PageRequest.of(3, 2));
    }
}

















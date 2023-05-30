package com.example.basic.controller;

import com.example.basic.entity.Product;
import com.example.basic.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/*")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("{page}/{size}")
    public List<Product> getList(@PathVariable int page, @PathVariable int size){
        return productService.getList(PageRequest.of(page - 1, size));
    }

    @PatchMapping("update")
    public String update(){
        productService.increasePrices();
        return "success";
    }

}

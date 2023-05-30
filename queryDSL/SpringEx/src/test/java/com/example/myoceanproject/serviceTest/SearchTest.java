//package com.example.myoceanproject.serviceTest;
//
//
//import com.example.myoceanproject.repository.GroupRepository;
//import com.example.myoceanproject.repository.UserRepository;
//import com.example.myoceanproject.service.search.SearchService;
//import com.example.myoceanproject.type.CommunityCategory;
//import com.example.myoceanproject.type.GroupStatus;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//@Rollback(false)
//public class SearchTest {
//    @Autowired
//    SearchService searchService;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    GroupRepository groupRepository;
//
//    @Test
//    public void test1(){
//
//        Pageable pageable = PageRequest.of(0,10);
//        String search = "ㄴ";
//
//    }
//
//    @Test
//    public void test2(){
//
//        Pageable pageable = PageRequest.of(0,10);
//        String search = "ㄴ";
//
//        ArrayList<CommunityCategory> categories = new ArrayList<>();
//        categories.add(CommunityCategory.EXERCISE);
//
//    }
//
//    @Test
//    public void test3(){
//
//        Pageable pageable = PageRequest.of(0,10);
//        String search = "ㄴ";
//
//        ArrayList<CommunityCategory> categories = new ArrayList<>();
//        categories.add(CommunityCategory.EXERCISE);
//        categories.add(CommunityCategory.BOOK);
//
//    }
//
//    @Test
//    public void test4(){
//
//        Pageable pageable = PageRequest.of(0,10);
//        String search = "ㄴ";
//
//        ArrayList<CommunityCategory> categories = new ArrayList<>();
//        categories.add(CommunityCategory.EXERCISE);
//        categories.add(CommunityCategory.BOOK);
//        categories.add(CommunityCategory.MOVIE);
//
//    }
//
//    @Test
//    public void test5(){
//        log.info(userRepository.findById(1l).get().toString());
//    }
//
//    @Test
//    public void test7(){
//        groupRepository.findAll().stream().forEach(i->i.update(GroupStatus.APPROVED));
//    }
//
//
//}

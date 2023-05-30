package com.example.basic.repository;

import com.example.basic.domain.entity.SuperCar;
import com.example.basic.exception.NoSuchSuperCarException;
import com.example.basic.type.SuperCarType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class SuperCarDaoTests {

    @Autowired
    private SuperCarDAO superCarDAO;

//    @Test
//    public void saveTest(){
//        SuperCarType[] superCarTypes = {SuperCarType.BENTLEY, SuperCarType.FERRARI, SuperCarType.HYUNDAI};
//        String[] colors = {"볼케이노 레드", "아쿠아 블루", "에메랄드 블루"};
//        Random random = new Random();
//
//        for (int i=0; i<100; i++){
//            SuperCar superCar = new SuperCar();
//            superCar.setName("super car" + (i + 1));
//            superCar.setType(superCarTypes[random.nextInt(superCarTypes.length)]);
//            superCar.setColor(colors[random.nextInt(colors.length)]);
//            superCar.setPrice((double)(100 * (i + 1)));
//            superCar.setReleaseDate(LocalDateTime.of(2022, 12, random.nextInt(31) + 1, 06, 00));
//            superCarDAO.save(superCar);
//        }
//    }

    @Test
    public void findByIdTest(){
        Optional<SuperCar> foundSuperCar = superCarDAO.findById(2L);
        foundSuperCar.map(SuperCar::getName).ifPresentOrElse(log::info, () -> {log.info("찾으시는 차량이 없습니다.");});
    }

    @Test
    public void deleteTest(){
        Optional<SuperCar> foundSuperCar = superCarDAO.findById(2L);
        superCarDAO.delete(foundSuperCar.orElseThrow(() -> {throw new NoSuchSuperCarException("찾으시는 차량이 없습니다.");}));
    }

    @Test
    public void updateTest(){
        Optional<SuperCar> foundSuperCar = superCarDAO.findById(3L);
        foundSuperCar.ifPresent(superCar -> superCar.setColor("아쿠아마린 블루"));
        foundSuperCar.map(SuperCar::getColor).ifPresent(log::info);
    }

    @Test
    public void findAllTest(){
        superCarDAO.findAll().stream().map(SuperCar::getName).forEach(log::info);
    }

    @Test
    public void findAllWithPagingTest(){
        superCarDAO.findAllWithPaging(41, 10).stream().map(SuperCar::toString).forEach(log::info);
    }

    @Test
    public void findAllByReleaseDate(){
        superCarDAO.findAllByReleaseDate("20221217").stream().map(SuperCar::toString).forEach(log::info);
    }

    @Test
    public void findAllBetweenReleaseDate(){
        LocalDateTime startDate = LocalDateTime.of(2022, 12, 10, 00, 00);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 20, 23, 59);
        log.info(superCarDAO.findAllBetweenReleaseDate(startDate, endDate).size() + "건");
    }

    @Test
    public void findAllByNameAndPriceTest(){
        assertThat(superCarDAO.findAllByNameAndPrice("super car87", 8700L).size()).isEqualTo(1);
    }

    @Test
    public void deleteByPriceGreaterThan() {
        superCarDAO.deleteByPriceGreaterThan(7000);
    }

    @Test
    public void updateByReleaseDateTest(){
        superCarDAO.updateByReleaseDate("20221204", 10);
    }
}


















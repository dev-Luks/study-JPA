package com.example.myoceanproject.serviceTest;


import com.example.myoceanproject.domain.ChattingDTO;
import com.example.myoceanproject.service.chattingService.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
@Slf4j
public class ChattingServiceTest {

    @Autowired
    private ChattingService chattingService;

//@Test
//    public void findTest(){
//    List<ChattingDTO> chattingDTOgList =  chattingService.showChatting(24L);
//    log.info("========================================================================");
//    chattingDTOgList.stream().map(ChattingDTO::toString).forEach(log::info);
//    log.info("========================================================================");
//
//}

@Test
    public void updateTest(){
    chattingService.showChatting(2L, 43L).stream().map(ChattingDTO::toString).forEach(log::info);
}
}

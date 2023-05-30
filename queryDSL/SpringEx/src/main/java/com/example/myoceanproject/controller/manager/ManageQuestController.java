package com.example.myoceanproject.controller.manager;
import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.service.alarm.AlarmService;
import com.example.myoceanproject.service.quest.QuestService;
import com.example.myoceanproject.type.UserAccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@Transactional
@RequestMapping("/quest/*")
public class ManageQuestController {


    private final QuestService questService;
    private final AlarmService alarmService;

    //    브라우저에서 JSON 타입으로 데이터를 전송하고 서버에서는 댓글의 처리 결과에 따라 문자열로 결과를 리턴한다.
//    consumes : 전달받은 데이터의 타입
//    produces : 콜백함수로 결과를 전달할 때의 타입
//    @RequestBody : 전달받은 데이터를 알맞는 매개변수로 주입
//    ResponseEntity : 서버의 상태코드, 응답 메세지 등을 담을 수 있는 타입

    @PostMapping(value = "/upload", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public String addQuest(@RequestBody QuestDTO questDTO) {
        questService.addQuest(questDTO);
        AlarmDTO alarmDTO = new AlarmDTO();
        if (questDTO.getQuestCategory().equals("오늘의 퀘스트")) {
            alarmDTO.setAlarmCategory("TODAY");
            alarmDTO.setAlarmContent("!오늘의 퀘스트 도착!");
            alarmService.addAlarm(alarmDTO);
        }
        return "dd";
    }
    @PatchMapping (value = "/update", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public String updateQuest(@RequestBody QuestDTO questDTO){
        log.info("-----------------------------------------------------");
        log.info("업데이트");
        log.info("-----------------------------------------------------");
        questService.modify(questDTO);

        return "update success";
    }

    @PostMapping("/uploadFile")
    public QuestDTO upload(MultipartFile upload) throws IOException {
        String rootPath = "C:/upload/quest";
        String uploadFileName = null;

        File uploadPath = new File(rootPath, createDirectoryByNow());
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

            QuestDTO questDTO = new QuestDTO();
            UUID uuid = UUID.randomUUID();
            String fileName = upload.getOriginalFilename();
            String fileUuid = uuid.toString();
            String groupFilePath = createDirectoryByNow();
            Long groupFileSize = upload.getSize();
            uploadFileName = uuid.toString() + "_" + fileName;

            questDTO.setQuestFileName(fileName);
            questDTO.setQuestFileUuid(fileUuid);
            questDTO.setQuestFileSize(upload.getSize());
            questDTO.setQuestFilePath(groupFilePath);

            try{
                File saveQuestFile = new File(uploadPath, uploadFileName);
                upload.transferTo(saveQuestFile);
            } catch(Exception e){
                log.error(e.getMessage());
            }
        return questDTO;
    }

    @GetMapping("/{page}/{keyword}")
    public QuestDTO getQuest(@PathVariable int page, @PathVariable(required = false) String keyword){
        String decodeKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);

        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setKeyword(decodeKeyword);
        //        0부터 시작,
        Pageable pageable = PageRequest.of(criteria.getPage() == 0 ? 0 : criteria.getPage()-1, 10);

        Page<QuestDTO> questDTOPage= questService.showPost(pageable, criteria);

        log.info(questDTOPage.getTotalPages() + "end");

        QuestDTO questDTO = new QuestDTO();

        questDTO.setQuestList(questDTOPage.getContent());
        questDTO.setEndPage(questDTOPage.getTotalPages());

        questDTOPage.getContent().stream().map(QuestDTO::toString).forEach(log::info);

        return questDTO;
    }

    @GetMapping("/display")
    public byte[] display(String fileName) throws IOException{
        File file = new File("C:/upload/quest", fileName);
        return FileCopyUtils.copyToByteArray(file);
    }


    public String createDirectoryByNow(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        return format.format(now);
    }
}

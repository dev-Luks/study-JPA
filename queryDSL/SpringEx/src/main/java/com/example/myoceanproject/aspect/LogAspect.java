package com.example.myoceanproject.aspect;

import com.example.myoceanproject.controller.mySpace.MySpaceService;
import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.*;
import com.example.myoceanproject.repository.DiaryRepositoryImpl;
import com.example.myoceanproject.repository.GroupRepository;
import com.example.myoceanproject.repository.PointRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.repository.ask.AskRepository;
import com.example.myoceanproject.repository.community.like.CommunityLikeRepositoryImpl;
import com.example.myoceanproject.repository.community.post.CommunityPostRepository;
import com.example.myoceanproject.repository.community.post.CommunityPostRepositoryImpl;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepositoryImpl;
import com.example.myoceanproject.repository.quest.QuestAchievementRepository;
import com.example.myoceanproject.repository.quest.QuestAchievementRepositoryImpl;
import com.example.myoceanproject.repository.quest.QuestRepository;
import com.example.myoceanproject.service.PointService;
import com.example.myoceanproject.service.alarm.AlarmService;
import com.example.myoceanproject.service.quest.QuestAchievementService;
import com.example.myoceanproject.service.quest.QuestService;
import com.example.myoceanproject.type.DiaryCategory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.mapping.Join;
import org.mockito.internal.matchers.Null;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final AlarmService alarmService;

    private final CommunityPostRepository postRepository;

    private final UserRepository userRepository;

    private final QuestService questService;

    private final AskRepository askRepository;

    private final PointRepository pointRepository;

    private final QuestAchievementRepository questAchievementRepository;

    private final CommunityLikeRepositoryImpl likeRepositoryImpl;

    private final CommunityReplyRepositoryImpl replyRepositoryImpl;
    private final QuestAchievementRepositoryImpl achievementRepositoryImpl;

    private final CommunityPostRepositoryImpl postRepositoryImpl;

    private final QuestAchievementService achievementService;
    private final QuestRepository questRepository;

    private final PointService pointService;
    private final GroupRepository groupRepository;

    private final MySpaceService mySpaceService;

    private final DiaryRepositoryImpl diaryRepositoryImpl;


    //    댓글 알림
    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.ReplyAlarm)")
    public void afterAddReply(JoinPoint joinPoint) {
        CommunityReplyDTO replyDTO = Arrays.stream(joinPoint.getArgs())
                .filter(CommunityReplyDTO.class::isInstance)
                .map(CommunityReplyDTO.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));
        CommunityPost post = postRepository.findById(replyDTO.getCommunityPostId()).get();

//       댓글의 작성자가 해당 포스트의 작성자가 아닐 경우, 포스트 작성자에게 알림을 보냄
        if (replyDTO.getUserId() != replyDTO.getWriterId()) {
            AlarmDTO alarmDTO = new AlarmDTO();
            alarmDTO.setAlarmCategory("COMMUNITY");
            alarmDTO.setAlarmContent("\"" + replyDTO.getCommunityPostTitle() + "\" 에 댓글이 달렸습니다 지금 확인해 보세요!");
            alarmDTO.setUserId(replyDTO.getWriterId());
            alarmDTO.setContentId(replyDTO.getCommunityPostId());

            log.info(alarmDTO.toString());
            alarmService.addAlarm(alarmDTO);
        }

//     해당 포스트의 댓글이 5개이상 달렸을 때 [포스트 작성자]에게 보상을 지급
        if (achievementRepositoryImpl.checkDuplicatedById(replyDTO.getWriterId(), 10003L) && replyRepositoryImpl.countReplyByCommunityPost(post.getCommunityPostId()) >= 5) {
            Quest quest = questRepository.findById(10003L).get();
            achievementService.save(replyDTO.getWriterId(), quest);

            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointAmountHistory(quest.getQuestPoint());
            pointDTO.setUserId(replyDTO.getWriterId());

            pointService.questReward(pointDTO, quest);

            AlarmDTO questAlarm = new AlarmDTO();

            questAlarm.setUserId(replyDTO.getWriterId());
            alarmService.questAlarm(questAlarm, quest);
        }

//      댓글 50개, 게시글 10개 작성 시 받는 뱃지
        if (postRepositoryImpl.countPostByUser(replyDTO.getUserId()) >= 10 && replyRepositoryImpl.countReplyByUser(replyDTO.getUserId()) >= 50 && achievementRepositoryImpl.checkDuplicatedById(replyDTO.getUserId(), 10002L)) {
            Quest quest = questRepository.findById(10002L).get();
            achievementService.save(replyDTO.getUserId(), quest);

            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointAmountHistory(quest.getQuestPoint());
            pointDTO.setUserId(replyDTO.getUserId());

            pointService.questReward(pointDTO, quest);

            AlarmDTO questAlarm = new AlarmDTO();

            questAlarm.setUserId(replyDTO.getUserId());
            alarmService.questAlarm(questAlarm, quest);
        }
//        고민상담 게시판에 댓글 3개 이상
        if (replyRepositoryImpl.countCounselingReplyById(replyDTO.getUserId()) >= 3 && achievementRepositoryImpl.checkDuplicatedById(replyDTO.getUserId(), 10013L)) {
            Quest quest = questRepository.findById(10013L).get();
            achievementService.save(replyDTO.getUserId(), quest);

            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointAmountHistory(quest.getQuestPoint());
            pointDTO.setUserId(replyDTO.getUserId());

            pointService.questReward(pointDTO, quest);

            AlarmDTO questAlarm = new AlarmDTO();

            questAlarm.setUserId(replyDTO.getUserId());
            alarmService.questAlarm(questAlarm, quest);
        }

    }

    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.LikeAlarm)")
    public void afterAddLike(JoinPoint joinPoint) {
        long userId = Long.parseLong(joinPoint.getArgs()[0].toString());
        long postId = Long.parseLong(joinPoint.getArgs()[1].toString());
        long postUserId = postRepository.findById(postId).get().getUser().getUserId();

//       좋아요를 누른 회원이 해당 포스트의 작성자가 아닐 경우, 포스트 작성자에게 알림을 보냄
        if (userId != postUserId) {
            AlarmDTO alarmDTO = new AlarmDTO();
            alarmDTO.setAlarmCategory("COMMUNITY");
            alarmDTO.setAlarmContent("\"" + postRepository.findById(postId).get().getCommunityTitle() + "\" 이 좋아요를 받았습니다");
            alarmDTO.setUserId(postUserId);
            alarmDTO.setContentId(postId);
            alarmService.addAlarm(alarmDTO);

//      해당 포스트에 좋아요가 5개 이상일 경우
            if (likeRepositoryImpl.findByCommunityPost(postRepository.findById(postId).get()).size() >= 5 && achievementRepositoryImpl.checkDuplicatedById(userId, 10004L)) {
                Quest quest = questRepository.findById(10004L).get();
                achievementService.save(postUserId, quest);

                PointDTO pointDTO = new PointDTO();
                pointDTO.setPointAmountHistory(quest.getQuestPoint());
                pointDTO.setUserId(postUserId);

                pointService.questReward(pointDTO, quest);

                AlarmDTO questAlarm = new AlarmDTO();

                questAlarm.setUserId(postUserId);
                alarmService.questAlarm(questAlarm, quest);
            }

//          해당 회원이 좋아요를 5개 이상
            if (likeRepositoryImpl.countLikeByUserId(userId) >= 5 && achievementRepositoryImpl.checkDuplicatedById(userId, 10005L)) {
                Quest quest = questRepository.findById(10005L).get();
                achievementService.save(userId, quest);

                PointDTO pointDTO = new PointDTO();
                pointDTO.setPointAmountHistory(quest.getQuestPoint());
                pointDTO.setUserId(userId);

                pointService.questReward(pointDTO, quest);

                AlarmDTO questAlarm = new AlarmDTO();

                questAlarm.setUserId(userId);
                alarmService.questAlarm(questAlarm, quest);
            }
        }
    }

    //    그룹 참여 알람
    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.GroupJoinAlarm)")
    public void joinGroup(JoinPoint joinPoint) {
        Long groupId = Long.valueOf(joinPoint.getArgs()[0].toString());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[1];
        Long userId = (Long) request.getSession().getAttribute("userId");
        User user = userRepository.findById(userId).get();

        Group group = groupRepository.findById(groupId).get();

        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setAlarmCategory("GROUP");
        alarmDTO.setAlarmContent("\"" + group.getGroupName() + "\" 에 참여하였습니다! 채팅방을 확인볼까요🙋‍♀️");
        alarmDTO.setUserId(userId);
        alarmDTO.setContentId(groupId);
        alarmService.addAlarm(alarmDTO);

        AlarmDTO managerAlarm = new AlarmDTO();
        managerAlarm.setAlarmCategory("GROUP");
        managerAlarm.setAlarmContent("\"" + user.getUserNickname() + "\" 님이 \"" + group.getGroupName() + "\" 에 참여하였습니다! 채팅방을 확인볼까요🙋‍♀️");
        managerAlarm.setUserId(group.getUser().getUserId());
        managerAlarm.setContentId(groupId);
        alarmService.addAlarm(managerAlarm);

        if (achievementRepositoryImpl.checkDuplicatedById(userId, 10006L)) {
//            처음 참가할 경우(뱃지의 유무로 검사), 보상 지급
            Quest quest = questRepository.findById(10006L).get();
            achievementService.save(userId, quest);

            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointAmountHistory(quest.getQuestPoint());
            pointDTO.setUserId(userId);

            pointService.questReward(pointDTO, quest);

            AlarmDTO questAlarm = new AlarmDTO();

            questAlarm.setUserId(userId);
            alarmService.questAlarm(questAlarm, quest);
        }


    }

    //관리자 답변 알림
    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.AskAlarm)")
    public void answer(JoinPoint joinPoint) {
        ObjectNode objectNode = Arrays.stream(joinPoint.getArgs())
                .filter(ObjectNode.class::isInstance)
                .map(ObjectNode.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));

        Long askId = objectNode.get("askId").asLong();

        Ask ask = askRepository.findById(askId).get();
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setAlarmCategory("ASK");
        alarmDTO.setAlarmContent("\"" + ask.getAskTitle() + "\" 에 답변이 달렸습니다.");
        alarmDTO.setUserId(ask.getUser().getUserId());
        alarmService.addAlarm(alarmDTO);
    }

    //포인트 충전 알람
    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.PointAlarm)")
    public void point(JoinPoint joinPoint) {
        ObjectNode objectNode = Arrays.stream(joinPoint.getArgs())
                .filter(ObjectNode.class::isInstance)
                .map(ObjectNode.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));

        AlarmDTO alarmDTO = new AlarmDTO();

        try {
            if (!objectNode.get("userId").isNull()) {
                log.info("포인트 충전");
                long userPoint = objectNode.get("point").asLong();
                Long userId = objectNode.get("userId").asLong();

                alarmDTO.setAlarmCategory("POINT");
                alarmDTO.setAlarmContent(userPoint + "POINT 충전이 완료되었습니다!");
                alarmDTO.setUserId(userId);

//100000포인트 결제 고객에게 리워드 지급(받은적이 없다면)
                if (userPoint >= 100000L && achievementRepositoryImpl.checkDuplicatedById(userId, 10009L)) {
                    Quest quest = questRepository.findById(10009L).get();
                    achievementService.save(userId, quest);

                    PointDTO pointDTO = new PointDTO();
                    pointDTO.setPointAmountHistory(quest.getQuestPoint());
                    pointDTO.setUserId(userId);

                    pointService.questReward(pointDTO, quest);

                    AlarmDTO questAlarm = new AlarmDTO();

                    questAlarm.setUserId(userId);
                    alarmService.questAlarm(questAlarm, quest);

                } else if (achievementRepositoryImpl.checkDuplicatedById(userId, 10008L)) {
//                첫 결제 && 이전 결제 보상이 없던 유저도 포함 보상
                    Quest quest = questRepository.findById(10008L).get();
                    achievementService.save(userId, quest);

                    PointDTO pointDTO = new PointDTO();
                    pointDTO.setPointAmountHistory(quest.getQuestPoint());
                    pointDTO.setUserId(userId);

                    pointService.questReward(pointDTO, quest);
                    AlarmDTO questAlarm = new AlarmDTO();

                    questAlarm.setUserId(userId);
                    alarmService.questAlarm(questAlarm, quest);
                }

            }
        } catch (NullPointerException e) {
            log.info("관리자 환불페이지");
            //        환불
            Long requestRefundUser = objectNode.get("requestRefundUser").asLong();
            Long requestRefundPointId = objectNode.get("requestRefundPointId").asLong();

            log.info(requestRefundUser + "requestRefundUser");
            log.info(requestRefundPointId + "requestRefundPointId");

            alarmDTO.setAlarmCategory("POINT");
            alarmDTO.setAlarmContent(pointRepository.findById(requestRefundPointId).get().getPointAmountHistory() + "POINT 에 대한 환불이 완료되었습니다.");
            alarmDTO.setUserId(requestRefundUser);

        }
        alarmService.addAlarm(alarmDTO);
    }

    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.RefundAlarm)")
    public void refund(JoinPoint joinPoint) {
        Long pointId = Long.valueOf(joinPoint.getArgs()[0].toString());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[1];
        Long userId = (Long) request.getSession().getAttribute("userId");
        Point point = pointRepository.findById(pointId).get();


        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setAlarmCategory("POINT");
        alarmDTO.setAlarmContent(point.getPointAmountHistory() + "POINT 에 대한 환불신청이 완료되었습니다.");
        alarmDTO.setUserId(userId);
        alarmService.addAlarm(alarmDTO);
    }

    //    모임 승인요청 답변 알림
    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.GroupAlarm)")
    public void requestGroup(JoinPoint joinPoint) {
        Long groupId = Long.valueOf(joinPoint.getArgs()[0].toString());
        String status = joinPoint.getArgs()[1].toString();
        Group group = groupRepository.findById(groupId).get();
        Long userId = group.getUser().getUserId();

        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setAlarmCategory("GROUP");
        if (status.equals("disapprove")) {
            alarmDTO.setAlarmContent("\"" + group.getGroupName() + "\" 모임이 거절되었습니다");
            alarmDTO.setUserId(group.getUser().getUserId());
            alarmDTO.setContentId(group.getGroupId());
        } else {
            alarmDTO.setAlarmContent("\"" + group.getGroupName() + "\" 모임이 승인되었습니다✨🎉");
            alarmDTO.setUserId(group.getUser().getUserId());
            alarmDTO.setContentId(group.getGroupId());
//          모임을 첫 등록(첫 승인)할 경우에 리워드 지급
            if (achievementRepositoryImpl.checkDuplicatedById(userId, 10007L)) {
                Quest quest = questRepository.findById(10007L).get();
                achievementService.save(userId, quest);

                PointDTO pointDTO = new PointDTO();
                pointDTO.setPointAmountHistory(quest.getQuestPoint());
                pointDTO.setUserId(userId);

                pointService.questReward(pointDTO, quest);

                AlarmDTO questAlarm = new AlarmDTO();

                questAlarm.setUserId(userId);
                alarmService.questAlarm(questAlarm, quest);
            }
        }
        alarmService.addAlarm(alarmDTO);
    }

    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.PostAlarm)")
    public void postAlarm(JoinPoint joinPoint) {
        CommunityPostDTO postDTO = (CommunityPostDTO) joinPoint.getArgs()[0];
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[1];
        Long userId = (Long) request.getSession().getAttribute("userId");
        String[] category = {"독서", "운동", "요리", "영화", "고민"};
        int k = 0;

        for (int i = 10014; i <= 10018; i++) {
//            각 카테고리 별 첫 게시글 작성 시 지급하는 뱃지
            if (postDTO.getCommunityCategory().equals(category[k]) && achievementRepositoryImpl.checkDuplicatedById(userId, (long) i)) {
                Quest quest = questRepository.findById((long) i).get();
                achievementService.save(userId, quest);

                PointDTO pointDTO = new PointDTO();
                pointDTO.setPointAmountHistory(quest.getQuestPoint());
                pointDTO.setUserId(userId);

                pointService.questReward(pointDTO, quest);

                AlarmDTO questAlarm = new AlarmDTO();

                questAlarm.setUserId(userId);
                alarmService.questAlarm(questAlarm, quest);
            }
            k++;
        }

        //      댓글 50개, 게시글 10개 작성 시 받는 뱃지
        if (postRepositoryImpl.countPostByUser(userId) >= 10 && replyRepositoryImpl.countReplyByUser(userId) >= 50 && achievementRepositoryImpl.checkDuplicatedById(userId, 10002L)) {
            Quest quest = questRepository.findById(10002L).get();
            achievementService.save(userId, quest);

            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointAmountHistory(quest.getQuestPoint());
            pointDTO.setUserId(userId);

            pointService.questReward(pointDTO, quest);

            AlarmDTO questAlarm = new AlarmDTO();

            questAlarm.setUserId(userId);
            alarmService.questAlarm(questAlarm, quest);
        }
    }

    //    회원가입 알림, 회원가입 축하 리워드
    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.JoinAlarm)")
    public void joinAlarm(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[1];
        Long userId = (Long) request.getSession().getAttribute("userId");
        User user = userRepository.findById(userId).get();


        if(achievementRepositoryImpl.checkDuplicatedById(userId, 10001L)){
            AlarmDTO alarmDTO = new AlarmDTO();
            alarmDTO.setAlarmCategory("LOGIN");
            alarmDTO.setAlarmContent("🎉✨ 회원가입을 환영합니다 ✨🎉");
            alarmDTO.setUserId(userId);
            alarmService.addAlarm(alarmDTO);

            Quest quest = questRepository.findById(10001L).get();
            achievementService.save(userId, quest);

            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointAmountHistory(quest.getQuestPoint());
            pointDTO.setUserId(userId);

            pointService.questReward(pointDTO, quest);

            AlarmDTO questAlarm = new AlarmDTO();

            questAlarm.setUserId(userId);
            alarmService.questAlarm(questAlarm, quest);
        }
    }

    @After("@annotation(com.example.myoceanproject.aspect.annotation.TodoAlarm)")
    public void toDoList(JoinPoint joinPoint) {
        Long userId = (long) joinPoint.getArgs()[2];
//        하루에 투두리스트 10개 작성 시
        if (mySpaceService.showAllByToday(userId).size() >= 10 && achievementRepositoryImpl.checkDuplicatedById(userId, 10010L)) {
            Quest quest = questRepository.findById(10010L).get();
            achievementService.save(userId, quest);

            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointAmountHistory(quest.getQuestPoint());
            pointDTO.setUserId(userId);

            pointService.questReward(pointDTO, quest);

            AlarmDTO questAlarm = new AlarmDTO();

            questAlarm.setUserId(userId);
            alarmService.questAlarm(questAlarm, quest);
        }
        ;
    }

    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.DiaryAlarm)")
    public void diary(JoinPoint joinPoint) {
        DiaryDTO diaryDTO = (DiaryDTO) joinPoint.getArgs()[0];
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[1];
        long userId = (long) request.getSession().getAttribute("userId");
        diaryDTO.setUserId(userId);
        // 나만의 일기 3회 작성 시
        if (diaryRepositoryImpl.countDiaryByuserId(DiaryCategory.CLOSEDIARY, diaryDTO.getUserId()) >= 2 && achievementRepositoryImpl.checkDuplicatedById(diaryDTO.getUserId(), 10012L)) {
            Quest quest = questRepository.findById(10012L).get();
            achievementService.save(diaryDTO.getUserId(), quest);

            PointDTO pointDTO = new PointDTO();
            pointDTO.setPointAmountHistory(quest.getQuestPoint());
            pointDTO.setUserId(diaryDTO.getUserId());

            pointService.questReward(pointDTO, quest);

            AlarmDTO questAlarm = new AlarmDTO();

            questAlarm.setUserId(diaryDTO.getUserId());
            alarmService.questAlarm(questAlarm, quest);
        }
        if (diaryDTO.getDiaryCategory().equals(DiaryCategory.OPENDIARY.toString())) {
            // 교환 일기 3회 작성 시
            if (diaryRepositoryImpl.countDiaryByuserId(DiaryCategory.OPENDIARY, diaryDTO.getUserId()) >= 2 && achievementRepositoryImpl.checkDuplicatedById(diaryDTO.getUserId(), 10011L)) {
                Quest quest = questRepository.findById(10011L).get();
                achievementService.save(diaryDTO.getUserId(), quest);

                PointDTO pointDTO = new PointDTO();
                pointDTO.setPointAmountHistory(quest.getQuestPoint());
                pointDTO.setUserId(diaryDTO.getUserId());

                pointService.questReward(pointDTO, quest);

                AlarmDTO questAlarm = new AlarmDTO();

                questAlarm.setUserId(diaryDTO.getUserId());
                alarmService.questAlarm(questAlarm, quest);
            }
        }
    }

    @AfterReturning("@annotation(com.example.myoceanproject.aspect.annotation.TodayAlarm)")
    public void todayQuest(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long userId = (Long) request.getSession().getAttribute("userId");
        QuestDTO questDTO = questService.showTodayQuest();

        AlarmDTO questAlarm = new AlarmDTO();

        questAlarm.setUserId(userId);
        alarmService.questAlarm(questAlarm, questDTO.toEntity());
    }

    @After("@annotation(com.example.myoceanproject.aspect.annotation.ExDiaryAlarm)")
    public void exchangeDiary(JoinPoint joinPoint) {
        Long userId = (Long) joinPoint.getArgs()[0];
        Diary diary = (Diary) joinPoint.getArgs()[1];

        log.info("------------------------------diary-----------------------");
        try {
            DiaryDTO diaryDTO = (DiaryDTO) joinPoint.getArgs()[2];
            log.info("--------------------------------------------------------");
            log.info("afterEx");
            log.info("--------------------------------------------------------");
        } catch (NullPointerException e) {
            log.info("--------------------------------------------------------");
            log.info("beforeExDiary");
            log.info("--------------------------------------------------------");
        }

    }


}




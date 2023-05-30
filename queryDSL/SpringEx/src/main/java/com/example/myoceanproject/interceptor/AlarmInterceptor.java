package com.example.myoceanproject.interceptor;

import com.example.myoceanproject.controller.myAlarm.MyAlarmRestController;
import com.example.myoceanproject.domain.AlarmDTO;
import com.example.myoceanproject.domain.UserDTO;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.repository.GroupRepository;
import com.example.myoceanproject.repository.quest.QuestAchievementRepositoryImpl;
import com.example.myoceanproject.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmInterceptor implements HandlerInterceptor {

    private AlarmService alarmService;
    private MyAlarmRestController alarmRestController;
    private GroupRepository groupRepository;
    private QuestAchievementRepositoryImpl questAchievementRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

//            response.sendRedirect("/alarm/update/25");
//        Long groupId = Long.valueOf(request.getRequestURI().split("/")[3]);
//        log.info(groupId + "id");
//        Group group = groupRepository.findById(groupId).get();
//        log.info(group.toString());
//        log.info("------------------------------------");
//        AlarmDTO alarmDTO = new AlarmDTO();
//        alarmDTO.setAlarmCategory("GROUP");
//        if(request.getRequestURI().contains("disapproved")){
//            alarmDTO.setAlarmContent("\""+ group.getGroupName() + "\" 모임이 거절되었습니다");
//            alarmDTO.setUserId(group.getUser().getUserId());
//            alarmDTO.setContentId(group.getGroupId());
//        }else{
//            alarmDTO.setAlarmContent("\""+ group.getGroupName() + "\" 모임이 승인되었습니다✨🎉");
//            alarmDTO.setUserId(group.getUser().getUserId());
//            alarmDTO.setContentId(group.getGroupId());
//        }
//            alarmService.addAlarm(alarmDTO);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

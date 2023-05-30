package com.example.myoceanproject.controller.myPoint;

import com.example.myoceanproject.aspect.annotation.PointAlarm;
import com.example.myoceanproject.aspect.annotation.RefundAlarm;
import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.Point;
import com.example.myoceanproject.entity.User;
import com.example.myoceanproject.repository.PointRepository;
import com.example.myoceanproject.repository.UserRepository;
import com.example.myoceanproject.service.PointService;
import com.example.myoceanproject.service.UserService;
import com.example.myoceanproject.type.PointCheckType;
import com.example.myoceanproject.type.PointType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.example.myoceanproject.entity.QPoint.point;
import static com.example.myoceanproject.entity.QUser.user;

@Slf4j
@Controller
@RequestMapping("/myPoint/*")
public class MyPointController {

    @Autowired
    private PointService pointService;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    // 마이 포인트 페이지
    @GetMapping("/index")
    public String myPoint(Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        List<PointDTO> pointDTOList=pointService.findAllPointByUser((Long)session.getAttribute("userId"));
        log.info("pointList:"+pointDTOList);
        model.addAttribute("points", pointDTOList);
        return "app/myPoint/myPoint";
    }

    @RequestMapping("/savePay")
    @ResponseBody
    @Transactional
    @Modifying
    @PointAlarm
    public String receivePayment(@RequestBody ObjectNode objectNode){
        PointDTO pointDTO=new PointDTO();
        UserDTO userDTO=new UserDTO();

        Long userPoint=objectNode.get("point").asLong();
        Long userId=objectNode.get("userId").asLong();
        String merchantUid=objectNode.get("merchantUid").asText();
        String impUid=objectNode.get("impUid").asText();
        String content=objectNode.get("content").asText();

        User user1=new User();
        user1.setUserId(userId);

        pointDTO.setPointAmountHistory(Integer.valueOf(String.valueOf(userPoint)));
        pointDTO.setPointType("결제");
        pointDTO.setPointMerchantUid(merchantUid);
        pointDTO.setPointImpUid(impUid);
        pointDTO.setPointContent(content);
        pointDTO.setPointCheckType("처음");
        Point point=pointDTO.toEntity();
        point.changeUser(user1);

        log.info("pointDTO:"+pointDTO);
        log.info("user:"+user1);
        log.info("point:"+point);

        pointRepository.save(point);

        User selectuser=jpaQueryFactory.selectFrom(user).where(user.userId.eq(userId)).fetchOne();

        int updateTotalPoint=selectuser.getUserTotalPoint()+Integer.parseInt(String.valueOf(userPoint));
        selectuser.updateUserTotalPoint(updateTotalPoint);

        log.info("userDTO1:"+userDTO);
        log.info("selectuser:"+selectuser);
        return "success";
    }

    @GetMapping("/myPointRefund")
    public String myPointRefund(Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        List<PointDTO> pointDTOList=pointService.findAllPayPointByUser((Long)session.getAttribute("userId"), PointType.PAY);
        log.info("pointList:"+pointDTOList);
        model.addAttribute("payPoints", pointDTOList);
        return "app/myPoint/myPointRefund";
    }

    @RequestMapping("/refundRequest")
    @ResponseBody
    @Transactional
    @Modifying
    @RefundAlarm
    public String myPointRefundRequest(@RequestBody String pointId,HttpServletRequest request){
        PointDTO pointDTO=new PointDTO();
        PointDTO pointDTO1=new PointDTO();
        User user1=new User();

        HttpSession session=request.getSession();
        Long userId=(Long)session.getAttribute("userId");

        user1.setUserId(userId);

        log.info("controller in");
        Point points=jpaQueryFactory.selectFrom(point).where(point.pointId.eq(Long.parseLong(pointId))).fetchOne();
        pointDTO.setPointAmountHistory(points.getPointAmountHistory());
        pointDTO.setPointType("환불대기");
        pointDTO.setPointMerchantUid(points.getPointMerchantUid());
        pointDTO.setPointImpUid(points.getPointImpUid());
        pointDTO.setPointContent("MyOcean 포인트 환불 요청");
        pointDTO.setPointCheckType("처음");
        Point point1=pointDTO.toEntity();
        point1.changeUser(user1);

        pointRepository.save(point1);

        pointDTO1.setPointCheckType("이후");
        pointDTO1.setPointContent("MyOcean 포인트 충전");
        pointDTO1.setPointType("결제");
        points.update(pointDTO1);


        return "success";
    }

    @GetMapping("/managerRefund")
    public String managerRefund(Model model){
        List<PointDTO> pointDTOList=pointService.findAllRefundPointByAllUser(PointType.REFUNDREADY);
        log.info("pointList:"+pointDTOList);
        model.addAttribute("refundPoints", pointDTOList);
        return "app/myPoint/managerRefund";
    }

    @RequestMapping("/managerRefund")
    @ResponseBody
    @Transactional
    @Modifying
    @PointAlarm
    public String managerRefundOk(@RequestBody ObjectNode objectNode){

        PointDTO pointDTO=new PointDTO();
        PointDTO pointDTO1=new PointDTO();
        UserDTO userDTO=new UserDTO();
        User user1=new User();

        Long userIds=objectNode.get("requestRefundUser").asLong();
        Long pointIds=objectNode.get("requestRefundPointId").asLong();

        user1.setUserId(userIds);

        Point points=jpaQueryFactory.selectFrom(point).where(point.pointId.eq(pointIds)).fetchOne();
        pointDTO.setPointAmountHistory(points.getPointAmountHistory());
        pointDTO.setPointType("환불승인");
        pointDTO.setPointMerchantUid(points.getPointMerchantUid());
        pointDTO.setPointImpUid(points.getPointImpUid());
        pointDTO.setPointContent("MyOcean 포인트 환불 완료");
        pointDTO.setPointCheckType("처음");
        Point point1=pointDTO.toEntity();
        point1.changeUser(user1);

        pointRepository.save(point1);


        pointDTO1.setPointCheckType("이후");
        pointDTO1.setPointContent("MyOcean 포인트 환불 요청");
        pointDTO1.setPointType("환불대기");
        points.update(pointDTO1);

        User updateUser=jpaQueryFactory.selectFrom(user).where(user.userId.eq(userIds)).fetchOne();

        int updateTotalPoint=updateUser.getUserTotalPoint()-Integer.parseInt(String.valueOf(points.getPointAmountHistory()));
        updateUser.updateUserTotalPoint(updateTotalPoint);

        return "success";
    }
}
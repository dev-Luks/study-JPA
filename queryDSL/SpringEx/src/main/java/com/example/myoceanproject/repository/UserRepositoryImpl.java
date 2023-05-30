package com.example.myoceanproject.repository;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.QUser;
import com.example.myoceanproject.entity.QUserFind;
import com.example.myoceanproject.repository.community.post.CommunityPostRepositoryImpl;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepositoryImpl;
import com.example.myoceanproject.repository.quest.QuestRepositoryImpl;
import com.example.myoceanproject.type.UserAccountStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.myoceanproject.entity.QCommunityPost.communityPost;
import static com.example.myoceanproject.entity.QUser.user;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory queryFactory;

    private final CommunityPostRepositoryImpl postRepositoryImpl;

    private final CommunityReplyRepositoryImpl replyRepositoryImpl;

    private final QuestRepositoryImpl questRepositoryImpl;

    @Override
    public int findCountByemail(String email) {
//        @Query("select count(u.userEmail) from User u where ")
        return 0;
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        List<UserDTO> users = queryFactory.select(new QUserDTO(
                    user.userId,
                    user.userPassword,
                    user.userNickname,
                    user.userAccountStatus,
                    user.userFileName,
                    user.userFilePath,
                    user.userFileSize,
                    user.userFileUuid,
                    user.userEmail,
                    user.userLoginMethod,
                    user.userTotalPoint,
                    user.createDate,
                    user.updatedDate,
                    user.userOauthId
                ))
                .from(user)
                .orderBy(user.userId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = queryFactory.selectFrom(user)
                .fetch().size();

        return new PageImpl<>(users, pageable, total);
    }
    @Override
    public Page<UserDTO> findAll(Pageable pageable, Criteria criteria) {
        List<UserDTO> users = queryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
                ))
                .from(user)
                .where(user.userNickname.contains(criteria.getKeyword()))
                .orderBy(user.userId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        users.stream().forEach(userDTO -> {
            userDTO.setUserPostCount(postRepositoryImpl.countPostByUser(userDTO.getUserId()));
            userDTO.setUserReplyCount(replyRepositoryImpl.countReplyByUser(userDTO.getUserId()));
        });

        long total = queryFactory.selectFrom(user)
                .where(user.userNickname.contains(criteria.getKeyword()))
                .fetch().size();

        return new PageImpl<>(users, pageable, total);
    }
    @Override
    public List<UserDTO> findAllByActive() {
        return queryFactory.select(new QUserDTO(
                        user.userId,
                        user.userPassword,
                        user.userNickname,
                        user.userAccountStatus,
                        user.userFileName,
                        user.userFilePath,
                        user.userFileSize,
                        user.userFileUuid,
                        user.userEmail,
                        user.userLoginMethod,
                        user.userTotalPoint,
                        user.createDate,
                        user.updatedDate,
                        user.userOauthId
                ))
                .from(user)
                .where(user.userAccountStatus.eq(UserAccountStatus.ACTIVE))
                .orderBy(user.userId.desc())
                .fetch();
    }

    public Page<UserDTO> findAllByStatus(Pageable pageable, UserAccountStatus userAccountStatus){
        List<UserDTO> users = queryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
                ))
                .from(user)
                .where(user.userAccountStatus.eq(userAccountStatus))
                .orderBy(user.userId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        users.stream().forEach(userDTO -> {
            userDTO.setUserPostCount(postRepositoryImpl.countPostByUser(userDTO.getUserId()));
            userDTO.setUserReplyCount(replyRepositoryImpl.countReplyByUser(userDTO.getUserId()));
        });

        long total = queryFactory.selectFrom(user)
                .where(user.userAccountStatus.eq(userAccountStatus))
                .fetch().size();

        return new PageImpl<>(users, pageable, total);
    }
    public Page<UserDTO> findAllByStatus(Pageable pageable, Criteria criteria, UserAccountStatus userAccountStatus){
        List<UserDTO> users = queryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
                ))
                .from(user)
                .where(user.userAccountStatus.eq(userAccountStatus).and(user.userNickname.contains(criteria.getKeyword())))
                .orderBy(user.userId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        users.stream().forEach(userDTO -> {
            userDTO.setUserPostCount(postRepositoryImpl.countPostByUser(userDTO.getUserId()));
            userDTO.setUserReplyCount(replyRepositoryImpl.countReplyByUser(userDTO.getUserId()));
        });

        long total = queryFactory.selectFrom(user)
                .where(user.userAccountStatus.eq(userAccountStatus).and(user.userNickname.contains(criteria.getKeyword())))
                .fetch().size();

        return new PageImpl<>(users, pageable, total);

    }

    @Override
    public UserDTO findUserById(Long userId){
        UserDTO userDTO = queryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
                ))
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();
        return userDTO;
    }

    @Override
    public UserDTO findAllByDashboard(){
        List<UserDTO> users = queryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
        ))
        .from(user)
        .orderBy(user.updatedDate.desc())
        .offset(0)
        .limit(7).fetch();

        users.stream().forEach(userDTO -> {
            userDTO.setUserPostCount(postRepositoryImpl.countPostByUser(userDTO.getUserId()));
            userDTO.setUserReplyCount(replyRepositoryImpl.countReplyByUser(userDTO.getUserId()));
            userDTO.setBadgeCount(questRepositoryImpl.countQuestByUser(userDTO.getUserId()));
        });

        UserDTO userDTO = new UserDTO();
        userDTO.setUserList(users);

        return userDTO;
    }

    //  유저 조회하기
    @Override
    public UserDTO findByUserId(Long userId){
        UserDTO searchUser = queryFactory.select(new QUserDTO(
                user.userId,
                user.userPassword,
                user.userNickname,
                user.userAccountStatus,
                user.userFileName,
                user.userFilePath,
                user.userFileSize,
                user.userFileUuid,
                user.userEmail,
                user.userLoginMethod,
                user.userTotalPoint,
                user.createDate,
                user.updatedDate,
                user.userOauthId
        ))
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();

        return searchUser;
    }
}

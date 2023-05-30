package com.example.myoceanproject.repository.ask;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.type.AskStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.myoceanproject.entity.QAsk.ask;

@Repository
@RequiredArgsConstructor
public class AskRepositoryImpl implements AskCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AskDTO findAllByDashboard(){
        List<AskDTO> asks = jpaQueryFactory.select(new QAskDTO(
                        ask.askId,
                        ask.user.userId,
                        ask.user.userNickname,
                        ask.askStatus,
                        ask.askTitle,
                        ask.askContent,
                        ask.answer,
                        ask.askCategory,
                        ask.createDate,
                        ask.updatedDate
                ))
                .from(ask)
                .orderBy(ask.createDate.desc())
                .offset(0)
                .limit(7).fetch();

                AskDTO askDTO = new AskDTO();
                askDTO.setAskList(asks);
        return askDTO;
    }

    @Override
    public Page<AskDTO> findAllByStatus(Pageable pageable){
        List<AskDTO> asks = jpaQueryFactory.select(new QAskDTO(
                        ask.askId,
                        ask.user.userId,
                        ask.user.userNickname,
                        ask.askStatus,
                        ask.askTitle,
                        ask.askContent,
                        ask.answer,
                        ask.askCategory,
                        ask.createDate,
                        ask.updatedDate
                ))
                .from(ask)
                .where(ask.askStatus.eq(AskStatus.WAITING))
                .orderBy(ask.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(ask)
                .where(ask.askStatus.eq(AskStatus.WAITING))
                .fetch().size();

        return new PageImpl<>(asks, pageable, total);
    }
    @Override
    public Page<AskDTO> findAllByStatus(Pageable pageable, Criteria criteria){
        List<AskDTO> asks = jpaQueryFactory.select(new QAskDTO(
                        ask.askId,
                        ask.user.userId,
                        ask.user.userNickname,
                        ask.askStatus,
                        ask.askTitle,
                        ask.askContent,
                        ask.answer,
                        ask.askCategory,
                        ask.createDate,
                        ask.updatedDate
                ))
                .from(ask)
                .where(ask.askStatus.eq(AskStatus.WAITING).and(ask.askTitle.contains(criteria.getKeyword())))
                .orderBy(ask.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(ask)
                .where(ask.askStatus.eq(AskStatus.WAITING).and(ask.askTitle.contains(criteria.getKeyword())))
                .fetch().size();

        return new PageImpl<>(asks, pageable, total);
    }
    @Override
    public Page<AskDTO> findAll(Pageable pageable){
        List<AskDTO> asks = jpaQueryFactory.select(new QAskDTO(
                        ask.askId,
                        ask.user.userId,
                        ask.user.userNickname,
                        ask.askStatus,
                        ask.askTitle,
                        ask.askContent,
                        ask.answer,
                        ask.askCategory,
                        ask.createDate,
                        ask.updatedDate
                ))
                .from(ask)
                .orderBy(ask.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(ask)
                .fetch().size();

        return new PageImpl<>(asks, pageable, total);
    }

    @Override
    public Page<AskDTO> findAll(Pageable pageable, Criteria criteria){
        List<AskDTO> asks = jpaQueryFactory.select(new QAskDTO(
                        ask.askId,
                        ask.user.userId,
                        ask.user.userNickname,
                        ask.askStatus,
                        ask.askTitle,
                        ask.askContent,
                        ask.answer,
                        ask.askCategory,
                        ask.createDate,
                        ask.updatedDate
                ))
                .from(ask)
                .where(ask.askTitle.contains(criteria.getKeyword()))
                .orderBy(ask.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.selectFrom(ask)
                .where(ask.askTitle.contains(criteria.getKeyword()))
                .fetch().size();

        return new PageImpl<>(asks, pageable, total);
    }

}

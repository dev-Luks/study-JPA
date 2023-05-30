package com.example.myoceanproject.repository.community.post;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.CommunityLike;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.QCommunityPost;
import com.example.myoceanproject.repository.community.like.CommunityLikeRepositoryImpl;
import com.example.myoceanproject.repository.community.reply.CommunityReplyRepositoryImpl;
import com.example.myoceanproject.type.CommunityCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.myoceanproject.entity.QCommunityPost.communityPost;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommunityPostRepositoryImpl implements CommunityPostCustomRepository{
    @Autowired
    private CommunityReplyRepositoryImpl replyRepositoryImpl ;
    @Autowired
    private CommunityLikeRepositoryImpl likeRepositoryImpl ;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<CommunityPostDTO> findAllByCategory(Pageable pageable, CommunityCategory communityCategory){

        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .where(communityPost.communityCategory.eq(communityCategory))
                .orderBy(communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });

        long total = jpaQueryFactory.selectFrom(communityPost)
                .where(communityPost.communityCategory.eq(communityCategory))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }

    //    전체 출력 동적쿼리(비회원 전용)
    @Override
    public List<CommunityPostDTO> filterCommunityBoard(int page, List<String> communityCategories) {
        List<CommunityPostDTO> communityBoards = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                )).from(communityPost)
                .where(
                        categoryFilter(communityCategories)
                )
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .offset(page * 10)
                .limit(10)
                .fetch();

        communityBoards.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });

        return communityBoards;
    }


    //    전체 출력 동적쿼리(카테고리 필터 적용한 회원 전용)
    @Override
    public List<CommunityPostDTO> filterCommunityBoard(int page, List<String> communityCategories, Long id) {
        List<CommunityPostDTO> communityBoards = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                )).from(communityPost)
                .where(
                        categoryFilter(communityCategories)
                )
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .offset(page * 10)
                .limit(10)
                .fetch();

        communityBoards.stream().forEach(communityPostDTO -> {
            log.info("=============" + id); // 여기서 null
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
            communityPostDTO.setCheckLike(likeRepositoryImpl.findByCommunityPostAndUser(id,communityPostDTO.getCommunityPostId()));
        });

        return communityBoards;
    }

    //    전체 출력 동적쿼리(필터 적용 안 한 회원 전용)
    public List<CommunityPostDTO> filterCommunityBoard(int page, Long id) {
        List<CommunityPostDTO> communityBoards = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                )).from(communityPost)
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .offset(page * 10)
                .limit(10)
                .fetch();

        communityBoards.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
            communityPostDTO.setCheckLike(likeRepositoryImpl.findByCommunityPostAndUser(id,communityPostDTO.getCommunityPostId()));
        });

        return communityBoards;
    }

    //    전체 출력 동적쿼리(필터 적용 안 한 회원 전용)
    public List<CommunityPostDTO> filterCommunityBoard(int page) {
        List<CommunityPostDTO> communityBoards = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                )).from(communityPost)
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .offset(page * 10)
                .limit(10)
                .fetch();

        communityBoards.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });

        return communityBoards;
    }


    @Override
    public List<CommunityPostDTO> filterCommunityBoard(List<String> communityCategories, Long userId) {
        List<CommunityPostDTO> communityBoards = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                )).from(communityPost)
                .where(
                        categoryFilter(communityCategories)
                )
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .limit(10)
                .fetch();

        communityBoards.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
            communityPostDTO.setCheckLike(likeRepositoryImpl.findByCommunityPostAndUser(userId,communityPostDTO.getCommunityPostId()));
        });

        return communityBoards;
    }
    private BooleanBuilder categoryFilter(List<String> communityCategories){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for(String communityCategory : communityCategories){
            if(communityCategory.equals("EXERCISE")){ // 사용자가 요청한 값 중 운동이 있다면
                booleanBuilder.or(communityPost.communityCategory.eq(CommunityCategory.EXERCISE)); //EXERCISE를 반환해라
            }
            if(communityCategory.equals("COOK")){
                booleanBuilder.or(communityPost.communityCategory.eq(CommunityCategory.COOK));
            }
            if(communityCategory.equals("MOVIE")){
                booleanBuilder.or(communityPost.communityCategory.eq(CommunityCategory.MOVIE));
            }
            if(communityCategory.equals("BOOK")){
                booleanBuilder.or(communityPost.communityCategory.eq(CommunityCategory.BOOK));
            }
            if(communityCategory.equals("COUNSELING")){
                booleanBuilder.or(communityPost.communityCategory.eq(CommunityCategory.COUNSELING));
            }
        }
        return booleanBuilder;
    }




    @Override
    public Page<CommunityPostDTO> findAllByCategory(Pageable pageable, CommunityCategory communityCategory, Criteria criteria){
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .where(communityPost.communityCategory.eq(communityCategory).and(communityPost.communityTitle.contains(criteria.getKeyword())))
                .orderBy(communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });
        long total = jpaQueryFactory.selectFrom(communityPost)
                .where(communityPost.communityCategory.eq(communityCategory).and(communityPost.communityTitle.contains(criteria.getKeyword())))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Page<CommunityPostDTO> findAll(Pageable pageable){
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .where(communityPost.communityCategory.ne(CommunityCategory.COUNSELING))
                .orderBy(communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });
        long total = jpaQueryFactory.selectFrom(communityPost)
                .where(communityPost.communityCategory.ne(CommunityCategory.COUNSELING))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }


    //  리스트로 게시글 출력하기
    @Override
    public List<CommunityPostDTO> findAllByList(Long userId){
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .limit(10)
                .fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
            communityPostDTO.setCheckLike(likeRepositoryImpl.findByCommunityPostAndUser(userId,communityPostDTO.getCommunityPostId()));
        });

        return posts;
    }


    //  로그인 안 한 유저를 위한 것
    public List<CommunityPostDTO> findAllByListWithoutLogin(){
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .limit(10)
                .fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });

        return posts;
    }

    @Override
    public Page<CommunityPostDTO> findAll(Pageable pageable, Criteria criteria){
//        검색어가 있는 경우
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .where(communityPost.communityTitle.contains(criteria.getKeyword()).and(communityPost.communityCategory.ne(CommunityCategory.COUNSELING)))
                .orderBy(communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        posts.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });
        long total = jpaQueryFactory.selectFrom(communityPost)
                .where(communityPost.communityTitle.contains(criteria.getKeyword()).and(communityPost.communityCategory.ne(CommunityCategory.COUNSELING)))
                .fetch().size();

        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public void deleteByPost(CommunityPost post){
//        삭제는 어짜피 entity로 진행되기 때문
        List<CommunityLike> likes = likeRepositoryImpl.findByCommunityPost(post);
        List<CommunityReplyDTO> replies = replyRepositoryImpl.findByCommunityPost(post);

//        만약 포스트객체에 라이크가 있다면?
        if(!likes.isEmpty()) {
//        전체 삭제 메소드를 실행하여 포스트와 관련된 파일을 전체 삭제한다.
            likeRepositoryImpl.deleteByCommunityPost(post);
        }
//        댓글이 있다면?
        if(!replies.isEmpty()) {
//        전체 삭제 메소드를 실행하여 포스트와 관련된 파일을 전체 삭제한다.
            replyRepositoryImpl.deleteByCommunityPost(post);
        }

    }

    @Override
    public Integer countPostByUser(Long userId){
        return Math.toIntExact(jpaQueryFactory.select(communityPost.communityPostId.count())
                .from(communityPost)
                .where(communityPost.user.userId.eq(userId))
                .fetchFirst());
    }

    public CommunityPostDTO findByCommunityId(Long communityPostId){
        return jpaQueryFactory.select(new QCommunityPostDTO(
                communityPost.communityPostId,
                communityPost.user.userId,
                communityPost.user.userNickname,
                communityPost.user.userFileName,
                communityPost.user.userFilePath,
                communityPost.user.userFileSize,
                communityPost.user.userFileUuid,
                communityPost.communityCategory,
                communityPost.communityTitle,
                communityPost.communityContent,
                communityPost.communityFilePath,
                communityPost.communityFileName,
                communityPost.communityFileUuid,
                communityPost.communityFileSize,
                communityPost.communityViewNumber,
                communityPost.communityLikeNumber,
                communityPost.createDate,
                communityPost.updatedDate
        )).from(communityPost).where(communityPost.communityPostId.eq(communityPostId)).fetchOne();
    }



    // 무한스크롤
    // 처음에 10개를 뿌려주고, 스크롤이 맨 끝 단에 닿으면 다음의 10개가 뿌려짐
    public List<CommunityPostDTO> selectScrollBoards(int page){
        List<CommunityPostDTO> boards = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .offset(page * 10)
                .limit(10)
                .fetch();
        boards.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });
        return boards;
    }

    // 무한스크롤(회원전용)
    // 처음에 10개를 뿌려주고, 스크롤이 맨 끝 단에 닿으면 다음의 10개가 뿌려짐
    public List<CommunityPostDTO> selectScrollBoards(int page, Long userId){
        List<CommunityPostDTO> boards = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .offset(page * 10)
                .limit(10)
                .fetch();
        boards.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
            communityPostDTO.setCheckLike(likeRepositoryImpl.findByCommunityPostAndUser(userId,communityPostDTO.getCommunityPostId()));
        });
        return boards;
    }

    public CommunityPostDTO findAllByDashboard(){
        List<CommunityPostDTO> posts = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                ))
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .offset(0)
                .limit(7)
                .fetch();

        CommunityPostDTO postDTO = new CommunityPostDTO();
        postDTO.setPostList(posts);
        return postDTO;
    }

    @Override
    public List<CommunityPostDTO> filterCommunityBoard(List<String> communityCategories) {
        List<CommunityPostDTO> communityBoards = jpaQueryFactory.select(new QCommunityPostDTO(
                        communityPost.communityPostId,
                        communityPost.user.userId,
                        communityPost.user.userNickname,
                        communityPost.user.userFileName,
                        communityPost.user.userFilePath,
                        communityPost.user.userFileSize,
                        communityPost.user.userFileUuid,
                        communityPost.communityCategory,
                        communityPost.communityTitle,
                        communityPost.communityContent,
                        communityPost.communityFilePath,
                        communityPost.communityFileName,
                        communityPost.communityFileUuid,
                        communityPost.communityFileSize,
                        communityPost.communityViewNumber,
                        communityPost.communityLikeNumber,
                        communityPost.createDate,
                        communityPost.updatedDate
                )).from(communityPost)
                .where(
                        categoryFilter(communityCategories)
                )
                .from(communityPost)
                .orderBy(communityPost.createDate.desc())
                .limit(10)
                .fetch();

        communityBoards.stream().forEach(communityPostDTO -> {
            communityPostDTO.setCommunityReplyCount(replyRepositoryImpl.countReplyByCommunityPost(communityPostDTO.getCommunityPostId()));
        });

        return communityBoards;
    }
}

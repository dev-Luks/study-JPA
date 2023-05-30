package com.example.basic.repository;

import com.example.basic.domain.entity.Member;
import com.example.basic.type.MemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Spring Data JPA
// JpaRepository를 상속받은 인터페이스에 직접 구현체를 만든 후 주입해준다.

// JpaRepository<Type, Id>
// Type: 엔티티 이름
// Id: PK 자료형
public interface MemberRepository extends JpaRepository<Member, Long> {
    public List<Member> findByMemberName(String memberName);
    public List<Member> findByMemberNameContaining(String memberName);
    public List<Member> findByMemberNameStartingWith(String memberName);
    public List<Member> findByMemberNameEndingWith(String memberName);
    public List<Member> findTop2ByMemberAgeGreaterThanEqualOrderByMemberAgeDesc(int memberAge);
    public Boolean existsByMemberAge(int memberAge);
    public int countAllByMemberType(MemberType memberType);
    public void deleteByMemberAgeGreaterThanEqual(int memberAge);

}














package com.example.intermediate.repository.user;

import com.example.intermediate.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.address = '서울', m.updatedDate = current_timestamp where m.address = :address")
    public void updateAllByAddress(String address);
}

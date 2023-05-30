package com.example.intermediate.repository.user;

import com.example.intermediate.entity.user.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BusinessRepository extends JpaRepository<Business, Long> {
//    주소가 경기도인 기업회원은 아이디 끝에 9를 붙이기
    @Modifying(clearAutomatically = true)
//    벌크 연산 시, Auditing Listener가 실행되지 않아서 직접 수정 날짜를 현재 시간(current_timestamp)으로 변경한다.
    @Query("update Business b set b.userId = concat(b.userId, '9'), b.updatedDate = current_timestamp where b.address = :address")
    public void updateUserIdByAddress(String address);
}

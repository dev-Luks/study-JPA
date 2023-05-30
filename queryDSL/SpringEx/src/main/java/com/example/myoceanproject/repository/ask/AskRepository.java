package com.example.myoceanproject.repository.ask;

import com.example.myoceanproject.entity.Ask;
import com.example.myoceanproject.type.AskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface AskRepository extends JpaRepository<Ask, Long> {

    @Query("select a from Ask a where a.user.userId=:userId and a.askCategory=:quest")
    public Ask findcategoryByuserId(@Param("userId") Long userId,@Param("quest") AskCategory quest);
}
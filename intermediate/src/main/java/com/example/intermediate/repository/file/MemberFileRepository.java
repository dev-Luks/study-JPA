package com.example.intermediate.repository.file;

import com.example.intermediate.entity.file.MemberFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberFileRepository extends JpaRepository<MemberFile, Long> {

    @Query("select m from MemberFile m where m.name in :fileNames")
    public List<MemberFile> findAllByFileNames(List<String> fileNames);
}

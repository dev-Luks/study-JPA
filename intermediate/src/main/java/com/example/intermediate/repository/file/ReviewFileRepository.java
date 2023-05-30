package com.example.intermediate.repository.file;

import com.example.intermediate.entity.file.ReviewFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewFileRepository extends JpaRepository<ReviewFile, Long> {
//    file path를 여러 개 전달받은 뒤
//    존재하는 경로 모두 ReviewFile 전체 정보 조회

    @Query("select r from ReviewFile r where r.filePath in :filePaths")
    public List<ReviewFile> findByFilePaths(List<String> filePaths);

}

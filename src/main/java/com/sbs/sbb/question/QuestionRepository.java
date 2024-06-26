package com.sbs.sbb.question;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);


//    @Modifying : 만약 아래 쿼리가 SELECT가 아니라면 이걸 붙여야 한다.
//    nativeQuery = true 여야 MySQL 쿼리 문법 사용 가능
    @Transactional
    @Modifying
    @Query(value="ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void clearAutoIncrement();
}
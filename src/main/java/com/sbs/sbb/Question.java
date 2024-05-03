package com.sbs.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity // question 테이블을 만들어줌.
public class Question {
    @Id // Primary key 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment 적용
    private Integer id;

    @Column(length = 200)  // VARCHAR(200)
    private String subject;

    @Column(columnDefinition = "TEXT") // TEXT 개념
    private String content;

    private LocalDateTime createDate; // 날짜
}

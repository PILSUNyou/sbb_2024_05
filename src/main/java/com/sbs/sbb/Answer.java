package com.sbs.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity  // answer 테이블
public class Answer {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto increment
    private Integer id;

    @Column(columnDefinition = "TEXT") // text
    private String content;

    private LocalDateTime createDate;

    // private Integer questionId;
    @ManyToOne // 중요한 부분 !
    private Question question;
}
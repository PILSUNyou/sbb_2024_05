package com.sbs.sbb.answer;

import com.sbs.sbb.question.Question;
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

    // many = Answer , One = Question
    // 多 대 1 관계
    @ManyToOne // 필수로 적어야함 ! (중요한 부분 !)
    private Question question;
}
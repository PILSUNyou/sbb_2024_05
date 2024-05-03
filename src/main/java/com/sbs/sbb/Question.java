package com.sbs.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    // maooedBy Answer 클래스의 question 변수 이름을 적어야함.(Answer 클래스에 있는 question이랑 이름이 같아야함.)
    // CascadeType.REMOVE를 하면 Question을 삭제할 때 답변도 함께 삭제한다.
    // OneToMany는 테이블의 컬럼으로 생성되지 않는다.
    // 선택적으로 작성
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}

package com.sbs.sbb;

import com.sbs.sbb.answer.Answer;
import com.sbs.sbb.answer.AnswerRepository;
import com.sbs.sbb.question.Question;
import com.sbs.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@Rollback(true)
class SbbApplicationTests {

	@Autowired // 객체 생성을 대신 해주는 녀석
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@Transactional
	@Test
	void testJpa() {
		// 질문을 생성
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);  // 첫번째 질문 저장
//
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2);  // 두번째 질문 저장

		// findAll 사용
//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(2, all.size()); // expected는 기대값으로 Equals 했을 때 all.size의 값이 기대값과 같아야 함
//
//		Question q = all.get(0);
//		assertEquals("sbb가 무엇인가요?", q.getSubject());

		// findById 사용
		// Optional을 사용할 경우 get을 사용하여야 한다
	//		Optional<Question> oq = this.questionRepository.findById(1); // 1을 가져옴
	//		if(oq.isPresent()) {
	//			Question q = oq.get(); // 위에서 findById에 1의 값을 가져왔기 때문에 get이후 정수 기입을 안해도 된다.
	//			assertEquals("sbb가 무엇인가요?", q.getSubject());
	//		}

		// findBySubject 사용
		// Optional을 사용하지 않을 경우는 Question으로 받으면 된다.
//		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
//		assertEquals(1, q.getId());

		// findBySubjectAndContent 사용
//		Question q = this.questionRepository.findBySubjectAndContent(
//				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
//		assertEquals(1, q.getId());

		// 질문 데이터 수정하기
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		q.setSubject("수정된 제목");
//		this.questionRepository.save(q);

		// 질문 데이터 삭제하기
//		assertEquals(2, this.questionRepository.count());
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		this.questionRepository.delete(q);
//		assertEquals(1, this.questionRepository.count());

		// 질문 데이터 저장하기
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//
//		Answer a = new Answer();
//		a.setContent("네 자동으로 생성됩니다.");
//		a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);

		// 질문 데이터 조회하기
//		Optional<Answer> oa = this.answerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer a = oa.get();
//		assertEquals(2, a.getQuestion().getId());

		// 질문 데이터를 통해 답변 데이터 조회하기
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		// oq에서 데이터가 끊어지기 때문에 @Transactional을 사용하여 oq의 데이터가 유지될 수 있도록 한다.
		// 테스트 => fetch = FetchType.LAZY가 기본 타입이 때문에 데이터가 끊어진다.(@Transactional을 사용하면 데이터가 유지된다)
		// 실제 => fetch = FetchType.EAGER을 사용하여 데이터가 유지되게끔 한다.
		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
package com.sbs.sbb.question;

import com.sbs.sbb.answer.AnswerForm;
import com.sbs.sbb.user.SiteUser;
import com.sbs.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/question") // 접두어에 question을 따로 작성하지 않아도 된다.
@Controller
@RequiredArgsConstructor
//@Validated 컨트롤러에서는 이 부분이 생략이 가능하다.
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(page, null);
           model.addAttribute("paging", paging);

        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        Question q = this.questionService.getQuestion(id);

        model.addAttribute("question",q);

        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    // QuestionForm 변수는 model.addAttribute 없이 바로 뷰에서 접근할 수 있다.
    // QuestionForm questionForm 써주는 이유 : question_form.html에서 questionForm 변수가 없으면 실행이 안되기 때문에
    // 빈 객체라도 만든다.
    public String Create(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    // QuestionForm 값을 바인딩할 때 유효성 체크를 해라 !!
    // QuestionForm 변수는 model.addAttribute 없이 바로 뷰에서 접근할 수 있다.
    public String questionCreate(@Valid QuestionForm questionForm , BindingResult bindingResult, Principal principal) {
        if ( bindingResult.hasErrors() ) {
            // question_form.html 실행
            // 다시 작성하라는 의미로 응답에 폼을 실어서 보냄
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Question q = this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);

        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }

    // 질문 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    // 질문 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());

        this.questionService.vote(question, siteUser);

        return "redirect:/question/detail/%s".formatted(id);
        // return String.format("redirect:/question/detail/%s", id); 아무거나 사용해도 상관없다,
    }
}
package com.careerlog.question.service;

import com.careerlog.interview.entity.Interview;
import com.careerlog.interview.exception.InterviewNotFoundException;
import com.careerlog.interview.repository.InterviewRepository;
import com.careerlog.question.dto.InterviewQuestionCreateRequest;
import com.careerlog.question.dto.InterviewQuestionResponse;
import com.careerlog.question.entity.InterviewQuestion;
import com.careerlog.question.repository.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewQuestionService {

    private final InterviewQuestionRepository interviewQuestionRepository;
    private final InterviewRepository interviewRepository;

    @Transactional
    public InterviewQuestionResponse create(
            Long userId,
            Long interviewId,
            InterviewQuestionCreateRequest request
    ) {
        Interview interview = interviewRepository.findByIdAndUserId(interviewId, userId)
                .orElseThrow(InterviewNotFoundException::new);

        InterviewQuestion question = new InterviewQuestion(
                interview.getUser(),
                interview,
                request.questionText(),
                request.questionType(),
                request.myAnswer(),
                request.answerScore(),
                request.weakness(),
                request.improvedAnswer(),
                request.techTags(),
                request.needReview(),
                request.memo()
        );

        InterviewQuestion savedQuestion = interviewQuestionRepository.save(question);

        return InterviewQuestionResponse.from(savedQuestion);
    }
}
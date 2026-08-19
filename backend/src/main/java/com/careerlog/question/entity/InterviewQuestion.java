package com.careerlog.question.entity;

import com.careerlog.global.common.BaseTimeEntity;
import com.careerlog.interview.entity.Interview;
import com.careerlog.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "interview_questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 사용자별 질문 목록 조회와 소유권 검증을 단순하게 하기 위해 user를 직접 가집니다.
     * interview_questions.user_id는 interviews.user_id와 항상 일치해야 합니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
     * 하나의 면접에는 여러 질문이 연결될 수 있습니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false, length = 30)
    private QuestionType questionType;

    @Column(name = "my_answer", columnDefinition = "TEXT")
    private String myAnswer;

    @Column(name = "answer_score")
    private Short answerScore;

    @Column(columnDefinition = "TEXT")
    private String weakness;

    @Column(name = "improved_answer", columnDefinition = "TEXT")
    private String improvedAnswer;

    /*
     * MVP에서는 배열/JSONB 대신 문자열로 단순 저장합니다.
     * 예: "Spring,JPA,Transaction"
     */
    @Column(name = "tech_tags", columnDefinition = "TEXT")
    private String techTags;

    @Column(name = "need_review", nullable = false)
    private boolean needReview;

    @Column(columnDefinition = "TEXT")
    private String memo;

    public InterviewQuestion(
            User user,
            Interview interview,
            String questionText,
            QuestionType questionType,
            String myAnswer,
            Short answerScore,
            String weakness,
            String improvedAnswer,
            String techTags,
            Boolean needReview,
            String memo
    ) {
        this.user = user;
        this.interview = interview;
        this.questionText = questionText;
        this.questionType = questionType;
        this.myAnswer = myAnswer;
        this.answerScore = answerScore;
        this.weakness = weakness;
        this.improvedAnswer = improvedAnswer;
        this.techTags = techTags;
        this.needReview = needReview != null && needReview;
        this.memo = memo;
    }

    public void update(
            String questionText,
            QuestionType questionType,
            String myAnswer,
            Short answerScore,
            String weakness,
            String improvedAnswer,
            String techTags,
            Boolean needReview,
            String memo
    ) {
        this.questionText = questionText;
        this.questionType = questionType;
        this.myAnswer = myAnswer;
        this.answerScore = answerScore;
        this.weakness = weakness;
        this.improvedAnswer = improvedAnswer;
        this.techTags = techTags;
        this.needReview = needReview != null && needReview;
        this.memo = memo;
    }

    public void updateNeedReview(boolean needReview) {
        this.needReview = needReview;
    }
}
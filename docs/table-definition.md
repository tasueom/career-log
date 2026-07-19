# CareerLog 테이블 정의서

> CareerLog 1차 MVP 기준의 초기 데이터베이스 설계 문서입니다.  
> 개발 진행에 따라 컬럼명, 타입, 제약조건은 실제 엔티티와 함께 변경될 수 있습니다.
>
> 최종 수정일: 2026-07-19

## 1. 문서 개요

CareerLog는 사용자의 취업 지원 현황, 면접 기록, 면접 질문과 답변 회고를 관리하는 개인 취업 관리 플랫폼입니다.

### 대상 테이블

| 테이블명 | 설명 | 현재 상태 |
|---|---|---|
| `users` | 사용자 계정 정보 | 구현 |
| `applications` | 취업 지원 건 정보 | 일부 구현 |
| `refresh_tokens` | JWT Refresh Token 정보 | 설계 |
| `interviews` | 지원 건별 면접 기록 | 설계 |
| `interview_questions` | 면접 질문 및 답변 회고 | 설계 |

### 공통 규칙

- 데이터베이스는 PostgreSQL 16을 기준으로 합니다.
- 기본 키는 `BIGINT` 자동 증가 값을 사용합니다.
- Java 필드는 `camelCase`, 데이터베이스 컬럼은 `snake_case`를 사용합니다.
- 날짜와 시간은 애플리케이션에서 `LocalDateTime`으로 관리하고 PostgreSQL의 `TIMESTAMP`에 저장합니다.
- 주요 엔티티의 `created_at`, `updated_at`은 `BaseTimeEntity`와 Spring Data JPA Auditing으로 관리합니다.
- `BaseTimeEntity`는 공통 매핑 클래스이며 별도의 데이터베이스 테이블을 생성하지 않습니다.
- 열거형 값은 JPA의 `EnumType.STRING`을 사용하여 문자열로 저장합니다.
- 모든 사용자 데이터는 `user_id`를 기준으로 소유권을 검증합니다.

---

## 2. `users`

사용자 계정 정보를 저장합니다.

### 컬럼 정의

| 컬럼명 | 데이터 타입 | NULL | 키 | 기본값 | 설명 |
|---|---|---:|---|---|---|
| `id` | `BIGINT` | N | PK | 자동 증가 | 사용자 식별자 |
| `email` | `VARCHAR(255)` | N | UK | - | 로그인에 사용하는 이메일 |
| `password_hash` | `VARCHAR(255)` | N | - | - | 단방향 암호화된 비밀번호 |
| `nickname` | `VARCHAR(50)` | N | - | - | 서비스에서 표시할 사용자 이름 |
| `created_at` | `TIMESTAMP` | N | - | JPA Auditing | 생성 일시 |
| `updated_at` | `TIMESTAMP` | N | - | JPA Auditing | 마지막 수정 일시 |

### 제약조건 및 인덱스

| 구분 | 대상 | 설명 |
|---|---|---|
| Primary Key | `id` | 사용자 식별자 |
| Unique | `email` | 동일한 이메일의 중복 가입 방지 |
| Index | `email` | 로그인 시 사용자 조회 성능 개선. Unique 제약조건으로 인덱스가 생성될 수 있음 |

### 보안 정책

- 비밀번호 원문은 데이터베이스에 저장하지 않습니다.
- `password_hash`에는 BCrypt 등 단방향 해시 결과만 저장합니다.
- API 응답에 `password_hash`를 포함하지 않습니다.

---

## 3. `applications`

사용자가 관심을 갖거나 실제로 지원한 채용 건을 저장합니다.  
MVP에서는 회사와 채용 공고를 별도 테이블로 분리하지 않고 회사명과 공고 정보를 함께 관리합니다.

### 컬럼 정의

| 컬럼명 | 데이터 타입 | NULL | 키 | 기본값 | 설명 |
|---|---|---:|---|---|---|
| `id` | `BIGINT` | N | PK | 자동 증가 | 지원 건 식별자 |
| `user_id` | `BIGINT` | N | FK | - | 지원 건 소유 사용자 |
| `company_name` | `VARCHAR(100)` | N | - | - | 회사명 |
| `position_title` | `VARCHAR(100)` | N | - | - | 지원 직무 또는 채용 공고명 |
| `job_url` | `VARCHAR(500)` | Y | - | `NULL` | 채용 공고 URL |
| `status` | `VARCHAR(30)` | N | - | - | 현재 지원 상태 |
| `priority` | `VARCHAR(20)` | N | - | - | 지원 우선순위 |
| `applied_at` | `DATE` | Y | - | `NULL` | 실제 지원 일자 |
| `result_expected_at` | `DATE` | Y | - | `NULL` | 결과 발표 예상 일자 |
| `next_action` | `VARCHAR(255)` | Y | - | `NULL` | 다음에 수행할 작업 |
| `next_action_at` | `TIMESTAMP` | Y | - | `NULL` | 다음 작업 예정 일시 |
| `memo` | `TEXT` | Y | - | `NULL` | 지원 건 관련 메모 |
| `created_at` | `TIMESTAMP` | N | - | JPA Auditing | 생성 일시 |
| `updated_at` | `TIMESTAMP` | N | - | JPA Auditing | 마지막 수정 일시 |

### `status` 허용값

| 값 | 설명 |
|---|---|
| `INTERESTED` | 관심 |
| `PLANNED` | 지원 예정 |
| `APPLIED` | 지원 완료 |
| `DOCUMENT_PASSED` | 서류 합격 |
| `DOCUMENT_FAILED` | 서류 탈락 |
| `INTERVIEW_PLANNED` | 면접 예정 |
| `INTERVIEW_DONE` | 면접 완료 |
| `FINAL_PASSED` | 최종 합격 |
| `FINAL_FAILED` | 최종 탈락 |
| `ON_HOLD` | 보류 |
| `GIVEN_UP` | 포기 |

### `priority` 허용값

| 값 | 설명 |
|---|---|
| `LOW` | 낮음 |
| `MEDIUM` | 보통 |
| `HIGH` | 높음 |

### 제약조건 및 인덱스

| 구분 | 대상 | 설명 |
|---|---|---|
| Primary Key | `id` | 지원 건 식별자 |
| Foreign Key | `user_id` → `users.id` | 지원 건 소유 사용자 참조 |
| Index | `user_id` | 사용자별 지원 목록 조회 |
| Composite Index | `user_id`, `status` | 사용자별 지원 상태 필터링 |
| Index | `next_action_at` | 예정된 다음 작업 조회 및 알림 확장 |

### 삭제 정책

- 사용자가 삭제되면 해당 사용자의 지원 건도 함께 삭제하는 방식을 고려합니다.
- 초기 구현에서는 JPA 연관관계와 서비스 정책이 확정된 후 `CASCADE` 또는 명시적 삭제 중 하나를 선택합니다.

---

## 4. `refresh_tokens`

JWT 재발급과 로그아웃 처리를 위한 Refresh Token 정보를 저장합니다.

### 컬럼 정의

| 컬럼명 | 데이터 타입 | NULL | 키 | 기본값 | 설명 |
|---|---|---:|---|---|---|
| `id` | `BIGINT` | N | PK | 자동 증가 | Refresh Token 식별자 |
| `user_id` | `BIGINT` | N | FK | - | 토큰 소유 사용자 |
| `token` | `VARCHAR(512)` | N | UK | - | Refresh Token 값 |
| `expires_at` | `TIMESTAMP` | N | - | - | 토큰 만료 일시 |
| `revoked` | `BOOLEAN` | N | - | `FALSE` | 로그아웃 또는 폐기 여부 |
| `created_at` | `TIMESTAMP` | N | - | 생성 시각 | 토큰 생성 일시 |

### 제약조건 및 인덱스

| 구분 | 대상 | 설명 |
|---|---|---|
| Primary Key | `id` | Refresh Token 식별자 |
| Foreign Key | `user_id` → `users.id` | 토큰 소유 사용자 참조 |
| Unique | `token` | 동일한 토큰의 중복 저장 방지 |
| Index | `user_id` | 사용자별 토큰 조회 및 일괄 폐기 |
| Index | `expires_at` | 만료된 토큰 정리 작업 |

### 보안 고려사항

- 운영 환경에서는 Refresh Token 원문 대신 해시 값을 저장하는 방식을 검토합니다.
- 로그아웃 시 레코드를 즉시 삭제하거나 `revoked`를 `TRUE`로 변경합니다.
- Refresh Token Rotation 도입 시 기존 토큰을 폐기하고 새 토큰을 발급합니다.

---

## 5. `interviews`

하나의 지원 건에서 진행된 전화, 화상, 기술, 인성, 최종 면접 등의 기록을 저장합니다.

### 컬럼 정의

| 컬럼명 | 데이터 타입 | NULL | 키 | 기본값 | 설명 |
|---|---|---:|---|---|---|
| `id` | `BIGINT` | N | PK | 자동 증가 | 면접 식별자 |
| `user_id` | `BIGINT` | N | FK | - | 면접 기록 소유 사용자 |
| `application_id` | `BIGINT` | N | FK | - | 면접이 속한 지원 건 |
| `interview_type` | `VARCHAR(30)` | N | - | - | 면접 유형 |
| `scheduled_at` | `TIMESTAMP` | N | - | - | 면접 예정 또는 진행 일시 |
| `location` | `VARCHAR(255)` | Y | - | `NULL` | 면접 장소 또는 화상 면접 정보 |
| `interviewer_count` | `INTEGER` | Y | - | `NULL` | 면접관 수 |
| `difficulty` | `SMALLINT` | Y | - | `NULL` | 사용자가 평가한 면접 난이도 |
| `atmosphere` | `TEXT` | Y | - | `NULL` | 면접 분위기 |
| `overall_review` | `TEXT` | Y | - | `NULL` | 면접 전체 회고 |
| `result` | `VARCHAR(20)` | N | - | `PENDING` | 면접 결과 |
| `next_preparation` | `TEXT` | Y | - | `NULL` | 다음 면접을 위한 준비 내용 |
| `created_at` | `TIMESTAMP` | N | - | JPA Auditing | 생성 일시 |
| `updated_at` | `TIMESTAMP` | N | - | JPA Auditing | 마지막 수정 일시 |

### `interview_type` 허용값

| 값 | 설명 |
|---|---|
| `PHONE` | 전화면접 |
| `VIDEO` | 화상면접 |
| `OFFLINE` | 대면면접 |
| `TECHNICAL` | 기술면접 |
| `PERSONALITY` | 인성면접 |
| `ASSIGNMENT` | 과제면접 |
| `FINAL` | 최종면접 |
| `OTHER` | 기타 |

### `result` 허용값

| 값 | 설명 |
|---|---|
| `PENDING` | 결과 대기 |
| `PASSED` | 합격 |
| `FAILED` | 탈락 |
| `UNKNOWN` | 결과를 알 수 없음 |

### 제약조건 및 인덱스

| 구분 | 대상 | 설명 |
|---|---|---|
| Primary Key | `id` | 면접 식별자 |
| Foreign Key | `user_id` → `users.id` | 면접 기록 소유 사용자 참조 |
| Foreign Key | `application_id` → `applications.id` | 소속 지원 건 참조 |
| Check | `interviewer_count >= 0` | 음수 인원 방지 |
| Check | `difficulty BETWEEN 1 AND 5` | 난이도 범위 제한. 적용 여부는 구현 시 확정 |
| Index | `user_id` | 사용자별 면접 조회 |
| Index | `application_id` | 지원 건 상세의 면접 목록 조회 |
| Index | `scheduled_at` | 예정 면접 조회 및 정렬 |

### 데이터 일관성

- `interviews.user_id`는 `applications.user_id`와 일치해야 합니다.
- 면접 등록 시 현재 로그인 사용자가 해당 지원 건의 소유자인지 서비스 계층에서 검증합니다.

---

## 6. `interview_questions`

면접에서 받은 개별 질문, 당시 답변, 부족했던 점, 개선 답변과 복습 여부를 저장합니다.

### 컬럼 정의

| 컬럼명 | 데이터 타입 | NULL | 키 | 기본값 | 설명 |
|---|---|---:|---|---|---|
| `id` | `BIGINT` | N | PK | 자동 증가 | 면접 질문 식별자 |
| `user_id` | `BIGINT` | N | FK | - | 질문 기록 소유 사용자 |
| `interview_id` | `BIGINT` | N | FK | - | 질문이 속한 면접 |
| `question_text` | `TEXT` | N | - | - | 실제 면접 질문 |
| `question_type` | `VARCHAR(30)` | N | - | - | 질문 유형 |
| `my_answer` | `TEXT` | Y | - | `NULL` | 면접 당시 자신의 답변 |
| `answer_score` | `SMALLINT` | Y | - | `NULL` | 답변 자기평가 점수 |
| `weakness` | `TEXT` | Y | - | `NULL` | 답변에서 부족했던 점 |
| `improved_answer` | `TEXT` | Y | - | `NULL` | 개선한 답변 |
| `tech_tags` | `VARCHAR(500)` | Y | - | `NULL` | 기술 태그. MVP에서는 단순 문자열로 관리 |
| `need_review` | `BOOLEAN` | N | - | `FALSE` | 복습 필요 여부 |
| `memo` | `TEXT` | Y | - | `NULL` | 추가 메모 |
| `created_at` | `TIMESTAMP` | N | - | JPA Auditing | 생성 일시 |
| `updated_at` | `TIMESTAMP` | N | - | JPA Auditing | 마지막 수정 일시 |

### `question_type` 허용값

| 값 | 설명 |
|---|---|
| `TECH_CONCEPT` | 기술 개념 |
| `PROJECT_EXPERIENCE` | 프로젝트 경험 |
| `PROBLEM_SOLVING` | 문제 해결 경험 |
| `COLLABORATION` | 협업 경험 |
| `PERSONALITY` | 인성 |
| `COMPANY_JOB` | 회사 및 직무 이해 |
| `CODING` | 코딩 및 알고리즘 |
| `OTHER` | 기타 |

### `answer_score` 기준

| 점수 | 설명 |
|---:|---|
| `1` | 거의 대답하지 못함 |
| `2` | 일부만 대답함 |
| `3` | 기본 내용은 대답함 |
| `4` | 꽤 잘 대답함 |
| `5` | 자신 있게 대답함 |

### 제약조건 및 인덱스

| 구분 | 대상 | 설명 |
|---|---|---|
| Primary Key | `id` | 면접 질문 식별자 |
| Foreign Key | `user_id` → `users.id` | 질문 기록 소유 사용자 참조 |
| Foreign Key | `interview_id` → `interviews.id` | 소속 면접 참조 |
| Check | `answer_score BETWEEN 1 AND 5` | 답변 점수 범위 제한 |
| Index | `user_id` | 사용자별 질문 조회 |
| Index | `interview_id` | 면접 상세의 질문 목록 조회 |
| Composite Index | `user_id`, `need_review` | 사용자별 복습 대상 질문 조회 |
| Composite Index | `user_id`, `question_type` | 질문 유형 필터링 |

### 데이터 일관성

- `interview_questions.user_id`는 `interviews.user_id`와 일치해야 합니다.
- 질문 등록 시 현재 로그인 사용자가 해당 면접과 지원 건의 소유자인지 검증합니다.
- `tech_tags`는 MVP에서 단순 문자열로 시작하고, 태그 검색 요구가 커지면 별도 태그 테이블이나 PostgreSQL 배열·JSONB 구조로 변경할 수 있습니다.

---

## 7. 테이블 관계 요약

| 부모 테이블 | 자식 테이블 | 관계 | 설명 |
|---|---|---|---|
| `users` | `applications` | 1:N | 한 사용자는 여러 지원 건을 등록할 수 있음 |
| `users` | `refresh_tokens` | 1:N | 한 사용자는 여러 로그인 세션을 가질 수 있음 |
| `users` | `interviews` | 1:N | 한 사용자는 여러 면접 기록을 소유할 수 있음 |
| `applications` | `interviews` | 1:N | 하나의 지원 건에는 여러 면접이 진행될 수 있음 |
| `users` | `interview_questions` | 1:N | 한 사용자는 여러 질문 기록을 소유할 수 있음 |
| `interviews` | `interview_questions` | 1:N | 하나의 면접에는 여러 질문이 포함될 수 있음 |

---

## 8. 향후 변경 가능 항목

다음 항목은 MVP 이후 별도 테이블로 분리하거나 확장할 수 있습니다.

| 항목 | 현재 설계 | 향후 확장 방향 |
|---|---|---|
| 회사 정보 | `applications.company_name` | `companies` 테이블 분리 |
| 채용 공고 | `applications.position_title`, `job_url` | `job_postings` 테이블 분리 |
| 상태 변경 이력 | `applications.status`에 현재 상태만 저장 | `application_status_histories` 추가 |
| 기술 태그 | `interview_questions.tech_tags` 문자열 | `tags`, `question_tags` 분리 |
| 학습 노트 | 질문의 개선 답변에 포함 | `notes` 테이블 추가 |
| 알림 | 저장하지 않음 | n8n 또는 별도 알림 테이블 연계 |

---

## 9. 구현 시 확인할 사항

- 문서의 컬럼명과 실제 JPA `@Column` 이름이 일치하는지 확인합니다.
- 실제 엔티티에서 사용하는 문자열 길이에 맞춰 `VARCHAR` 길이를 조정합니다.
- JPA가 자동 생성한 FK와 인덱스 이름은 환경에 따라 달라질 수 있습니다.
- 초기 개발에서는 `ddl-auto: update`를 사용하지만, 구조가 안정화되면 Flyway 도입을 검토합니다.
- 사용자 탈퇴 및 상위 데이터 삭제 시 자식 데이터 처리 방식을 구현 전에 확정합니다.
- 공개 저장소에는 실제 비밀번호, JWT Secret, DB 접속 비밀번호, 운영 환경 변수를 커밋하지 않습니다.

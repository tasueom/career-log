# CareerLog

취업 지원 현황과 면접 질문 회고를 관리하는 개인 취업 관리 플랫폼입니다.

## 주요 기능

- 회원가입·로그인·로그아웃과 JWT Access/Refresh Token 인증
- 내 정보 조회
- 지원 건 CRUD, 상태 변경, 상태 필터 및 회사명·직무명 검색
- 면접 기록 CRUD와 사용자별 소유권 검증
- 면접 질문·답변 회고 CRUD와 복습 대상 관리
- 지원 상태 및 복습 필요 질문 수 대시보드 요약
- 일관된 400·401·404 오류 응답

## 기술 스택

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- Swagger/OpenAPI

### Frontend
- Vite
- React
- TypeScript

### Future
- n8n 알림 자동화
- Temporal 기반 면접 준비 워크플로
- Oracle Cloud 배포

## 프로젝트 문서

프로젝트 설계와 데이터 구조는 아래 문서를 참고하실 수 있습니다.

- [요구사항 정의서](docs/requirements.md)
- [테이블 정의서](docs/table-definition.md)
- [ERD](docs/erd.md)

## 로컬 실행

필요 환경은 Java 17과 Docker입니다.

1. 저장소 루트에서 `.env.example`을 `.env`로 복사하고 PostgreSQL 값을 설정합니다.
2. `docker compose up -d`로 PostgreSQL 16을 실행합니다.
3. 애플리케이션 실행 환경에 `JWT_SECRET`과 데이터베이스 접속 정보를 설정합니다. `SPRING_DATASOURCE_USERNAME`과 `SPRING_DATASOURCE_PASSWORD`는 Docker의 `POSTGRES_USER`, `POSTGRES_PASSWORD`와 같아야 합니다.
4. `backend` 디렉터리에서 Windows는 `.\gradlew.bat bootRun`, macOS/Linux는 `./gradlew bootRun`을 실행합니다.

### 환경변수

| 변수 | 용도 | 기본값 또는 비고 |
| :--- | :--- | :--- |
| `POSTGRES_DB` | Docker PostgreSQL 데이터베이스명 | `.env`에서 설정 |
| `POSTGRES_USER` | Docker PostgreSQL 사용자명 | `.env`에서 설정 |
| `POSTGRES_PASSWORD` | Docker PostgreSQL 비밀번호 | `.env`에서 설정 |
| `POSTGRES_PORT` | 호스트 PostgreSQL 포트 | `.env`에서 설정 |
| `SPRING_DATASOURCE_URL` | Spring Boot 데이터베이스 URL | `jdbc:postgresql://localhost:5432/career_log` |
| `SPRING_DATASOURCE_USERNAME` | Spring Boot 데이터베이스 사용자명 | `career` |
| `SPRING_DATASOURCE_PASSWORD` | Spring Boot 데이터베이스 비밀번호 | `career1234` |
| `JWT_SECRET` | JWT 서명 키 | 필수, 충분히 긴 임의 문자열 사용 |
| `JWT_ACCESS_TOKEN_EXPIRATION_MS` | Access Token 만료 시간(ms) | `900000` |
| `JWT_REFRESH_TOKEN_EXPIRATION_MS` | Refresh Token 만료 시간(ms) | `1209600000` |

저장소 루트의 `.env`는 Docker Compose가 사용합니다. Spring Boot 실행 프로세스에는 `SPRING_DATASOURCE_*`와 `JWT_*` 환경변수를 별도로 전달해야 합니다.

## API 문서

본 프로젝트는 Swagger/OpenAPI를 활용해 백엔드 API 명세를 확인할 수 있도록 구성했습니다. 현재 구현된 백엔드 API는 문서화되어 있으며, 이후 API를 순차적으로 추가할 예정입니다.

현재는 로컬 개발 환경 기준으로 문서 경로를 제공합니다.  
추후 배포 환경 구성 후에는 배포 도메인 기준의 Swagger 문서 URL로 갱신할 예정입니다.

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Docs: http://localhost:8080/v3/api-docs

## 주요 API

`인증 필요` API는 `Authorization: Bearer <Access Token>` 헤더가 필요합니다. Refresh Token은 Access Token 재발급과 로그아웃 요청 본문에서만 사용합니다.

| 기능 | Method | 경로 | 인증 |
| :--- | :---: | :--- | :---: |
| 회원가입 | `POST` | `/api/auth/signup` | 불필요 |
| 로그인 | `POST` | `/api/auth/login` | 불필요 |
| Access Token 재발급 | `POST` | `/api/auth/refresh` | 불필요 |
| 로그아웃 | `POST` | `/api/auth/logout` | 불필요 |
| 내 정보 조회 | `GET` | `/api/me` | 필요 |
| 지원 건 등록·목록 | `POST`, `GET` | `/api/applications` | 필요 |
| 지원 건 상세·수정·삭제 | `GET`, `PUT`, `DELETE` | `/api/applications/{applicationId}` | 필요 |
| 지원 상태 변경 | `PATCH` | `/api/applications/{applicationId}/status` | 필요 |
| 면접 등록·목록 | `POST`, `GET` | `/api/applications/{applicationId}/interviews` | 필요 |
| 면접 상세·수정·삭제 | `GET`, `PUT`, `DELETE` | `/api/interviews/{interviewId}` | 필요 |
| 질문 등록·목록 | `POST`, `GET` | `/api/interviews/{interviewId}/questions` | 필요 |
| 질문 상세·수정·삭제 | `GET`, `PUT`, `DELETE` | `/api/questions/{questionId}` | 필요 |
| 복습 필요 여부 변경 | `PATCH` | `/api/questions/{questionId}/need-review` | 필요 |
| 복습 대상 목록 | `GET` | `/api/review-targets` | 필요 |
| 대시보드 요약 | `GET` | `/api/dashboard/summary` | 필요 |
| 상태 확인 | `GET` | `/api/health` | 필요 |

지원 건 목록의 `GET /api/applications`는 선택 query parameter인 `status`와 `keyword`를 지원합니다. `status`는 `ApplicationStatus` 값, `keyword`는 회사명 또는 직무명 검색어입니다.

## 개발 현황

| 분류 | 기능 및 작업 내용 | 상태 |
| :--- | :--- | :---: |
| **인프라/DB** | PostgreSQL Docker 개발 환경 구성 | ✅ 완료 |
| **인프라/DB** | Spring Boot 데이터베이스 연결 | ✅ 완료 |
| **회원** | User 엔티티 및 JPA Auditing 구성 | ✅ 완료 |
| **지원 관리** | 지원 건 등록 / 목록 조회·필터·검색 / 상세 조회 | ✅ 완료 |
| **지원 관리** | 지원 건 수정 / 삭제 / 상태 변경 | ✅ 완료 |
| **공통** | 전역 예외 처리 | ✅ 완료 |
| **인증** | 회원가입 및 로그인 / JWT 인증/인가 / 내 정보 조회 | ✅ 완료 |
| **면접** | 면접 기록 관리 | ✅ 완료 |
| **면접** | 면접 질문 회고 관리 | ✅ 완료 |
| **대시보드** | 지원 현황 및 복습 필요 질문 요약 API | ✅ 완료 |
| **프론트** | 프론트엔드 연동 | ⬜ 대기 |

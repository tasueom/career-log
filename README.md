# CareerLog

취업 지원 현황과 면접 질문 회고를 관리하는 개인 취업 관리 플랫폼입니다.

## 주요 기능

- JWT 기반 회원가입/로그인
- 지원 공고 및 지원 상태 관리
- 면접 일정 및 회고 관리
- 면접 질문, 내 답변, 답변 점수 기록
- 복습 필요 질문 관리

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

## API 문서

본 프로젝트는 Swagger/OpenAPI를 활용해 백엔드 API 명세를 확인할 수 있도록 구성했습니다. 현재는 지원 건 API 문서화를 완료했으며, 이후 구현되는 API를 순차적으로 추가할 예정입니다.

현재는 로컬 개발 환경 기준으로 문서 경로를 제공합니다.  
추후 배포 환경 구성 후에는 배포 도메인 기준의 Swagger 문서 URL로 갱신할 예정입니다.

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Docs: http://localhost:8080/v3/api-docs

## 개발 현황

| 분류 | 기능 및 작업 내용 | 상태 |
| :--- | :--- | :---: |
| **인프라/DB** | PostgreSQL Docker 개발 환경 구성 | ✅ 완료 |
| **인프라/DB** | Spring Boot 데이터베이스 연결 | ✅ 완료 |
| **회원** | User 엔티티 및 JPA Auditing 구성 | ✅ 완료 |
| **지원 관리** | 지원 건 등록 / 목록 조회 / 상세 조회 | ✅ 완료 |
| **지원 관리** | 지원 건 수정 / 삭제 / 상태 변경 | ✅ 완료 |
| **공통** | 전역 예외 처리 (지원 건 미존재 / 요청 검증 오류) | 🟡 일부 구현 |
| **인증** | 회원가입 및 로그인 / JWT 인증/인가 | ⬜ 대기 |
| **면접** | 면접 기록 관리 / 면접 질문 회고 관리 | ⬜ 대기 |
| **프론트** | 프론트엔드 연동 | ⬜ 대기 |

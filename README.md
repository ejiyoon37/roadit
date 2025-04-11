#  Roadit

Spring Boot 기반  플랫폼입니다.  
Docker 개발 환경과 AWS 인프라를 활용하였습니다.

---

## 🚀 기술 스택

- **Backend**: Spring Boot (Gradle)
- **Infra**: Docker, AWS RDS (PostgreSQL), AWS SES

---

## 🔐 주요 기능

- 이메일 인증 시스템 (AWS SES 기반 인증번호 발송 및 검증)
- 사용자 정보 관리 (공용 PostgreSQL 데이터베이스 사용)
- Docker 기반의 로컬 실행 환경 구축

---

## ⚙️ 실행 방법

```bash
cd spring
docker compose up --build
```

## ⚙️ 테스트 방법

```bash
cd spring
docker-compose build backend-test
docker-compose run --rm backend-test
```
---

## 📄 문서 Wiki 링크

- [📘 API 명세서](https://github.com/ejiyoon37/roadit/wiki/API-%EB%AA%85%EC%84%B8%EC%84%9C)

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
docker build -t roadit-backend .
docker run -p 8080:8080 roadit-backend
```

---

## 📄 문서 Wiki 링크

- [📘 API 명세서](https://github.com/사용자명/저장소명/wiki/API-명세서)
- [📦 환경변수 설정 가이드](https://github.com/사용자명/저장소명/wiki/환경변수-설정)
- [📬 이메일 인증 시스템 설명](https://github.com/사용자명/저장소명/wiki/이메일-인증-시스템)


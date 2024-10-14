## ORM과 JPA

### ORM이란?

- ORM은 SQL을 사용하지 않고 데이터베이스를 관리할 수 있는 도구
- ORM은 데이터베이스의 테이블을 자바 클래스로 만들어 관리

```sql
-- Sql
insert into question (id, subject, content) values (1, '안녕하세요', '가입 인사드립니다 ^^');
insert into question (id, subject, content) values (2, '질문 있습니다', 'ORM이 궁금합니다');
```

```java
// Java
Question q1 = new Question();
q1.setId(1);
q1.setSubject("안녕하세요");
q1.setContent("가입 인사드립니다 ^^");
this.questionRepository.save(q1);

Question q2 = new Question();
q2.setId(2); 
q2.setSubject("질문 있습니다"); 
q2.setContent("ORM이 궁금합니다"); 
this.questionRepository.save(q2);
```

### ORM의 장점

- 별도의 SQL 문법을 배우지 않아도 데이터베이스를 사용할 수 있다
- MySQL, 오라클 DB, MS SQL과 같은 DBMS의 종류에 관계 없이 일관된 자바 코드를 사용할 수 있어서 프로그램을 유지·보수하기가 편리
- 코드 내부에서 안정적인 SQL 쿼리문을 자동으로 생성해 주므로, 개발자가 달라도 통일된 쿼리문을 작성할 수 있고, 오류 발생률도 줄일 수 있다

### JPA란?

- 스프링 부트는 JPA(Java Persistence API)를 사용하여 데이터베이스를 관리한다.
- 스프링 부트는 JPA를 ORM(Object-Relational Mapping) 기술의 표준으로 사용한다.
- JPA는 인터페이스 모음이므로, 이 인터페이스를 구현한 실제 클래스가 필요한데 대표적으로 하이버네이트(Hibernate)가 있다.
- 하이버네이트는 JPA의 인터페이스를 구현한 실제 클래스이자 자바의 ORM 프레임워크로, 스프링 부트에서 데이터베이스를 관리하기 쉽게 도와준다.
- 인터페이스(interface)란 클래스가 구현해야 하는 메서드 목록을 정의한 틀이다.

--------------------------------------
## build.gradle

### H2 데이터베이스 설치
```groovy
dependencies {
  runtimeOnly 'com.h2database:h2'
}
```

### JPA 환경 설정
```groovy
dependencies { 
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa' 
}
```

#### * implementation이란?
- build.gradle 파일에서 작성한 implementation은 필요한 라이브러리 설치를 위해 가장 일반적으로 사용하는 설정이다.
- implementation은 해당 라이브러리가 변경되더라도 이 라이브러리와 연관된 모든 모듈을 컴파일하지 않고 변경된 내용과 관련이 있는 모듈만 컴파일하므로 프로젝트를 리빌드(rebuild)하는 속도가 빠르다.

### JUnit 설치
```groovy
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

### template 엔진 설치 (Thymeleaf)
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect'
}
```

--------------------------------------
## application.properties

### H2 데이터베이스 사용
```yaml
# DATABASE
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:~/local
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```
- spring.h2.console.enabled: H2 콘솔에 접속할 것인지를 묻는 항목이다. 여기서는 true로 설정한다. [H2 콘솔](http://localhost:8080/h2-console)은 H2 데이터베이스를 웹 UI로 보여 준다. 
- spring.h2.console.path: [H2 콘솔](http://localhost:8080/h2-console)로 접속하기 위한 URL 경로이다.
- spring.datasource.url: 데이터베이스에 접속하기 위한 경로. 사용자의 홈 디렉터리(코드에서 ~에 해당하는 경로) 아래에 H2 데이터베이스 파일로 local.mv.db라는 파일을 생성
- spring.datasource.driverClassName: 데이터베이스에 접속할 때 사용하는 드라이버 클래스명이다.
- spring.datasource.username: 데이터베이스의 사용자명이다(사용자명으로 기본값인 sa로 설정한다.).
- spring.datasource.password: 데이터베이스의 비밀번호이다(여기서는 로컬에서 개발 용도로만 사용하므로 비밀번호를 설정하지 않고 비워 두었다.).

### JPA 사용

```yaml
# JPA
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

# LOG
logging.level.org.hibernate.type.descriptor.sql=trace
```
- spring.jpa.properties.hibernate.dialect: 스프링 부트와 하이버네이트를 함께 사용할 때 필요한 설정 항목이다. 표준 SQL이 아닌 하이버네이트만의 SQL을 사용할 때 필요한 항목으로 하이버네이트의 org.hibernate.dialect.H2Dialect 클래스를 설정했다.
- spring.jpa.hibernate.ddl-auto: 엔티티를 기준으로 데이터의 테이블을 생성하는 규칙을 설정한다.
- spring.jpa.properties.hibernate.show_sql : 실행창에 JPA 구현제 Hibernate가 만들어 준 sql을 보여준다.
- spring.jpa.properties.hibernate.format_sql : sql 예쁘게 보기
- spring.jpa.properties.hibernate.use_sql_comments : sql에 추가적인 주석 표시하기
- logging.level.org.hibernate.type.descriptor.sql : ?에 어떤 값이 들어갔는지 확인 하기

#### spring.jpa.hibernate.ddl-auto 규칙
- none: 엔티티가 변경되더라도 데이터베이스를 변경하지 않는다.
- update: 엔티티의 변경된 부분만 데이터베이스에 적용한다.
- validate: 엔티티와 테이블 간에 차이점이 있는지 검사만 한다.
- create: 스프링 부트 서버를 시작할 때 테이블을 모두 삭제한 후 다시 생성한다.
- create-drop: create와 동일하지만 스프링 부트 서버를 종료할 때에도 테이블을 모두 삭제한다.
- 개발 환경에서는 보통 update 모드를 사용하고, 운영 환경에서는 none 또는 validate를 주로 사용한다.

--------------------------------------
## 엔티티 (Entity)와 리포지터리(Repository)

### 엔티티는?
- 데이터베이스를 관리하는데 사용하는 ORM의 자바 **클래스**를 엔티티(entity)라고 한다.
- 엔티티는 데이터베이스의 **테이블**과 매핑되는 자바 클래스를 말한다.
- 엔티티를 모델 또는 도메인 모델 이라고도 한다.

### 리포지토리는?
- 엔티티가 데이터베이스 테이블을 생성했다면, 리포지터리는 이와 같이 생성된 데이터베이스 테이블의 데이터들을 저장, 조회, 수정, 삭제 등을 할 수 있도록 도와주는 인터페이스이다.
- 리포지터리는 테이블에 접근하고, 데이터를 관리하는 메서드(예를 들어 findAll, save 등)를 제공한다.

--------------------------------------
## template 사용 (Thymeleaf)

### 분기문
```thymeleafexpressions
th:if="${question != null}"
```
- question 객체가 null이 아닌 경우에만 이 속성을 포함한 요소가 표시 됨

### 반복문
```thymeleafexpressions
th:each="question : ${questionList}"
or
th:each="question, loop : ${questionList}"
```
- loop.index: 루프의 순서(루프의 반복 순서, 0부터 1씩 증가)
- loop.count: 루프의 순서(루프의 반복 순서, 1부터 1씩 증가)
- loop.size: 반복 객체의 요소 개수(예를 들어 questionList의 요소 개수)
- loop.first: 루프의 첫 번째 순서인 경우 true
- loop.last: 루프의 마지막 순서인 경우 true
- loop.odd: 루프의 홀수 번째 순서인 경우 true
- loop.even: 루프의 짝수 번째 순서인 경우 true
- loop.current: 현재 대입된 객체(여기서는 question과 동일)

### 텍스트 속성
```thymeleafexpressions
th:text="${question.subject}"
```
- 텍스트는 th:text 속성 대신에 다음처럼 대괄호를 사용하여 값을 직접 출력할 수 있다.
```html
<tr th:each="question : ${questionList}">
    <td>[[${question.subject}]]</td>
    <td>[[${question.createDate}]]</td>
</tr>
```
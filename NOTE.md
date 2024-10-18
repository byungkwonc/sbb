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

### Spring Boot Validation 라이브러리 설치
```groovy
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-validation'
}
```

### Spring Security
```groovy
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-security'
  implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
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
## 서비스

### 서비스란?
- 서비스(service)는 간단히 말해 스프링에서 데이터 처리를 위해 작성하는 클래스
- 컨트롤러에서 리포지터리를 직접 호출 할 수 있지만, 규모있는 서비스의 경우 중간에 서비스를 두어 테이터를 처리한다

### 서비스가 필요한 이유는?
- 복잡한 코드를 모듈화할 수 있다
  - 예를 들어 A라는 컨트롤러가 어떤 기능을 수행하기 위해 C라는 리포지터리의 메서드 a, b, c를 순서대로 실행해야 한다고 가정해 보자. 그리고 B라는 컨트롤러도 A 컨트롤러와 동일한 기능을 수행해야 한다면 A, B 컨트롤러가 C 리포지터리의 메서드 a, b, c를 호출해 사용하는 중복된 코드를 가지게 된다. 이런 경우 C 리포지터리의 a, b, c 메서드를 호출하는 기능을 서비스로 만들고 컨트롤러에서 이 서비스를 호출하여 사용할 수 있다. 즉, 서비스를 사용하면 이와 같은 모듈화가 가능하다.
- 엔티티 객체를 DTO 객체로 변환할 수 있다
  - 우리가 앞에서 작성한 Question, Answer 클래스는 모두 엔티티 클래스이다. 엔티티 클래스는 데이터베이스와 직접 맞닿아 있는 클래스이므로 컨트롤러 또는 타임리프와 같은 템플릿 엔진에 전달해 사용하는 것은 좋지 않다. 왜냐하면 엔티티 객체에는 민감한 데이터가 포함될 수 있는데, 타임리프에서 엔티티 객체를 직접 사용하면 민감한 데이터가 노출될 위험이 있기 때문이다.
  - 이러한 이유로 Question, Answer 같은 엔티티 클래스는 컨트롤러에서 사용하지 않도록 설계하는 것이 좋다. 그래서 Question, Answer를 대신해 사용할 DTO (Data Transfer Object) 클래스가 필요하다. 그리고 Question, Answer 등의 엔티티 객체를 DTO 객체로 변환하는 작업도 필요하다. 그러면 엔티티 객체를 DTO 객체로 변환하는 일은 어디서 처리해야 할까? 이때도 서비스가 필요하다. 서비스는 컨트롤러와 리포지터리의 중간에서 엔티티 객체와 DTO 객체를 서로 변환하여 양방향에 전달하는 역할을 한다.

--------------------------------------
## 자료구조

### Collection(컬렉션)
- 데이터의 집합,그룹
- JCF(Java Collection Framework)는 다수의 데이터를 쉽고 효과적으로 처리할 수 있는 표준화된 방법을 제공하는 클래스의 집합
- 주요 인터페이스 : List, Set, Map

### List
- 입력 순서를 유지하며, 데이터의 중복을 허용
- 인덱스를 통해 저장 데이터에 접근이 가능
- 구현체
  - ArrayList : 단반향 포인터 구조 데이터 순차적 접근(조회)가 빠름
  - LinkedList : 양방향 포인터 구조 데이터 삽입, 삭제가 빠름

### Set
- 입력 순서를 유지하지 않으며, 데이터의 중복 허용하지 않음
- 데이터에 null 입력 가능하나, 한 번만 저장하고 중복 저장을 허용하지 않음
- 인덱스가 따로 존재하지 않기 때문에 Iterator를 사용하여 조회
- 구현체
  - HashSet : 입력 순서를 보장하지 않으며, 데이터의 중복을 허용하지 않음
  - LinkedHashSet : 입력 순서를 보장하며, 데이터의 중복을 허용하지 않음
  - TreeSet : (default) 입력한 데이터의 크기가 비교 가능한 경우 오름차순으로 정렬되며 데이터의 중복을 허용하지 않음. 입력하는 데이터가 사용자 정의 객체인 경우 Comparable을 구현하여 정렬 기준 설정 가능

### Map
- Key&Value 구조
- Key(키)는 입력 순서를 유지하지 않으며, 중복을 허용하지 않음, Value(값)는 중복을 허용
- 인덱스가 따로 존재하지 않기 때문에 Iterator를 사용하여 조회
- 구현체
  - HashMap : Key(키)에 대한 입력 순서를 보장하지 않으며, 중복 Key(키)를 허용하지 않음
  - LinkedHashMap : Key(키)에 대한 입력 순서를 보장하며, 중복 Key(키)를 허용하지 않음
  - TreeMap : 레드-블랙 트리(Red-Black Tree)를 기반으로 Key&Value를 저장. (default) 입력한 Key(키)데이터의 크기가 비교 가능한 경우 오름차순으로 정렬되며, 중복 Key(키)를 허용하지 않음. 입력하는 데이터가 사용자 정의 객체인 경우 Comparable을 구현하여, 정렬 기준 설정 가능

--------------------------------------
## 의존성 주입 DI(Dependency Injection)

### 생성자 주입(Constructor Injection)
- 스프링 팀에서도 권장하는 방식
- 스프링 프레임워크 4.3 버전부터는 의존성 주입으로부터 클래스를 완벽하게 분리할 수 있다. 단일 생성자인 경우에는 @Autowired 어노테이션 조차 붙이지 않아도 되지만 생성자가 2개 이상인 경우에는 생성자에 어노테이션을 붙여주어야 한다.
- 생성자로 객체를 생성하는 시점에 필요한 빈을 주입한다. 먼저 생성자의 인자에 사용되는 빈을 찾거나 빈 팩터리에서 만든다. 그 후에 찾은 인자 빈으로 주입하려는 빈의 생성자를 호출한다. 즉, 먼저 빈을 생성하지 않는다.
```java
@Component
public class MadExample {

    // final로 선언할 수 있는 보너스
    private final HelloService helloService;

    // 단일 생성자인 경우는 추가적인 어노테이션이 필요 없다.
    public MadExample(HelloService helloService) {
        this.helloService = helloService;
    }
}
```

### 필드 주입(Field Injection)
- 필드에 @Autowired 어노테이션을 붙여주면 자동으로 의존성이 주입된다. 편리.
- 수정자 주입 방법과 동일하게 먼저 빈을 생성한 후에 어노테이션이 붙은 필드에 해당하는 빈을 찾아서 주입하는 방법이다. 그러니까, 먼저 빈을 생성한 후에 필드에 대해서 주입한다.
```java
@Component
public class MadExample {

    @Autowired
    private HelloService helloService;
}
```

### 수정자 주입(Setter Injection)
- 꼭 setter 메서드일 필요는 없다. 메서드 이름이 수정자 네이밍 패턴(setXXXX)이 아니어도 동일한 기능을 하면 된다.
- 우선 주입(inject) 받으려는 빈의 생성자를 호출하여 빈을 찾거나 빈 팩터리에 등록한다. 그 후에 생성자 인자에 사용하는 빈을 찾거나 만든다. 그 이후에 주입하려는 빈 객체의 수정자를 호출하여 주입한다.
```java
@Component
public class MadExample {

    private HelloService helloService;

    @Autowired
    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }
}
```

### Remove "field injection" and use "constructor injection" instead 인텔리제이 경고 메시지
- 순환 참조 방지
  - 순환 참조는 생성자 주입에서만 문제가 된다. 객체 생성 시점에 빈을 주입하기 때문에 서로 참조하는 객체가 생성되지 않은 상태에서 그 빈을 참조하기 때문에 오류가 발생
  - 순환 참조가 있는 객체 설계는 잘못된 설계이기 때문에 생성자 주입을 사용하여 순환 참조되는 설계를 사전에 막아야 한다.
```java
// 첫번째 class
@Service
public class MadPlayService {

    // 순환 참조
    @Autowired
    private MadLifeService madLifeService;

    public void sayMadPlay() {
        madLifeService.sayMadLife();
    }
}
// 두번째 class
@Service
public class MadLifeService {
    
    // 순환 참조
    @Autowired
    private MadPlayService madPlayService;

    public void sayMadLife() {
        madPlayService.sayMadPlay();
    }
}
// 실행 : CommandLineRunner
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  @Autowired
  private MadLifeService madLifeService;
  @Autowired
  private MadPlayService madPlayService;

  @Override
  public void run(String... args) {
    madPlayService.sayMadPlay();
    madLifeService.sayMadLife();
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
// 실행결과 : run이 수행 되면서 오류와 함께 종료
java.lang.StackOverflowError: null
    at com.example.demo.GreetService.sayGreet(GreetService.java:12) ~[classes/:na]
    at com.example.demo.HelloService.sayHello(HelloService.java:12) ~[classes/:na]
    at com.example.demo.GreetService.sayGreet(GreetService.java:12) ~[classes/:na]
    at com.example.demo.HelloService.sayHello(HelloService.java:12) ~[classes/:na]
    at com.example.demo.GreetService.sayGreet(GreetService.java:12) ~[classes/:na]
```

```java
// 생성자 주입으로 코드 변경

// 첫번째 class
@Service
public class MadPlayService {
  private final MadLifeService madLifeService;

  public MadPlayService(MadLifeService madLifeService) {
    this.madLifeService = madLifeService;
  }

  // 생략
}
// 두번째 class
@Service
public class MadLifeService {
  private final MadPlayService madPlayService;

  public MadLifeService(MadPlayService madPlayService) {
    this.madPlayService = madPlayService;
  }

  // 생략
}
// 실행결과 : BeanCurrentlyInCreationException이 발생하며 애플리케이션이 구동조차 되지 않는다
Description:
The dependencies of some of the beans in the application context form a cycle:
        ┌─────┐
        |  madLifeService defined in file [~~~/MadLifeService.class]
        ↑     ↓
        |  madPlayService defined in file [~~~/MadPlayService.class]
        └─────┘
```
- 테스트에 용이
  - 생성자 주입을 사용하게 되면 테스트 코드를 조금 더 편리하게 작성할 수 있다.
  - DI의 핵심은 관리되는 클래스가 DI 컨테이너에 의존성이 없어야 한다는 것이다. 즉, 독립적으로 인스턴스화가 가능한 POJO(Plain Old Java Ojbect) 여야 한다는 것이다.
  - DI 컨테이너를 사용하지 않고서도 단위 테스트에서 인스턴스화할 수 있어야 한다.
  - 생성자 주입을 사용하면 테스트가 조금 더 편리하다고 생각하면 좋을 것 같다. Mockito(@Mock과 @spy 같은)를 적절히 섞어서 테스트를 할 수 있지만 생성자 주입을 사용한 경우 매우 간단한 코드를 만들 수 있다.
```java
SomeObject someObject = new SomeObject();
MadComponent madComponent = new MadComponent(someObject);
madComponent.someMadPlay();
```
- 코드 냄새를 없앤다
  - 한 개의 컴포넌트가 수많은 의존성을 갖는 역할을 할 수 있다. 생성자 주입을 사용하게 되는 경우 생성자의 인자가 많아짐에 따라 복잡한 코드가 됨을 쉽게 알 수 있고 리팩토링하여 역할을 분리하는 등과 같은 코드의 품질을 높이는 활동의 필요성을 더 쉽게 알 수 있다.
- 불변성(Immutability)
  - 필드 주입과 수정자 주입은 해당 필드를 final로 선언할 수 없다. 따라서 초기화 후에 빈 객체가 변경될 수 있지만 생성자 주입의 경우는 다르다. 필드를 final로 선언할 수 있다.
  - 물론 런타임 환경에서 객체를 변경하는 경우가 있을까 싶지만 이로 인해 발생할 수 있는 오류를 사전에 미리 방지할 수 있다.
```java
@Service
public class MadPlayService {
    private final MadPlayRepository madPlayRepository;

    public MadPlayService(MadPlayRepository madPlayRepository) {
        this.madPlayRepository = madPlayRepository;
    }
}
```
- 오류를 방지할 수 있다
  - 스프링 레퍼런스에는 강제화되는 의존성의 경우는 생성자 주입 형태를 사용하고 선택적인 경우에는 수정자 주입 형태를 사용하는 것을 권장한다.
  - 불변 객체나 null이 아님을 보장할 때는 반드시 생성자 주입을 사용해야 한다.
```java
@Service
public class MadPlayService {
    @Autowired
    private MadPlayRepository madPlayRepository;

    public void someMethod() {
        // final이 아니기 때문에 값을 변경할 수 있다.
        madPlayRepository = null;
        madPlayRepository.call();
    }
}
// 필드 주입을 사용했기 때문에 선언된 필드는 final이 아니다. 따라서 런타임 시점에 변경할 수 있다.
// null을 참조하도록 변경했기 때문에 이어지는 코드에서 NullPointerException이 발생할 것이다.
// 하지만 생성자 주입을 사용한다면 이와 같은 상황을 컴파일 시점에 방지할 수 있다.

@Service
public class MadPlayService {
    private final MadPlayRepository madPlayRepository;

    public MadPlayService(MadPlayRepository madPlayRepository) {
        this.madPlayRepository = madPlayRepository;
    }

    public void someMethod() {
        // cannot assign a value to final variable
        madPlayRepository = null;
    }
}
```

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
- 마크다운으로 변환된 HTML 문서를 제대로 표시하려면 이스케이프 처리를 하지 않고 출력하는 th:utext를 사용
```thymeleafexpressions
th:utext="${@commonUtil.markdown(answer.content)}"
```


--------------------------------------
## bootstrap class

| **부트스트랩 클래스**                    | **설명**                        |
|----------------------------------|-------------------------------|
| ```card```, ```card-body```, ```card-text``` | card 컴포넌트를 적용하는 클래스들이다.       |
| ```badge```               | badge 컴포넌트를 적용하는 클래스이다.       |
| ```form-control```                     | 텍스트 창에 form 컴포넌트를 적용하는 클래스이다. |
| ```border-bottom```                    | 아래 방향 테두리 선을 만드는 클래스이다.       |
| ```my-3```                             | 상하 마진값으로 3을 지정하는 클래스이다.       |
| ```py-2```                             | 상하 패딩값으로 2를 지정하는 클래스이다.       |
| ```p-2```                              | 상하좌우 패딩값으로 2를 지정하는 클래스이다.     |
| ```d-flex justify-content-end```       | HTML 요소를 오른쪽으로 정렬하는 클래스이다.    |
| ```bg-light```                         | 연회색으로 배경을 지정하는 클래스이다.         |
| ```text-dark```                        | 글자색을 검은색으로 지정하는 클래스이다.        |
| ```text-start```                       | 글자를 왼쪽으로 정렬하는 클래스이다.          |
| ```btn btn-primary```                  | 버튼 컴포넌트를 적용하는 클래스이다.          |

--------------------------------------
## Spring Boot Validation

|항목|설명|
|--|--|
|@Size|문자 길이를 제한한다.|
|@NotNull|Null을 허용하지 않는다.|
|@NotEmpty|Null 또는 빈 문자열("")을 허용하지 않는다.|
|@Past|과거 날짜만 입력할 수 있다.|
|@Future|미래 날짜만 입력할 수 있다.|
|@FutureOrPresent|미래 또는 오늘 날짜만 입력할 수 있다.|
|@Max|최댓값 이하의 값만 입력할 수 있도록 제한한다.|
|@Min|최솟값 이상의 값만 입력할 수 있도록 제한한다.|
|@Pattern|입력값을 정규식 패턴으로 검증한다.|

--------------------------------------
## Spring Security

- 스프링 시큐리티는 스프링 기반 웹 애플리케이션의 인증과 권한을 담당하는 스프링의 하위 프레임워크이다.
- 인증(authenticate)은 로그인과 같은 사용자의 신원을 확인하는 프로세스를, 권한(authorize)은 인증된 사용자가 어떤 일을 할 수 있는지(어떤 접근 권한이 있는지) 관리
- 스프링 시큐리티는 기본적으로 세션 & 쿠키 방식으로 인증
- 인증 관리자(Authentication Manager)와 접근 결정 관리자(Access Decision Manger)를 통해 사용자의 리소스 접근을 관리
  - 인증 관리자는 UsernamePasswordAuthenticationFilter가 수행
  - 접근 결정 관리자는 FilterSecurityInterceptor가 수행

### 필터
- 클라이언트와 자원 사이에서 요청과 응답 정보를 이용해 다양한 처리를 하는 목적
- 기본 제공되는 필터 : Security Filter Chain
  - SecurityContextPersistenceFilter : SecurityContextRepository에서 SecurityContext를 가져오거나 저장하는 역할
  - LogoutFilter : 설정된 로그아웃 URL로 오는 요청을 감시하며, 해당 유저를 로그아웃 처리
  - (UsernamePassword)AuthenticationFilter : (아이디와 비밀번호를 사용하는 form 기반 인증) 설정된 로그인 URL로 오는 요청을 감시하며, 유저 인증 처리 
    - Authentication Manager를 통한 인증 실행
    - 인증 성공 시, 얻은 Authentication 객체를 SecurityContext에 저장 후 AuthenticationSuccessHandler 실행
    - 인증 실패 시, AuthenticationFailureHandler 실행
  - DefaultLoginPageGeneratingFilter : 폼기반 또는 OpenID기반 인증에 사용하는 가상 URL에 대한 요청을 감시하고 로그인 폼 기능을 수행하는데 필요한 HTML을 생성
  - BasicAuthenticationFilter : HTTP 기본 인증 헤더를 감시하여 처리
  - RequestCacheAwareFilter : 로그인 성공 이후, 원래 인증 요청에 의해 가로채어진 사용자의 원래 요청을 재구성하는데 사용
  - SecurityContextHolderAwareRequestFilter : HttpServletRequestWrapper를 상속한 SecurityContextHolderAwareRequestWapper 클래스로 HttpServletRequest 정보를 감싼다. SecurityContextHolderAwareRequestWrapper 클래스는 필터 체인상의 다음 필터들에게 부가정보를 제공
  - AnonymousAuthenticationFilter : 이 필터가 호출되는 시점까지 사용자 정보가 인증되지 않았다면 인증토큰에 사용자가 익명 사용자로 나타난다.
  - SessionManagementFilter : 인증된 주체를 바탕으로 세션 트래킹을 처리해 단일 주체와 관련한 모든 세션들이 트래킹되도록 도움
  - ExceptionTranslationFilter : 이 필터는 보호된 요청을 처리하는 중에 발생할 수 있는 예외의 기본 라우팅과 위임, 전달하는 역할을 한다.
  - FilterSecurityInterceptor : 이 필터는 Access Decision Manager 로 권한부여 처리를 위임함으로써 접근 제어 결정을 쉽게해준다.

### sbb 프로젝트 구성
- 스프링 시큐리티는 기본적으로 인증되지 않은 사용자가 SBB와 같은 웹 서비스를 사용할 수 없게끔 만든다. 따라서 최조 접속 시 인증을 위한 로그인 화면이 나타난다.
- 스프링 시큐리티는 웹 사이트의 콘텐츠가 다른 사이트에 포함되지 않도록 하기 위해 X-Frame-Options 헤더의 기본값을 DENY로 사용하는데, 프레임 구조의 웹 사이트는 이 헤더의 값이 DENY인 경우 오류가 발생한다.
- 스프링 부트에서 X-Frame-Options 헤더는 클릭재킹 공격을 막기 위해 사용한다. 클릭재킹은 사용자의 의도와 다른 작업이 수행되도록 속이는 보안 공격 기술이다.

### H2 콘솔 수정
- 스프링 시큐리티를 적용하면 H2 콘솔 로그인 시 403 Forbidden 오류가 발생
- 스프링 시큐리티의 CSRF 방어 기능에 의해 H2 콘솔 접근이 거부 됨.
  - CSRF는 웹 보안 공격 중 하나로, 조작된 정보로 웹 사이트가 실행되도록 속이는 공격 기술이다.
  - 스프링 시큐리티는 이러한 공격을 방지하기 위해 CSRF 토큰을 세션을 통해 발행하고, 웹 페이지에서는 폼 전송 시에 해당 토큰을 함께 전송하여 실제 웹 페이지에서 작성한 데이터가 전달되는지를 검증
- 스프링 시큐리티는 페이지에 CSRF 토큰을 발행하여 이 값이 다시 서버로 정확하게 들어오는지를 확인하는 과정을 거친다.
- 만약 CSRF 토큰이 없거나 해커가 임의의 CSRF 토큰을 강제로 만들어 전송한다면 스프링 시큐리티에 의해 차단될 것이다.
- H2 콘솔은 스프링 프레임워크가 아니므로 CSRF 토큰을 발행하는 기능이 없어 이와 같은 403 오류가 발생

### 인증
- 제공 방식 : 폼 기반 로그인, OAuth, LDAP 등
- 설정 (SecurityConfig 클래스)
  - HttpSecurity 객체를 사용하여 인증 방식, 로그인 페이지, 로그아웃 처리 등을 설정
  - formLogin() 메서드를 사용하여 폼 기반 로그인을 활성화
- 사용자 인증 정보 관리 (UserDetailsService 인터페이스)
  - 스프링 시큐리티는 사용자 인증 정보를 UserDetails 객체로 관리
  - loadUserByUsername(String username) 메서드를 통해 사용자의 인증 정보를 불러오는 역할
  - BCryptPasswordEncoder와 같은 패스워드 인코더를 사용하여 사용자의 비밀번호를 안전하게 관리할 수 있음

#### 인증테스트
- MockMvc
  - 컨트롤러 테스트
  - perform(post("/login").param("username", "user").param("password", "password"))와 같은 코드를 사용하여 로그인 요청을 테스트
- SpringBootTest
  - 통합 테스트

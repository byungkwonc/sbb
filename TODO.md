# 답변 페이징과 정렬 기능 추가하기
- 현재 SBB는 질문 하나에 답변이 무수히 많이 달릴 수 있는 구조이다. 성능을 위해서라도 답변 페이징은 반드시 필요하다.
- 그리고 답변을 최신순, 추천순 등으로 정렬하여 보여 줄 수 있는 기능도 필요하다.
- 여러분은 이미 질문 목록에 페이징과 정렬을 적용한 경험이 있으므로 답변에 페이징과 정렬을 적용하는 것이 그리 어렵지는 않을 것이다.

# 카테고리 추가하기
- ‘질문답변’이라는 카테고리로만 게시판을 구성하지만 여기에 ‘강좌’나 ‘자유게시판’을 추가로 더 만들고 싶을 수도 있을 것이다. 이런 경우에 Category 엔티티를 추가하고 Question 엔티티에 Category 엔티티를 연결하면 게시판을 분류할 수 있을 것이다.

# 비밀번호 찾기와 변경 기능 추가하기
- 비밀번호 분실 시 임시 비밀번호를 가입할 때 등록한 이메일 주소로 발송하여 로그인할 수 있도록 조치하는 간단한 기능을 구현해 보자.
- 그리고 비밀번호 변경 프로그램도 필요하다.
- 로그인한 후 기존 비밀번호와 새 비밀번호를 입력받아 비밀번호를 변경할 수 있는 프로그램을 만들어 보자.

# 프로필 화면 구현하기
- 로그인한 사용자의 프로필 화면을 만들어 보자.
- 이 화면에는 사용자의 기본 정보와 작성한 질문, 답변, 댓글 등을 확인할 수 있도록 하면 좋을 것이다.

# 최근 답변과 최근 댓글 순으로 노출시키기
- 현재 SBB는 질문 글 위주로 목록을 보여 준다. 하지만 최근에 작성된 답변이나 최근에 작성된 댓글이 궁금할 수도 있을 것이다. 최근 답변과 최근 댓글을 확인할 수 있는 기능을 추가해 보자.

# 조회 수 표시하기
- 현재 SBB는 답변 수를 표시하고 있지만 조회 수는 표시하지 않는다. 조회 수를 표시해 보자.

# 소셜 미디어 로그인 기능 구현하기
- SBB에 구글이나 페이스북, 트위터 등을 경유하여 로그인하는 소셜 로그인 기능을 구현해 보자.

# 마크다운 에디터 적용하기
- 마크다운 문법을 더 쉽게 입력할 수 있는 마크다운 에디터를 적용해 보자. simpleMDE(simplemde. com)를 SBB에 적용해 보자.

# 서비스

## 오라클 클라우드 가입
- byungkwonc
## 인스턴스 생성
- 컴퓨트 > 인스턴스 > 인스턴스생성
## 퍼블릭 고정 IP 확보
- 네트워킹 > IP관리 > 예약된 퍼블릭 IP > 퍼블릭 IP 예약
- 컴퓨트 > 인스턴스 > 인스턴스 세부정보 > 연결된 VNIC > VNIC 세부정보 > IPv4 주소
  - 전용 IP 주소 편집 : 공용IP없음 - update - 예약된 공용 IP : 기존 예약된 IP 주소 선택 - update
- PublicIP : 129.154.51.235
## 방화벽 해제
- 컴퓨트 > 인스턴스 > 서브넷 or 네트워킹 > 가상 클라우드 네트워크 > 가상 클라우드 네트워크 세부정보 > 서브넷
- 보안목록 (Default Security List for vcn-20240731-1915)
  - INBOUND : TCP - 0.0.0.0/0 (any) - 80, 8080, 443
- instance SSH 접속 후 iptables 수정
  - 네트워크 인터페이스 이름 확인 : ifconfig
  - 보안 정책 확인 : sudo iptables --list
  - 보안 정책 추가
    - sudo iptables -I INPUT 1 -i ens3 -p tcp --dport 80 -m state --state NEW,ESTABLISHED -j ACCEPT
      sudo iptables -I INPUT 2 -i ens3 -p tcp --dport 443 -m state --state NEW,ESTABLISHED -j ACCEPT
      sudo iptables -I INPUT 3 -i ens3 -p tcp --dport 8080 -m state --state NEW,ESTABLISHED -j ACCEPT
- 보안 정책 유지
  - sudo yum install iptables-persistent (or netfilter-persistent)
  - netfilter-persistent save
  - netfilter-persistent start
## SSH private key
- 인스턴스 생성 시 다운로드
- SSH, SFTP Client : Terminus
- instance에 opc 계정으로 접속 후 root 계정 암호 설정
  - sudo su | passwd 
## 배포
- 호스트명 변경
  - sudo hostnamectl set-hostname sbb
  - sudo reboot
  - hostname
- 시간 설정
  - date
  - sudo ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
  - date
- java 설치
  - java -version
  - sudo apt update
  - sudo apt install openjdk-19 -jdk
  - java -version
- 프로젝트 디렉터리 설정
  - /home/ubuntu > mkdir sbb
- 프로젝트 배포 파일 생성
  - Gradle Task
  - name : sbb
  - run : boorJar
  - /build/libs/sbb-0.0.1-SNAPSHOT.jar
- 서버 디렉터리로 업로드 (SFTP)
  - /home/ubuntu/sbb/sbb-0.0.1-SNAPSHOT.jar
- SSH 실행
  - /home/ubuntu/sbb > java -jar sbb-0.0.1-SNAPSHOT.jar
    - http://43.202.195.94:8080/

## 백그라운드 서비스
- nano start.ssh
```bash
#!/bin/bash

# 배포파일 이름
JAR=sbb-0.0.1-SNAPSHOT.jar
# 로그 파일 이름
LOG=/home/ubuntu/sbb/sbb.log

# nohup는 프로세스를 실행한 터미널의 연결이 끊어지더라도 프로세스가 지속적으로 동작할 수 있게 해주는 명령어
# java –jar $JAR는 JAR 변수에 저장된 JAR 파일을 실행하라는 명령어
# 명령어 > $LOG는 자바로 실행된 프로세스의 출력을 로그 파일에 저장하라는 의미
# 2>&1은 오류 출력(stderr)을 일반 출력(stdout)으로 전달하라는 의미. 따라서 일반 로그와 오류 로그가 모두 sbb.log 파일에 저장
# & 기호는 백그라운드로 명령을 실행하라는 의미

nohup java -jar $JAR > $LOG 2>&1 &
```
  - chmod +x start.sh
  - ./start.sh
- nano stop.sh
```bash
#!/bin/bash

SBB_PID=$(ps -ef | grep java | grep sbb | awk '{print $2}')

if [ -z "$SBB_PID" ];
then
    echo "SBB is not running"
else
    kill -9 $SBB_PID
    echo "SBB stopped."
fi
```
  - chmod +x stop.sh
  - ./stop.ssh

## 서버 환경 파일 생성
- 시작 옵션으로 ```spring.profiles.active``` 전달
  - application.properties 파일 대신 application-prod.properties을 사용
```bash
java -Dspring.profiles.active=prod -jar sbb-0.0.1-SNAPSHOT.jar
```
- application-prod.properties
  - # DATABASE
    - spring.h2.console.settings.web-allow-others=true
    - spring.datasource.password=1234
- build.gradle
  - version = '0.0.2' -> sbb-0.0.2.jar
- H2 : ALTER USER sa SET PASSWORD '1234';
- start.sh
  - JAR=sbb-0.0.2.jar
  - nohup java -Dspring.profiles.active=prod -jar $JAR > $LOG 2>&1 &
- (or) start.sh
  - export spring_profiles_active=prod
- ./stop.sh
- ./start.sh

## Nginx로 80 포트로 서비스 하기
- sudo apt install nginx
- cd /etc/nginx/sites-available/
- sudo nano sbb
```yaml
server {
        listen 80;
        server_name localhost;

        location / {
                proxy_pass http://localhost:8080;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
        }
}

# listen 80 : 웹 서버를 80번 포트로 서비스
# server_name : ip와 연결된 도메인을 구입하지 않았으므로 localhost를 입력
# location / { … } : /로 시작되는 모든 URL, 즉 모든 클라이언트 요청에 대한 설정을 담당
#    - proxy_pass: 엔진엑스(nginx) 웹 서버가 받은 모든 클라이언트 요청을 http://localhost:8080으로 리다이렉트
#    - proxy_set_header: 브라우저에서 SBB 서비스를 호출하면 엔진엑스를 통해서 스프링 부트(Spring Boot)의 톰캣(Tomcat) 서버로 요청이 전달된다. proxy_set_header 설정은 이 과정에서 클라이언트의 주소가 실제 IP 주소가 아닌 엔진엑스가 설치된 서버의 주소로 톰캣 서버에 전달되는 것을 방지하기 위해 사용한다.
```
- cd /etc/nginx/sites-enabled/
  - site-available 디렉터리에 있는 설정 파일 중에서 활성화하고 싶은 것을 링크로 관리하는 디렉터리
- ls
  - default
- sudo rm default
- sudo ln -s /etc/nginx/sites-available/sbb
- ls
  - sbb

## Nginx 실행
- cd /etc/nginx/sites-enabled
- sudo systemctl restart nginx
  - sudo systemctl stop nginx
  - sudo systemctl start nginx
  - sudo nginx -t
    - nginx 설정 파일 오류 검사
- http://43.202.195.94
- if 502 then ./start.sh

## 로그 관리
- 스프링 부트는 기본적으로 로그백(Logback)이라는 로깅 도구를 사용하여 로그를 관리
- IDE 콘솔 창에 출력되는 내용과 서버에서 sbb.log 파일에 출력되는 내용 모두 이 로그백에 의해 출력되는 로그
- SBB 서비스를 다시 실행할 경우(즉, stop.sh를 하고 start.sh를 실행할 경우) 이전 로그가 삭제된다.
- 로그가 쌓일수록 로그 파일의 용량이 커지며 무한대로 증가할 수 있다.
- 로그 시간이 시스템 시간이 아닌 UTC 시간으로 출력된다.

### 운영 환경 로그 분리
- application-prod.properties
```yaml
# logging
logging.logback.rollingpolicy.max-history=30
logging.logback.rollingpolicy.max-file-size=100MB
logging.file.name=logs/sbb.log
logging.logback.rollingpolicy.file-name-pattern=${LOG_FILE}.%d{yyyy-MM-dd}-%i.log
logging.pattern.dateformat=yyyy-MM-dd HH:mm:ss.SSS,Asia/Seoul

# logging.logback.rollingpolicy.max-history: 로그 파일을 유지할 기간(일수)을 설정
# logging.logback.rollingpolicy.max-file-size: 로그 파일 1개의 최대 용량(size)를 설정
# logging.file.name: 로그 파일의 이름을 설정
# logging.logback.rollingpolicy.file-name-pattern: 로그 파일의 용량이 설정한 용량을 초과하거나 날짜가 변경될 경우 새로이 만들어질 로그 파일의 이름에 관한 규칙(pattern)을 설정
# logging.pattern.dateformat: 로그 출력 시 출력하는 날짜와 시간의 형식과 타임존(time zone)을 설정. 타임존을 설정하지 않을 경우 UTC 시간을 기준으로 출력
```
- /home/ubuntu/sbb/start.sh
  - LOG=/dev/null 로 변경
  - 로깅 설정을 통해 logs 디렉터리 하위에 로그 파일(sbb.log)이 생성되도록 설정
  - 자바 프로그램의 출력을 /dev/null로 지정하면 콘솔 출력이 무시됨
- rm sbb.log
- stop.sh
- start.sh
- ls

### 사용자 로그
- 롬복(Lombok)이 제공하는 @Slf4j 애너테이션을 사용
- trace(1단계): 가장 낮은 로그 레벨이며, debug보다 정보를 훨씬 상세하게 기록할 경우에 사용한다.
  debug(2단계): 디버깅 목적으로 사용한다.
  info(3단계): 주요 이벤트나 상태 등의 일반 정보를 출력할 목적으로 사용한다.
  warn(4단계): 문제가 발생할 가능성이 있는 상태나 상황 등(비교적 작은 문제)에 관한 경고 정보를 출력할 목적으로 사용한다.
  error(5단계): 심각한 문제나 예외 상황 등(비교적 큰 문제)에 대한 오류 정보를 출력할 목적으로 사용한다.
  fatal(6단계): 가장 높은 로그 레벨이며, 프로그램 기능의 일부가 실패하거나 오류가 발생하는 등 아주 심각한 문제에 관한 정보를 출력할 목적으로 사용한다.
- application-prod.properties 파일에 logging.level.root=info로 설정하면 TRACE, DEBUG 로그는 출력되지 않고 INFO 이상의 로그만 출력된다.
  - log.trace 또는 log.debug로 출력하는 로그는 출력되지 않고 logging.info, logging.warn, logging.error, logging.fatal로 출력한 로그만 출력
  - logging.level.root의 기본값은 info. 특별히 설정하지 않으면 info로 설정된다.

## 도메인
- 가비아나 AWS 등에서 구매
- 오라클 클라우드에서 도메인 및 DNS 설정
  - 도메인 등록 및 고정 IP 할당
- /etc/nginx/sites-available/sbb
  - server_name {구매한_도메인};
- sudo systemctl restart nginx

## HTTPS로 전환 하기
- SSL 인증서 발급
  - 무료로 SSL 인증서를 발급해 주는 Let’s Encrypt 서비스를 사용
  - 봥화벽 해제 : 443
- ~$ sudo apt install certbot
- ~$ sudo apt install python3-certbot-nginx
- ~$ sudo certbot certonly --nginx
  - email 입력
  - (a)gree
  - (y)es
  - 구입한 도메인 입력
  - /etc/letsencrypt/live/{구매한_도메인}/fullchain.pem
  - /etc/letsencrypt/live/{구매한_도메인}/privkey.pem
- /etc/nginx/sites-available/sbb
```yaml
server {
        listen 80;
        server_name {구매한_도메인};
        rewrite        ^ https://$server_name$request_uri? permanent;
}

server {
        listen 443 ssl;
        server_name {구매한_도메인};

        ssl_certificate /etc/letsencrypt/live/{구매한_도메인}/fullchain.pem; # managed by Certbot
        ssl_certificate_key /etc/letsencrypt/live/{구매한_도메인}/privkey.pem; # managed by Certbot
        include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot

        location / {
                proxy_pass http://localhost:8080;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
        }
}
```
- sudo systemctl restart nginx.service

## PostgreSQL 로 전환 하기
- AWS 라이트 세일 데이터베이스 생성. 월 15$
- sudo apt install postgresql-client
- ~/projects/mysite$ createdb sbb --username=dbmasteruser -h ls-be78fd2c2exxxxxxxxxxxxxxxx2c9.cqlcyugj7ibs.ap-northeast-2.rds.amazonaws.com
- build.gradle
  - runtimeOnly 'org.postgresql:postgresql'
- application-prod.properties
```yaml
# DATABASE
spring.datasource.url=jdbc:postgresql://<데이터베이스주소>:5432/sbb
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=dbmasteruser
spring.datasource.password=<암호>

# JPA
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none
```
- stop.sh
- start.sh

### pgAdmin
- AWS PostgreSQL > 네트워킹 : 퍼블릭모드 on
- https://www.pgadmin.org/download
- 
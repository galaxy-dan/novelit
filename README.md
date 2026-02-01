![novelit](https://github.com/galaxy-dan/novelit/assets/85854928/62f15271-bcd8-4938-a419-e0da2c72d6a8)
![novelit2](https://github.com/user-attachments/assets/e99d3f04-a057-4291-9874-fe5488d1c865)

## 주요 기능

### 카카오 로그인

### 작품 관리

1. 작가가 쓴 작품 목록 확인
2. 소설 작품 생성 및 제목 변경, 삭제
3. react-arborist를 사용하여 작품 내의 디렉토리와 파일을 tree 형식으로 보여줌
4. 생성, 삭제, 수정, 이동, 검색 가능

### 글 쓰기(에디터)

1. 글자수 체크
2. 커스텀 버튼을 활용하여 글자 크기, 글꼴, bold 처리 가능
3. 결과물 txt 파일로 다운로드 가능
4. 맞춤법 검사 가능
    1. 단어장을 제외한 틀린 맞춤법 검사 결과 제공
5. 단어장 기능 
    1. 맞춤법 검사에서 제외하도록 함 
    2. 캐릭터 ,그룹 생성시 단어장에 추가되면 별도로 단어 지정하여 저장 가능
6. 공유
    1. 작가가 토큰을 발급하여 편집자에게 넘겨주면 별도의 로그인 없이 해당 글에 대해서만 접속이 가능
    2. 특정 부분을 드래그하여 댓글 작성 가능
    3. 댓글 달린 부분을 하이라이트 표시
    4. 편집자가 댓글을 달면 작가에게 알림
7. 알림
    1. 댓글 작성시 댓글 작성자 모두에게 알림이 간다.
     - SSE를 활용
     - 로그인시 subscribe api 호출을 통해 연결
     - 이후 댓글 작성시 서버 → 클라이언트 알림 이벤트 호출
    2. 오른쪽 상단의 종모양을 통해 전체 알림을 확인 할 수 있다.
     - 알림데이터는 redis에 보관
     - 유효기간 1일 설정
     - 댓글 작성자 - 작성 내용 : key - value 관계
8. 댓글
   1. 본문에 피드백을 남기고 싶은 부분을 하이라이팅하여 댓글 작성 가능
   2. MongoDB 활용
      - 하이라이팅 부분에 UUID를 부여
      - 하이라이팅 공간이 해당 구역에 달린 모든 댓글 작성 정보(작성자, 작성 내용)를 포함하는 nested data형태를 사용함.

### 캐릭터

1. 그룹 페이지에서 그룹 이름 수정 가능
2. 캐릭터 이미지 저장 시 압축
3. 캐릭터 기본정보 다양한 key로 저장 가능
4. 캐릭터 관계
    1. 캐릭터 관계에서 캐릭터 목록에 있는 대상 선택 시 글자 검정색, uuid 저장하여 관계도에서 검색 되도록 함,
    2. 캐릭터 목록에서 선택하지 않을 시 글자 회색, uuid null로 고치고 관계도에서 검색 안됨

### 관계도

1. cytoscape js 활용
2. 그룹 선택 시 하위 그룹과 캐릭터 확인 가능
3. 캐릭터 선택 시
    1. 모든 관계 (그룹포함)
    2. 내가 주는 관계
    3. 내가 받는 관계
    표시
4. 캐릭터와 그룹 이동 시 위치 저장

### 플롯

1. 스토리 흐름을 정리할 수 있는 기능
2. 발단-전개-위기-절정-결말로 나누어 기록
3. 혹은 <줄거리 작성> 부분에 자유롭게 기록
4. 플롯 검색 기능
 - QueryDSL 사용
 - 키워드를 포함한 플롯의 제목을 검색할때, 다음과 같은 순서로 조회
    - 1순위 : 키워드가 제일 앞에 위치
    - 2순위 : 키워드를 포함한 제목들

## 기술 스택 및 버전

<aside>
💡 **Server**

</aside>

### `**Back-end server**`

- gradle 8.3
- develop 서버 (**내부포트: docker network, 외부포트 : 8080**)
- prodution 서버 (**내부포트: docker network, 외부포트 : 8080**)
- spring boot - 3.1.5 ver.
- jdk - 17 ver.
- querydsl - querydsl-jap:5.0.0 ver.
- JWT - io.jsonwebtoken:jjwt 0.9.1 ver.
- Spring Security - spring boot 버전과 동일
- JPA - spring boot 버전과 동일

### `**DataBase server**`

- MariaDB - 11.0.2 ver.
- MariaDB - **포트 : 3306**
- MongoDB - (v 7.0.2)
- redis version:7.2.3 - 포트 : 3503 → 6379 (aws포트 - 컨테이너포트)

### **`Infra`**

- AWS EC2
- Docker - v.24.0.6
- Jenkins  - 포트: 3000
- Nginx - v.1.18.0
- nGrinder - 포트: 3800
- prometheus - 포트: 3500 → 9090
- node-exporter - 포트: 3501 → 9091
- grafana - 포트: 3502 → 3000

<aside>
💡 **Web Frontend**

</aside>

- Next.js 13.4.12
- typescript 5.1.6
- recoil 0.7.7
- tailwind 3.3.3
- tanstack/react-query 4.32.0
- react-hook-form 7.45.2
- framer-motion 10.16.4
- react-icons 4.10.1
- react-intersection-observer 9.5.2
- react-aws-s3 1.5.0
- react-spinners 0.13.8
- yup 1.2.0

<aside>
💡 **Cooperation**

</aside>

- JIRA
- Notion
- Slack
- Git Lab

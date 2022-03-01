# QueryDSL
* JPA 표준에서 지원하는 Criteria 기술처럼 JPQL 빌더 역할을 해주는 오픈소스 프로젝트
* 기존 JPQL처럼 문자로 작성하는 것이 아닌 자바 문법으로 JPQL을 작성할 수 있도록 해줌
  * 문법 오류를 컴파일 단계에서 잡을 수 있음
  * IDE 자동완성의 기능 도움을 받을 수 있음
  * Criteria에 비해 복작성이 낮음
  * 쿼리를 문자가 아닌 코드로 작성해 쉽고 간결하며 그 모양도 쿼리와 비슷하게 개발할 수 있게 해줌

### QueryDSL 설정
* pom.xml - dependency
    
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-apt</artifactId>
            <!-- <version>${querydsl.version}</version> -->
        </dependency>
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-jpa</artifactId>
            <!-- <version>${querydsl.version}</version> -->
        </dependency>

    * querydsl-jpa: QueryDSL JPA 라이브러리
    * querydsl-apt: 쿼리 타입(Q)을 생성할 때 필요한 라이브러리
    * Spring Boot 프로젝트 구성 시, 위의 의존성 또한 Spring Boot가 안정적인 버전을 알아서 설정해주기 때문에 version을 기입하지 않아도 된다.

QueryDSL을 사용하려면 엔티티를 기반으로 쿼리 타입이라는 쿼리용 클래스를 생성해야 한다.
쿼리 타입 생성용 APT 플러그인을 설정

* pom.xml - plugin

        <plugin>
            <groupId>com.mysema.maven</groupId>
            <artifactId>apt-maven-plugin</artifactId>
            <version>1.1.3</version>
            <executions>
            <execution>
                <goals>
                    <goal>process</goal>
                </goals>
                <configuration>
                    <outputDirectory>target/generated-sources/java</outputDirectory>
                    <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                </configuration>
            </execution>
            </executions>
        </plugin>

    * JPAAnnotationProcessor: javax.persistence.Entity 어노테이션을 가진 도메인 타입을 찾아서 쿼리 타입을 생성함
    * 도메인 타입으로 Hibernate 어노테이션을 사용하면, APT 프로세서로 com.querydsl.apt.hibernate.HibernateAnnotationProcessor를 사용해야 한다.
    * target/generated-sources/java 디렉토리에 Query 타입 생성
      * IntelliJ 사용
        * 최상위 프로젝트 마우스 우클릭 -> maven -> Generate Sources and Update Folders 클리 
      * Eclipse 사용
        * mvn eclipse:eclipse 실행
    * QEntity가 생기는 경로는 **gitignore** 설정하는 것을 권장
      * 메이븐이 알아서 생성해주는 파일이기 때문에 프로젝트 설정에 따라 알아서 생성된다
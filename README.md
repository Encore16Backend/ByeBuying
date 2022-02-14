* Spring Boot
  - Maven
  - Java 1.8
  - 2.6.3
  - Library
    - Spring Data JPA
    - MySQL Driver
    - Spring Security
    - Spring Boot DevTools
    - Lombok

* DataBase
  - MySql 8.0.22
  - HeidiSQL 11.3

* PLAN
  - 2/12 ~ 2/18
    - User Table Create
    - JWT
    - Oauth - Google, Kakao, Naver
  - 2/19 ~ 2/25
    - Item, Review, Basket Table Create
  - 2/26 ~ 2/28
    - Image Search

* Table
  - User
    - User Table
      - ID: Auto Increase, Primary Key, INT
      - USERNAME: NOT NULL, Unique, VARCHAR
      - PASSWORD: NOT NULL, VARCHAR
      - LOCATION: NOT NULL, VARCHAR
      - STYLE: NULL, INT
      - EMAIL: NOT NULL, VARCHAR -> Mail Authentication
      - ROLE: NOT NULL, INT
    - Role Table
      - ID: Auto, Primary Key, INT
      - NAME: VARCHAR
    - USER_ROLES TABLE
      - USER_ID: User Table Foreign Key
      - ROLES_ID: Role Table Foreign Key
  - Item Table
    - ITEMID: Auto Increase, Primary Key, INT
    - CATEGORYID: NOT NULL, INT
    - ITEMNAME: NOT NULL, VARCHAR
    - MAINIMG: NOT NULL, VARCHAR
    - DETAILIMG: NULL, VARCHAR
    - SIZE: NOT NULL, INT
    - PRICE: NOT NULL, INT
    - PURCHASECNT: NOT NULL, INT
    - COUNT: NOT NULL, INT
    - REVIEWMEAN: NOT NULL, FLOAT
    - DESCRIPTION: NOT NULL, VARCHAR
  - Review Table
    - REVIEWID: Auto Increase, Primary Key, INT
    - ITEMID: Item Table Foreign Key
    - USERID: User Table Foreign Key
    - SCORE: NOT NULL, INT
    - CONTENT: NOT NULL, VARCHAR
    - GENERATEDATE: NOT NULL, DATE
  - Basket Table
    - ITEMID: Item Table Foreign Key
    - USERID: User Table Foreign Key
    - COUNT: NOT NULL, INT
    - SIZE: NOT NULL, INT
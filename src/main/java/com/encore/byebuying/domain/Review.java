package com.encore.byebuying.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {

    @Id @GeneratedValue(strategy = AUTO)
	private Long id;
	private String username; // 유저 네임
	private String itemname; // 아이템 이름
	
	private double score; // 리뷰 점수
	private String content; // 리뷰 내용
	@Temporal(TIMESTAMP)
	private Date date; // 리뷰 작성 날짜
}

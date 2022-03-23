package com.encore.byebuying.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistory {

	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String username;
	private int bcount; // 개수

	private String location;
	private int chkreview; // 리뷰 체크
	
	private Long itemid;
	private String itemimg;
	private String itemname;
	private int itemprice;

	@Temporal(DATE)
	private Date date; // 구매 날짜
}

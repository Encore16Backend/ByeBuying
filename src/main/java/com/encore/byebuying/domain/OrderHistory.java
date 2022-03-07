package com.encore.byebuying.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistory extends BaseTimeEntity{

	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String username;
	private int bcount; // 개수
	
	private Long itemid;
	private String itemimg;
	private String itemname;
	private int itemprice;
}

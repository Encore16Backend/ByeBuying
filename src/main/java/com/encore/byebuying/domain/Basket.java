package com.encore.byebuying.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private int bcount; // 개수

    private Long itemid;
    private String itemimg;
    private String itemname;
    private int itemprice;

}

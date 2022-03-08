package com.encore.byebuying.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String username;

    private Long itemid;
    private String itemname;

    private String title;
    private String content;
    private String answer;
    @Temporal(TemporalType.DATE)
    private Date date; // 년-월-일

    private int chkanswer;

}

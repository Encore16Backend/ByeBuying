package com.encore.byebuying.domain.inquiry;

import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.user.User;
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
public class Inquiry extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;
    private String answer;
    @Temporal(TemporalType.DATE)
    private Date date; // 년-월-일

    private int chkanswer;

    public static Inquiry createInquiry(User user, String title, String content){
        Inquiry inquiry = Inquiry.builder()
                .user(user)
                .title(title)
                .chkanswer(0)
                .content(content).build();

        user.getInquiries().add(inquiry);

        return inquiry;
    }

}

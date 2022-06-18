package com.encore.byebuying.domain.inquiry;

import com.encore.byebuying.domain.code.InquiryType;
import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.inquiry.dto.InquirySaveDTO;
import com.encore.byebuying.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import lombok.ToString;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Inquiry extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
//    문의사항은 아이템 기준이 아니기 때문에 Item 필요 x
//    @ManyToOne
//    @JoinColumn(name = "item_id")
//    private Item item;
    private String title;
    private String content;
    private String answer = "";
    private InquiryType chkAnswer = InquiryType.WAITING;

    @Builder(builderClassName = "init", builderMethodName = "initInquiry")
    private Inquiry(InquirySaveDTO dto, User user) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.user = user;
    }

    public static Inquiry createInquiry(InquirySaveDTO dto, User user) {
        Inquiry inquiry = Inquiry.initInquiry().dto(dto).user(user).build();
        user.getInquiries().add(inquiry);
        return inquiry;
    }

    public void inquiryAnswer(String answer) {
        this.answer = answer;
        this.chkAnswer = InquiryType.COMPLETE;
    }
}

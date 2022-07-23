package com.encore.byebuying.domain.inquiry;

import com.encore.byebuying.domain.code.InquiryType;
import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.inquiry.controller.dto.UpdateInquiryDTO;
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
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private User user;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false, length = 2000)
    private String content;
    @Column(name = "answer", length = 2000)
    private String answer = "";
    @Column(name = "chkAnswer", nullable = false)
    private InquiryType chkAnswer = InquiryType.WAITING;

    @Builder(builderClassName = "update", builderMethodName = "updateBuildInquiry")
    private Inquiry(UpdateInquiryDTO dto, User user) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        if (user != null)
            this.user = user;
    }

    public static Inquiry updateInquiry(UpdateInquiryDTO dto, User user) {
        Inquiry inquiry = Inquiry.updateBuildInquiry()
            .dto(dto)
            .user(user)
            .build();

        if (user != null)
            user.getInquiries().add(inquiry);

        return inquiry;
    }

    public void inquiryAnswer(String answer) {
        this.answer = answer;
        this.chkAnswer = InquiryType.COMPLETE;
    }
}

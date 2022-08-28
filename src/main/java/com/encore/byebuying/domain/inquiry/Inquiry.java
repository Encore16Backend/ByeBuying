package com.encore.byebuying.domain.inquiry;

import static javax.persistence.GenerationType.IDENTITY;

import com.encore.byebuying.domain.code.InquiryType;
import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.inquiry.controller.dto.AnswerInquiryDTO;
import com.encore.byebuying.domain.inquiry.controller.dto.UpdateInquiryDTO;
import com.encore.byebuying.domain.user.User;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inquiry)) {
            return false;
        }
        Inquiry that = (Inquiry) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Inquiry createInquiry(UpdateInquiryDTO dto, User user) {
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(dto.getTitle());
        inquiry.setContent(dto.getContent());
        inquiry.setUser(user);

        user.getInquiries().add(inquiry);

        return inquiry;
    }

    public static Inquiry updateInquiry(Inquiry inquiry, UpdateInquiryDTO dto) {
        inquiry.setTitle(dto.getTitle());
        inquiry.setContent(dto.getContent());

        return inquiry;
    }

    public static Inquiry updateAnswerInquiry(Inquiry inquiry, AnswerInquiryDTO dto) {
        inquiry.setChkAnswer(InquiryType.COMPLETE);
        inquiry.setAnswer(dto.getAnswer());

        return inquiry;
    }
}

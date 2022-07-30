package com.encore.byebuying.domain.common.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Getter
@Setter
public class DateRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime endDate;

    public LocalDateTime getEndDate() {
        if (startDate == null) setStartDate(LocalDateTime.now());
        return endDate;
    }

    public LocalDateTime getStartDate() {
        if (this.startDate.isAfter(endDate)) setStartDate(this.endDate);
        return startDate;
    }

    public boolean hasDate(){
        return this.startDate != null && this.endDate != null;
    }

}

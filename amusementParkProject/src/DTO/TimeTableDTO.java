package DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class TimeTableDTO {
    private String tiId;
    private int tiDay;
    private LocalTime tiTime;
    private String tiContent;
}

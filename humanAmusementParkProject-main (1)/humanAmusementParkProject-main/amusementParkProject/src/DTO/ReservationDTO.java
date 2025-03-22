package javaproject.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Builder
public class ReservationDTO {
    private int no;
    private String mId;
    private String tPass;
    private String atId;
    private Date rTime;

    public String TimetoString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(rTime);
    }
}

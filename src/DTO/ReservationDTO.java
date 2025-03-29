package javaproject.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@Getter
@Setter
@Builder
public class ReservationDTO {
    private int no;
    private String mId;
    private String tPass;
    private String atId;
    private Timestamp rTime;

    public String TimetoString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(rTime);
    }


    public String toString(Timestamp rTime){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(rTime);
    }
}

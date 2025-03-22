package javaproject.DTO;

import lombok.Builder;
import lombok.Getter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter

@Builder
public class AdminDTO {
    private String aId;
    private String aPass;
    private String aName;
    private int aGender;
    private Date aBirth;
    private String aPosition;

    public String BirthToString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(aBirth);
    }

    public String getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(aBirth);
    }

    public String getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(aBirth);
    }

    public String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(aBirth);
    }



}
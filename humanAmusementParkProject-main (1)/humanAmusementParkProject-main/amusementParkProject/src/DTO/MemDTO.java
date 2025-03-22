package javaproject.DTO;

import lombok.Builder;
import lombok.Getter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Builder
public class MemDTO {
    private String mId;
    private String mPass;
    private String mName;
    private int mGender;
    private Date mBirth;
    private String tPass;

    public String getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(mBirth);
    }

    public String getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(mBirth);
    }

    public String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(mBirth);
    }

    public String BirthToString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(mBirth);
    }

    public String toString(Date mBirth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(mBirth);
    }

}

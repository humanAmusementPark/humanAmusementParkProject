package amuse;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
	String mId;
	String mPass;
	String mName;
	int mGender;
	Date mBirth;
	String tPass;
	
}

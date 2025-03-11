package javaproject;

public class MemberDAO {
    private static MemberDAO memberDAO;

    public static MemberDAO getInstance(){
        if(memberDAO == null){
            memberDAO = new MemberDAO();
        }
        return memberDAO;
    }
}

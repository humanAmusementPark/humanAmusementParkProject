package javaproject;

public class RidesDAO {
private static RidesDAO instance;
public static RidesDAO getInstance(){
    if(instance ==null){
        instance = new RidesDAO();
    }
    return instance;
}
}

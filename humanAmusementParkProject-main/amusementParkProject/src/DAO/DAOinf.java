package javaproject.DAO;

import java.util.List;

public interface DAOinf<T> {

    List<T> selectAll();
    T select(String id);
    boolean insert(T data);
    boolean update(T data);
    boolean delete(String id);

}

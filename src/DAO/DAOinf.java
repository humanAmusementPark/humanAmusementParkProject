package javaproject.DAO;

import javaproject.DTO.TicketDTO;

import java.util.List;

public interface DAOinf<T> {

    List<T> selectAll();
    T select(String id);
    boolean insert(T data);
    boolean update(T data);
    boolean delete(String id);


}

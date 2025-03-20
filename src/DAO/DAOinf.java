package javaproject.DAO;

import java.util.List;

public interface DAOinf<T> {

    public abstract List<T> selectAll();
    public abstract T select(String id);
    public abstract void insert(T data);
    public abstract void update(T data);
    public abstract void delete(T data);

}

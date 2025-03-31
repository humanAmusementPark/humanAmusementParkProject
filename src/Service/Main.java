package javaproject.Service;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new LoginS();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

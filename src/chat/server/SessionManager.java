package javaproject.chat.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javaproject.chat.util.MyLogger.log;

public class SessionManager {
    private final List<Session> customerQueue = new ArrayList<>();
    private final List<Session> adminQueue = new ArrayList<>();

    public void add(Session session) {
        try {
            if ("상담사".equals(session.getRole())) {

                adminQueue.add(session);// 상담사 대기열에 추가
                session.send(("상담사로 대기 중입니다."));
            } else if ("고객".equals(session.getRole())) {
                customerQueue.add(session);
                session.send("고객으로 대기 중입니다. 상담사와 매칭될 때까지 기다려 주세요.");
            }
            matchCustomerToAdmin(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void matchCustomerToAdmin(Session session) {
        Session customer = null;
        Session admin = null;
        if (!customerQueue.isEmpty() && !adminQueue.isEmpty()) {

            for (Session a : customerQueue) {
                for (Session b : adminQueue) {

                    if (a.getType().equals(b.getType()) && a.isFlag() && b.isFlag()) {

                        customer = a;
                        admin = b;
                        customer.setFlag(false);
                        admin.setFlag(false);
                    }
                }
            }
            if (customer != null) {
                customer.setMatchedSession(admin);
            }
            if (admin != null) {
                admin.setMatchedSession(customer);
            }


            try {
                if (session.getMatchedSession() != null) {
                    customer.send("고객이 매칭되었습니다. 이제 대화가 가능합니다.");
                    admin.send("상담사가 매칭되었습니다. 이제 대화가 가능합니다.");
                }
            } catch (IOException e) {
                log(e);
            }
        }
    }


    public void remove(Session session) {
        if ("상담사".equals(session.getRole())) {
            adminQueue.remove(session);
        } else if ("고객".equals(session.getRole())) {
            customerQueue.remove(session);
        }
    }

    public synchronized void closeAll() {
        for (Session session : adminQueue) {
            session.close();
        }
        for (Session session : customerQueue) {
            session.close();
        }
        adminQueue.clear();
        customerQueue.clear();
    }
}

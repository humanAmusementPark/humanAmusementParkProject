package test;

import DAO.TimeTableDAO;
import DTO.TimeTableDTO;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


import java.util.ArrayList;
import java.util.List;


public class TimeTable extends JFrame {
    private List<TimeTableDTO> timeTableDTOList;

    public TimeTable() {
        init();
        getTimeTableInfo();
        setDisplay();
        showFrame();

    }

    public void init() {


    }

    public void getTimeTableInfo()  {
        try{
            TimeTableDAO timeTableDAO = new TimeTableDAO();
            timeTableDTOList = timeTableDAO.select();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDisplay() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,7,10,10));


        //요일 라벨만들기
        String[] days = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};

        for (String day : days) {
            JLabel label = new JLabel(day,SwingConstants.CENTER);
            panel.add(label);
        }

        // 요일별 데이터를 그룹화 Map 활용
        Map<Integer, List<TimeTableDTO>> groupedData = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            groupedData.put(i, new ArrayList<>());
        }

        // timeTableDTOList를 요일별로 그룹화
        for (TimeTableDTO dto : timeTableDTOList) {
            groupedData.get(dto.getTiDay()).add(dto);
        }

        // 요일별로 내용과 시간을 세로로 나열
        for (int i = 1; i <= 7; i++) {
            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS)); // 세로로 배치

            // 해당 요일의 행사들을 표시
            List<TimeTableDTO> dtos = groupedData.get(i);
            if (!dtos.isEmpty()) {
                for (TimeTableDTO dto : dtos) {
                    // 행사 내용 표시
                    JLabel contentLabel = new JLabel(dto.getTiContent());
                    contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    dayPanel.add(contentLabel);

                    // 행사 시간 표시
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    JLabel timeLabel = new JLabel(dto.getTiTime().format(formatter));
                    timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    dayPanel.add(timeLabel);
                }
            } else {
                JLabel noContentLabel = new JLabel("");
                noContentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                dayPanel.add(noContentLabel);
            }

            // 요일별로 패널에 추가
            panel.add(dayPanel);
        }

        add(panel);

    }

    private void showFrame() {
        setTitle("Banner");
        setBounds(100, 100, 700, 300);
        setVisible(true);
        //이거 실행하면 전체 JFrame가 꺼짐 그래서 베너만 없에애되니까
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //임시 메인
    public static void main(String[] args) {

        // TimeTable 객체 생성 및 실행
        new TimeTable();
    }
}

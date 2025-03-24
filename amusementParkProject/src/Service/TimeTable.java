package javaproject.Service;

import javaproject.DAO.TimeTableDAO;
import javaproject.DTO.TimeTableDTO;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;


public class TimeTable extends JFrame {
    private List<TimeTableDTO> timeTableDTOList;
    private GridBagConstraints gbc;
    private GridBagLayout grid;

    public TimeTable() {

        getTimeTableInfo();
        setDisplay();
        showFrame();
    }

    public void getTimeTableInfo()  {
        try{
            TimeTableDAO timeTableDAO = new TimeTableDAO();
            timeTableDTOList = timeTableDAO.select();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void setDisplay() {

        Image image = new ImageIcon("resource\\images\\일정표.jpeg").getImage();
        //mac용
//        Image image = new ImageIcon("resource/images/일정표.jpeg").getImage();

        JPanel ImagePanel = new JPanel(){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image,0,0,getWidth(),getHeight(),this);
                this.setOpaque(true);
                super.paintComponents(g);
            }
        };


        //제목 만들기
        JLabel titleLabel = new JLabel("Parade Schedule",SwingConstants.CENTER);
        titleLabel.setBounds(190,20,500,100);
        Font font = new Font("맑은 고딕",Font.PLAIN,51);
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.white);
        add(titleLabel);

        //요일 라벨만들기
        String[] days = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};
        int x_temp = 40;
        for (String day : days) {
            JLabel label = new JLabel(day,SwingConstants.CENTER);
            label.setBounds(x_temp,103,100,40);
            label.setForeground(Color.white);
            x_temp += 115;
            add(label);
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
        int x = 40;
        // 요일별로 내용과 시간을 세로로 나열
        for (int i = 1; i <= 7; i++) {
            JPanel dayPanel = new JPanel(){
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(0, 0, 0, 0)); // 투명 색상
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };

            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS)); // 세로로 배치

            // 해당 요일의 행사들을 표시
            List<TimeTableDTO> dtos = groupedData.get(i);
            if (!dtos.isEmpty()) {
                for (TimeTableDTO dto : dtos) {

                    // 행사 시간 표시
                    JLabel timeLabel = new JLabel(String.valueOf(dto.getTiTime()));

                    timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    dayPanel.add(timeLabel);


                    // 행사 내용 표시
                    JLabel contentLabel = new JLabel(dto.getTiContent());

                    contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    dayPanel.add(contentLabel);

                    dayPanel.add(Box.createVerticalStrut(10)); // 10px의 세로 간격을 추가

                }
            } else {
                JLabel noContentLabel = new JLabel("");
                noContentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                dayPanel.add(noContentLabel);
            }
            // 요일별로 패널에 추가
            dayPanel.setBounds(x,150, 100,200);
            dayPanel.setOpaque(false);    //투명화

            x += 115;
            add(dayPanel);
        }
        add(ImagePanel);
    }

    public void showFrame() {
        setTitle("Banner");
        setBounds(100, 100, 885, 500);

        //창크기조절막기
        setResizable(false);

        setVisible(true);
    }
    public static void main(String[] args){
        new TimeTable();
    }

}

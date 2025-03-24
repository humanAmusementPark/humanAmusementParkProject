package amuse;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Reservation extends JFrame{
	String mId=null;
	AttractionDAO adao=AttractionDAO.getInstance();
	ResDAO rdao=ResDAO.getInstance();
	
	JPanel p=new JPanel();
	JLabel name=new JLabel();
	JLabel chaos=new JLabel();
	JButton submit=new JButton("예약하기");
	
	public Reservation(JFrame before, String atId) {
		this.setSize(500, 200);
		
		this.setTitle("기구 예약");
		this.setLayout(new GridLayout());
		//AttractionDTO attract=adao.getAttract(atId);
		//if(attract==null) {
		//	this.dispose();
		//}
		//mId=before.mId;
		//int count= rdao.selectatt(atId);
		
		mId="aaaa";
		AttractionDTO attract=AttractionDTO.builder()
				.atId("1111")
				.atName("관람차")
				.atUrl("C:\\Users\\hu-15\\Downloads\\mug_obj_149006360290199937.jpg")
				.atMax(30)
				.atOnoff(1)
				.build();
		
		
		int count=10;

		ImageIcon icon = new ImageIcon(attract.getAtUrl());
		JLabel lb1 = new JLabel(icon, JLabel.CENTER); //가운데로 수평정렬
		this.add(lb1);

		
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
        
		name.setText("기구명: "+attract.getAtName());
		p.add(name,c);
		
		c.gridy=1;
		chaos.setText("대기 인원 비율: "+(float)count/attract.getAtMax()*100+"%");
		p.add(chaos,c);
		
		c.gridy=2;
		p.add(submit,c);
		
		this.add(p);
		this.setVisible(true);
		this.setLocationRelativeTo(null); // 화면 중앙 배치
		
		submit.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	ResDTO r=new ResDTO();
            	r.setRId(mId+rdao.getcount(mId));
            	r.setAtId(attract.getAtId());
            	r.setMId(mId);
            	r.setTPass(TicketDAO.getInstance().selectti("1111").getTPass());
            	if(rdao.insertres(r)) {
            		JOptionPane.showMessageDialog(null, "예약되었습니다.");          		
            	}
            }
        });

		
		addWindowListener (
			    new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			        	before.setEnabled(true);
			        }
			    }
			);
	}
}

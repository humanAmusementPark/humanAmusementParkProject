package amuse;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class FoodView extends JFrame{
	//FoodDAO fdao=FoodDAO.getInstance();
	
	JPanel foodp=new JPanel();
	JPanel menup=new JPanel();
	JPanel contents=new JPanel();
	JLabel name=new JLabel();

	public FoodView(JFrame before, String fId) {
		this.setSize(500, 400);
		
		this.setTitle("식당 정보");
		
		contents.setLayout(new GridLayout());
		
		//foodDTO attract=fdao.getFood(atId);
		//if(attract==null) {
		//	this.dispose();
		//}
		//mId=before.mId;
		//int count= rdao.selectatt(atId);
		
		FoodmenuDTO m=FoodmenuDTO.builder()
				.fId("1111")
				.fmName("치즈")
				.fmPrice(1000)
				.build();
		List<FoodmenuDTO> mlist=new ArrayList<>();
		mlist.add(m);
		
		FoodmenuDTO mm=FoodmenuDTO.builder()
				.fId("1111")
				.fmName("치즈2")
				.fmPrice(10030)
				.build();
		mlist.add(mm);
		
		FoodDTO f=FoodDTO.builder()
				.fId("1111")
				.fName("한식당")
				.fUrl("C:\\Users\\hu-15\\Downloads\\mug_obj_149006360290199937.jpg")
				.menuDTOList(mlist)
				.build();

		ImageIcon icon = new ImageIcon(f.getFUrl());
		JLabel lb1 = new JLabel(icon, JLabel.CENTER); //가운데로 수평정렬
		foodp.add(lb1);
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setFont(name.getFont().deriveFont(30.0f));
		name.setForeground(Color.BLACK);
		name.setText(f.getFName());
		this.add("North",name);
		
		
		
		menup.setLayout(new GridLayout(5,1));
		for(FoodmenuDTO menu:f.getMenuDTOList()) {
			JPanel p=new JPanel();
			p.setLayout(new GridLayout(2,1,5,5));
			p.setBorder(new TitledBorder(new LineBorder(Color.BLACK,5)));
			p.add(new JLabel("이름: "+menu.getFmName()));
			p.add(new JLabel("가격: "+menu.getFmPrice()+"원"));
			menup.add(p);
		}
		
		
		
		
		
		contents.add(foodp);
		contents.add(menup);
		this.add(contents);
		this.setVisible(true);
		this.setLocationRelativeTo(null); // 화면 중앙 배치
		
		
		addWindowListener (
			    new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			        	before.setEnabled(true);
			        }
			    }
			);
	}
}

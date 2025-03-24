package amuse;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class SignIn extends JFrame implements ActionListener{
	
	JLabel title=new JLabel("회원가입");
	JLabel i_label=new JLabel("id");
	JLabel p_label=new JLabel("pw");
	JLabel n_label=new JLabel("name");
	JLabel g_label=new JLabel("gender");
	JLabel b_label=new JLabel("birth");
	JTextField i_field=new JTextField(15);
	JTextField p_field=new JTextField(25);
	JTextField n_field=new JTextField(25);
	ButtonGroup g_radio=new ButtonGroup();
	UtilDateModel model = new UtilDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model);
	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	JButton submit=new JButton("가입");
	JButton ck=new JButton("중복검사");
	JPanel center=new JPanel();
	
	boolean idck=false; //중복체크시 true
	
	SignIn(JFrame before){
		this.setSize(400,300);
		this.setVisible(true);
		this.setLocationRelativeTo(null); // 화면 중앙 배치
		this.setTitle("회원가입");
		this.add(center);
		

		JRadioButton m=new JRadioButton("남자");
		JRadioButton f=new JRadioButton("여자");
		g_radio.add(m);
		g_radio.add(f);
		JPanel radio=new JPanel();
		radio.setLayout(new GridLayout());
		radio.add(m);
		radio.add(f);


		
		center.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
        c.gridy = 1;
		center.add(i_label,c);
		
		
		c.gridx = 1;
        c.gridy = 1;
		center.add(i_field,c);
		
		c.gridx = 2;
        c.gridy = 1;
        center.add(ck,c);
		
		//c.fill = 0;
		c.gridx = 0;
        c.gridy = 2;
		center.add(p_label,c);
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
        c.gridy = 2;
        c.gridwidth=2;
		center.add(p_field,c);
		
		//c.fill = 0;
		c.gridx = 0;
        c.gridy = 3;
        c.gridwidth=1;
		center.add(n_label,c);
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
        c.gridy = 3;
        c.gridwidth=2;
		center.add(n_field,c);
		
		//c.fill = 0;
		c.gridx = 0;
        c.gridy = 4;
        c.gridwidth=1;
		center.add(g_label,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
        c.gridy = 4;
		center.add(radio,c);
		
		//c.fill = 0;
		c.gridx = 0;
        c.gridy = 5;
		center.add(b_label,c);
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
        c.gridy = 5;
        c.gridwidth=2;
		center.add(datePicker,c);
		
        

		c.gridx = 0;
        c.gridy = 6;
        c.gridwidth=3;
        center.add(submit,c);
        
        submit.addActionListener(this);
        ck.addActionListener(this);
		
		addWindowListener (
			    new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			        	before.setEnabled(true);
			        }
			    }
			);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ck) {
			
		}
		if(e.getSource()==submit) {
			if(idck) {
				
			}else {
				JOptionPane.showMessageDialog(null, "중복체크필수"); 
			}
		}
	}
}

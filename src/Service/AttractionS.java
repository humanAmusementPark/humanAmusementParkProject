package javaproject.Service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javaproject.DAO.AttractionDAO;
import javaproject.DTO.AttractionDTO;

public class AttractionS extends JFrame{
	AttractionDAO dao=new AttractionDAO();
	String[] col={"아이디","이름","이미지","최대인원","운영여부"};
	DefaultTableModel model=new DefaultTableModel(col,0){
		// Jtable 내용 편집 안되게
		public boolean isCellEditable(int i, int c) {
			if(c<2){
				return false;
			}
			return true;
		}
	};
	JTable t=new JTable(model);
	JButton update=new JButton("수정");

	public AttractionS(JFrame before) {
		this.setSize(600, 400);
		this.setTitle("시설관리");

		modelset();

		JScrollPane p=new JScrollPane(t);
		p.setSize(600,400);
		add(p);
		t.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		t.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		t.getColumn("이름").setPreferredWidth(100);
		t.getColumn("이미지").setPreferredWidth(200);
		t.getColumn("최대인원").setPreferredWidth(50);
		t.getColumn("운영여부").setPreferredWidth(50);

		JPanel p2=new JPanel();

		p2.add(update);

		update.addActionListener (new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String id=(String)model.getValueAt(t.getSelectedRow(), 0);
					String name=(String)model.getValueAt(t.getSelectedRow(), 1);
					String image=(String)model.getValueAt(t.getSelectedRow(), 2);
					int max=(int)model.getValueAt(t.getSelectedRow(), 3);
					int flag=-1;

					String s=(String)model.getValueAt(t.getSelectedRow(), 4);
					if(s.equals("off"))
						flag=0;
					else if(s.equals("on"))
						flag=1;
					else
						throw new Exception();


					AttractionDTO att = AttractionDTO.builder()
							.atId(id)
							.atName(name)
							.atUrl(image)
							.atMax(max)
							.atOnoff(flag)
							.build();
					if(dao.update(att))
						JOptionPane.showMessageDialog(null, "수정완료.");
					else
						JOptionPane.showMessageDialog(null, "수정실패.");
					modelset();

				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "정확히 입력해주세요.");
				}


			}
		});

		add("South",p2);
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

	private void modelset() {
		model.setRowCount(0);
		List<AttractionDTO> alist =dao.selectAll();

		for(AttractionDTO a:alist) {
			model.addRow(new Object[] {
					a.getAtId(),a.getAtName(),a.getAtUrl(),a.getAtMax(),a.getAtOnoff()==0 ? "off":"on"
			});
		}

	}
}
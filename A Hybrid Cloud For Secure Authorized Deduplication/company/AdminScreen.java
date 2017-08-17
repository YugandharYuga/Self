package hybridcloud;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
public class AdminScreen extends JFrame
{
	GradientPanel p1;
	JPanel p2;
	JLabel l1,l2;
	JButton b1,b2,b3,b4;
	Font f1;
	Login login;
public AdminScreen(Login log){
	super("Admin Screen Modules");
	login = log;
	p1 = new GradientPanel(600,200);
	p1.setLayout(null);
	
	f1 = new Font("Courier New",Font.BOLD,13);
	p2 = new JPanel();
	p2.setBackground(new Color(204, 110, 155));
	l1 = new JLabel("<HTML><BODY><CENTER>A HYBRID CLOUD APPROACH FOR SECURE AUTHORIZED<BR/>DEDUPLICATION</CENTER></BODY></HTML>");
	l1.setFont(new Font("Courier New",Font.BOLD,16));
	p2.add(l1);

	l2 = new JLabel("Add Employees Screen");
	l2.setFont(new Font("Courier New",Font.BOLD,18));
	l2.setBounds(180,20,300,30);
	p1.add(l2);

	

	b1 = new JButton("Add Employee");
	b1.setFont(f1);
	b1.setBounds(180,110,240,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Register r = new Register();
			r.setVisible(true);
			r.setSize(500,600);
		}
	});
	
	b2 = new JButton("Logout");
	b2.setFont(f1);
	b2.setBounds(180,160,240,30);
	p1.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
			login.clear();
			login.setVisible(true);
		}
	});

	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(p2,BorderLayout.NORTH);
}

}
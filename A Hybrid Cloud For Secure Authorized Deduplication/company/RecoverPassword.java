package hybridcloud;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
public class RecoverPassword extends JFrame
{
	GradientPanel p1;
	JPanel p2;
	JLabel l1,l2,title,l3;
	JTextField tf1,tf2;
	JButton b1,b2,b3;
	Font f1;
public RecoverPassword(){
	super("Recover Password Screen");
	p1 = new GradientPanel(600,200);
	p1.setLayout(null);
	
	f1 = new Font("Courier New",Font.BOLD,14);
	p2 = new JPanel();
	p2.setBackground(new Color(204, 110, 155));
	title = new JLabel("<HTML><BODY><CENTER>A HYBRID CLOUD APPROACH FOR SECURE AUTHORIZED<BR/>DEDUPLICATION</CENTER></BODY></HTML>");
	title.setFont(new Font("Courier New",Font.BOLD,16));
	p2.add(title);

	l3 = new JLabel("Recover Password Screen");
	l3.setFont(new Font("Courier New",Font.BOLD,18));
	l3.setBounds(170,20,350,30);
	p1.add(l3);

	l1 = new JLabel("Username");
	l1.setFont(f1);
	l1.setBounds(200,60,100,30);
	p1.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	tf1.setBounds(300,60,130,30);
	p1.add(tf1);
	
	l2 = new JLabel("Email ID");
	l2.setFont(f1);
	l2.setBounds(200,110,100,30);
	p1.add(l2);
	tf2 = new JTextField(15);
	tf2.setFont(f1);
	tf2.setBounds(300,110,130,30);
	p1.add(tf2);

	JPanel pan3 = new JPanel(); 
	b1 = new JButton("Recover");
	b1.setFont(f1);
	b1.setBounds(200,160,110,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			recover();
		}
	});
	b2 = new JButton("Reset");
	b2.setFont(f1);
	b2.setBounds(320,160,80,30);
	p1.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			tf1.setText("");
			tf2.setText("");
		}
	});

	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(p2,BorderLayout.NORTH);
}
public void clear(){
	tf1.setText("");
	tf2.setText("");
}
public void recover(){
	String user = tf1.getText();
	String email = tf2.getText();
	
	if(user == null || user.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Username must be enter");
		tf1.requestFocus();
		return;
	}
	if(email == null || email.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Email ID must be enter");
		tf2.requestFocus();
		return;
	}
	if(!CheckMail.checkMail(email)){
		JOptionPane.showMessageDialog(this,"Enter valid mailid");
		tf2.requestFocus();
		return;
	}
	try{
		String msg = DBCon.recover(user,email);
		if(!msg.equals("fail")){
			JOptionPane.showMessageDialog(this,"Your password : "+msg);
		}
		if(msg.equals("fail")){
			JOptionPane.showMessageDialog(this,"invalid user or email id");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

}
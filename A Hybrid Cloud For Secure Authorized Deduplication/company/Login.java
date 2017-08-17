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
public class Login extends JFrame
{
	GradientPanel p1;
	JPanel p2;
	JLabel l1,l2,title,l3;
	JTextField tf1,tf2;
	JButton b1,b2,b3;
	Font f1;
public Login(){
	super("Login Screen");
	p1 = new GradientPanel(600,200);
	p1.setLayout(null);
	
	f1 = new Font("Courier New",Font.BOLD,13);
	p2 = new JPanel();
	p2.setBackground(new Color(204, 110, 155));
	title = new JLabel("<HTML><BODY><CENTER>A HYBRID CLOUD APPROACH FOR SECURE AUTHORIZED<BR/>DEDUPLICATION</CENTER></BODY></HTML>");
	title.setFont(new Font("Courier New",Font.BOLD,16));
	p2.add(title);

	l3 = new JLabel("Login Screen");
	l3.setFont(new Font("Courier New",Font.BOLD,18));
	l3.setBounds(250,20,200,30);
	p1.add(l3);

	l1 = new JLabel("Username");
	l1.setFont(f1);
	l1.setBounds(200,60,100,30);
	p1.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	tf1.setBounds(300,60,130,30);
	p1.add(tf1);
	
	l2 = new JLabel("Password");
	l2.setFont(f1);
	l2.setBounds(200,110,100,30);
	p1.add(l2);
	tf2 = new JPasswordField(15);
	tf2.setFont(f1);
	tf2.setBounds(300,110,130,30);
	p1.add(tf2);

	JPanel pan3 = new JPanel(); 
	b1 = new JButton("Login");
	b1.setFont(f1);
	b1.setBounds(220,160,80,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			login();
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

	b2 = new JButton("Forgot Password");
	b2.setFont(f1);
	b2.setBounds(240,210,180,30);
	p1.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			RecoverPassword recover = new RecoverPassword();
			recover.setVisible(true);
			recover.setSize(600,360);
			recover.setLocationRelativeTo(null);
			recover.setResizable(false);
		}
	});

	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(p2,BorderLayout.NORTH);
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	Login login = new Login();
	login.setVisible(true);
	login.setSize(600,360);
	login.setLocationRelativeTo(null);
	login.setResizable(false);
}
public void clear(){
	tf1.setText("");
	tf2.setText("");
}
public void login(){
	String user = tf1.getText();
	String pass = tf2.getText();
	
	if(user == null || user.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Username must be enter");
		tf1.requestFocus();
		return;
	}
	if(pass == null || pass.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Password must be enter");
		tf2.requestFocus();
		return;
	}
	boolean flag = false;
	try{
		if(user.equals("admin") && pass.equals("admin")){
			setVisible(false);
			AdminScreen screen = new AdminScreen(this);
			screen.setVisible(true);
			screen.setSize(600,450);
			flag=true;
		}
		if(!flag){
			String input[] = {user,pass};
			String msg = DBCon.login(input);
			if(!msg.equals("fail")){
				setVisible(false);
				UserScreen us = new UserScreen(this,user,msg);
				us.setVisible(true);
				us.setSize(600,400);
			}
			if(msg.equals("fail")){
				JOptionPane.showMessageDialog(this,"invalid user");
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

}
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
import javax.swing.JComboBox;
public class Register extends JFrame
{
	GradientPanel p1;
	JPanel p2;
	JLabel l1,l2,l3,l4,title,l5,l6,l7,l9;
	JTextField tf1,tf2,tf3,tf4,tf5,tf6;
	JComboBox tf7;
	JButton b1,b2,b3;
	Font f1;
public Register(){
	super("Employee Registration Screen");
	p1 = new GradientPanel(600,200);
	p1.setLayout(null);
	
	f1 = new Font("Courier New",Font.BOLD,14);
	p2 = new JPanel();
	p2.setBackground(new Color(204, 110, 155));
	title = new JLabel("<HTML><BODY><CENTER>A HYBRID CLOUD APPROACH FOR SECURE AUTHORIZED<BR/>DEDUPLICATION</CENTER></BODY></HTML>");
	title.setFont(new Font("Courier New",Font.BOLD,16));
	p2.add(title);

	l9 = new JLabel("Registration Screen");
	l9.setFont(new Font("Courier New",Font.BOLD,18));
	l9.setBounds(140,20,300,30);
	p1.add(l9);

	f1 = new Font("Courier New",Font.BOLD,14);
	
	l1 = new JLabel("Username");
	l1.setBounds(120,60,100,30);
	l1.setFont(f1);
	p1.add(l1);
	tf1 = new JTextField(15);
	tf1.setBounds(220,60,130,30);
	tf1.setFont(f1);
	p1.add(tf1);
	
	
	l2 = new JLabel("Password");
	l2.setBounds(120,110,100,30);
	l2.setFont(f1);
	p1.add(l2);
	tf2 = new JPasswordField(15);
	tf2.setBounds(220,110,130,30);
	tf2.setFont(f1);
	p1.add(tf2);

	
	l3 = new JLabel("Confirm Password");
	l3.setBounds(70,160,150,30);
	l3.setFont(f1);
	p1.add(l3);
	tf3 = new JPasswordField(15);
	tf3.setBounds(220,160,130,30);
	tf3.setFont(f1);
	p1.add(tf3);

	l4 = new JLabel("Contact No");
	l4.setBounds(120,210,150,30);
	l4.setFont(f1);
	p1.add(l4);
	tf4 = new JTextField(15);
	tf4.setBounds(220,210,130,30);
	tf4.setFont(f1);
	p1.add(tf4);

	l5 = new JLabel("Email ID");
	l5.setBounds(120,260,150,30);
	l5.setFont(f1);
	p1.add(l5);
	tf5 = new JTextField(15);
	tf5.setBounds(220,260,130,30);
	tf5.setFont(f1);
	p1.add(tf5);

	l6 = new JLabel("Address");
	l6.setBounds(120,310,150,30);
	l6.setFont(f1);
	p1.add(l6);
	tf6 = new JTextField(15);
	tf6.setBounds(220,310,130,30);
	tf6.setFont(f1);
	p1.add(tf6);

	l7 = new JLabel("Designation");
	l7.setBounds(120,360,150,30);
	l7.setFont(f1);
	p1.add(l7);
	tf7 = new JComboBox();
	tf7.addItem("Director");
	tf7.addItem("Team Leader");
	tf7.addItem("Engineer");
	tf7.setBounds(220,360,130,30);
	tf7.setFont(f1);
	p1.add(tf7);

	b1 = new JButton("Register");
	b1.setFont(f1);
	b1.setBounds(140,460,100,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			register();
		}
	});
	b2 = new JButton("Reset");
	b2.setFont(f1);
	b2.setBounds(260,460,80,30);
	p1.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			clear();
		}
	});

	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(p2,BorderLayout.NORTH);
}
public void clear(){
	tf1.setText("");
	tf2.setText("");
	tf3.setText("");
	tf4.setText("");
	tf5.setText("");
	tf6.setText("");
}
public void register(){
	String user = tf1.getText();
	String pass = tf2.getText();
	String cpass = tf3.getText();
	String contact = tf4.getText();
	String email = tf5.getText();
	String address = tf6.getText();
	String desg = tf7.getSelectedItem().toString();
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
	if(cpass == null || cpass.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Confirm Password must be enter");
		tf3.requestFocus();
		return;
	}
	if(!pass.equals(cpass)){
		JOptionPane.showMessageDialog(this,"Password and Confirm Password must be same");
		tf3.requestFocus();
		return;
	}
	if(contact == null || contact.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Contact no must be enter");
		tf4.requestFocus();
		return;
	}
	if(email == null || email.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Email id must be enter");
		tf5.requestFocus();
		return;
	}
	if(!CheckMail.checkMail(email)){
		JOptionPane.showMessageDialog(this,"Enter valid mailid");
		tf5.requestFocus();
		return;
	}
	if(address == null || address.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Address must be enter");
		tf6.requestFocus();
		return;
	}
	
	try{
		String input[] = {user,pass,cpass,contact,email,address,desg};
		String msg = DBCon.register(input);
		if(msg.equals("success")){
			JOptionPane.showMessageDialog(this,"Registration Process Completed");
			clear();
		}else{
			JOptionPane.showMessageDialog(this,"Error in registration");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

}
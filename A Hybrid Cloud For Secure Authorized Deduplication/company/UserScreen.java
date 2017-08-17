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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class UserScreen extends JFrame
{
	GradientPanel p1;
	JPanel p2;
	JLabel l1,l2;
	JButton b1,b2,b3,b4;
	Font f1;
	Login login;
	String user;
	JComboBox c1;
	String access;
	JFileChooser chooser;
	StringBuilder sb = new StringBuilder();
	byte filedata[] = null;
	File file;
public UserScreen(Login log,String usr,String ac){
	super("User Screen Modules");
	login = log;
	user = usr;
	access = ac;
	p1 = new GradientPanel(600,200);
	p1.setLayout(null);
	
	chooser = new JFileChooser();
	f1 = new Font("Courier New",Font.BOLD,13);
	p2 = new JPanel();
	p2.setBackground(new Color(204, 110, 155));
	l1 = new JLabel("<HTML><BODY><CENTER>A HYBRID CLOUD APPROACH FOR SECURE AUTHORIZED<BR/>DEDUPLICATION</CENTER></BODY></HTML>");
	l1.setFont(new Font("Courier New",Font.BOLD,16));
	p2.add(l1);

	l2 = new JLabel("User Screen");
	l2.setFont(new Font("Courier New",Font.BOLD,18));
	l2.setBounds(280,20,300,30);
	p1.add(l2);

	

	b1 = new JButton("Choose File");
	b1.setFont(f1);
	b1.setBounds(180,110,120,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(UserScreen.this);
			if(option == JFileChooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				try{
					FileInputStream fin = new FileInputStream(file);
					filedata = new byte[fin.available()];
					fin.read(filedata,0,filedata.length);
					fin.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(UserScreen.this,"File Loaded");
			}
		}
	});

	c1 = new JComboBox();
	c1.addItem("Access Permission");
	c1.addItem("Director");
	c1.addItem("Team Leader");
	c1.addItem("Engineer");
	c1.setBounds(300,110,140,30);
	c1.setFont(f1);
	p1.add(c1);
	c1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			String acc = c1.getSelectedItem().toString().trim();
			if(!acc.equals("Access Permission"))
				sb.append(acc+"#");
		}
	});

	b2 = new JButton("Upload File");
	b2.setFont(f1);
	b2.setBounds(440,110,120,30);
	p1.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			if(filedata != null && sb.toString().length() > 0){
				sb.deleteCharAt(sb.length()-1);
				String permission = sb.toString().trim();
				sb.delete(0,sb.length());
				try{
					Socket socket = new Socket("localhost",5555);
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					Object req[] = {"generatekey",user,user+"/"+file.getName(),permission,filedata};
					out.writeObject(req);
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					Object res[] = (Object[])in.readObject();
					if(res[0].toString().equals("key")){
						String key = (String)res[1];
						String token = (String)res[2];
						if(token.equals("none")){
							byte enc[] = Encryption.encrypt(filedata,key);
							Socket socket1 = new Socket("localhost",6666);
							ObjectOutputStream out1 = new ObjectOutputStream(socket1.getOutputStream());
							Object req1[] = {"upload",user,file.getName(),enc};
							out1.writeObject(req1);
							ObjectInputStream in1 = new ObjectInputStream(socket1.getInputStream());
							Object res1[] = (Object[])in1.readObject();
							if(res1[0].toString().equals("success")){
								JOptionPane.showMessageDialog(UserScreen.this,"File sent to cloud server");
							}else{
								JOptionPane.showMessageDialog(UserScreen.this,"Error in sending file to cloud server");
							}
						}else{
							JOptionPane.showMessageDialog(UserScreen.this,"Duplicate file exists on given location "+token);
						}
					}else{
						JOptionPane.showMessageDialog(UserScreen.this,"No response received from private cloud");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(UserScreen.this,"Choose file and access permission");
			}
		}
	});

	b3 = new JButton("Download File");
	b3.setFont(f1);
	b3.setBounds(180,160,140,30);
	p1.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			DownloadFile df=new DownloadFile(user,access);
			df.getFileName();
			df.pack();
			df.setLocationRelativeTo(null);
			df.setVisible(true);
		}
	});
	
	b4 = new JButton("Logout");
	b4.setFont(f1);
	b4.setBounds(180,210,120,30);
	p1.add(b4);
	b4.addActionListener(new ActionListener(){
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
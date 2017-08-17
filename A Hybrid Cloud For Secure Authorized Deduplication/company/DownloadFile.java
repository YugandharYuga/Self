package hybridcloud;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.awt.FlowLayout;
import java.io.FileOutputStream;
import javax.swing.JTextArea;
import java.awt.Color;
import java.io.File;
import javax.swing.JOptionPane;
public class DownloadFile extends JFrame
{
	JLabel l1;
	JButton b1;
	JComboBox c1;
	String user;
	String files[];
	String keys[];
	String designation;
public DownloadFile(String usr,String des){
	user = usr;
	designation = des;
	setTitle("Download File");
	getContentPane().setBackground(Color.white);
	getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

	l1 = new JLabel("File Name");
	getContentPane().add(l1);

	c1 = new JComboBox();
	getContentPane().add(c1);

	b1 = new JButton("Download");
	getContentPane().add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try{
				String file = c1.getSelectedItem().toString();
				String key = keys[c1.getSelectedIndex()];
				Socket socket=new Socket("localhost",6666);
				ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
				Object req[]={"download",file};
				file = file.substring(file.lastIndexOf("/")+1,file.length());
				System.out.println(file+" filename");
				out.writeObject(req);
				out.flush();
				Object res[]=(Object[])in.readObject();
				byte fdata[] = (byte[])res[0];
				FileOutputStream fout=new FileOutputStream("D:/"+file);
				byte data[] = Encryption.decrypt(fdata,key);
				fout.write(data,0,data.length);
				fout.flush();
				fout.close();
				JOptionPane.showMessageDialog(DownloadFile.this,"Selected file downloaded in 'System D' directory");
				setVisible(false);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	});
}
public void getFileName(){
	try{
		c1.removeAllItems();
		Socket socket=new Socket("localhost",5555);
        ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
		Object req[]={"search",user,designation};
		out.writeObject(req);
		out.flush();
		Object res[]=(Object[])in.readObject();
		files =(String[])res[1];
		keys =(String[])res[2];
		for(int i=0;i<files.length;i++){
			c1.addItem(files[i]);
		}
		out.close();
		in.close();
		socket.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
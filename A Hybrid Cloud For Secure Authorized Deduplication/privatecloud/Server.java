package server;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.net.Socket;
import java.net.ServerSocket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
public class Server extends JFrame{
	ServerProcess thread;
	JPanel p1,p2,p3;
	JLabel l1;
	JScrollPane jsp;
	JTextArea area;
	Font f1,f2;
	ServerSocket server;
	Socket socket;
public void start(){
	try{
		server = new ServerSocket(5555);
		area.append("Private Cloud Server Started\n\n");
		while(true){
			socket = server.accept();
			socket.setKeepAlive(true);
			InetAddress address=socket.getInetAddress();
			String ipadd=address.toString();
			area.append("Connected Computers :"+ipadd.substring(1,ipadd.length())+"\n");
			thread=new ServerProcess(socket,area);
			thread.start();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public Server(){
	setTitle("Private Cloud Server");
	f1 = new Font("Courier New",Font.BOLD,16);
    p1 = new JPanel();
    l1 = new JLabel("<HTML><BODY><CENTER>A HYBRID CLOUD APPROACH FOR SECURE AUTHORIZED<BR/>DEDUPLICATION</CENTER></BODY></HTML>");
	l1.setFont(this.f1);
    l1.setForeground(Color.white);
    p1.add(l1);
	p1.setBackground(new Color(104, 211, 55));

    f2 = new Font("Courier New", 1, 13);
    p2 = new JPanel();
    p2.setLayout(new BorderLayout());
    area = new JTextArea();
    area.setFont(f2);
    jsp = new JScrollPane(area);
    area.setEditable(false);
    p2.add(jsp);

	
	
	getContentPane().add(p1, "North");
    getContentPane().add(p2, "Center");
	addWindowListener(new WindowAdapter(){
            @Override
        public void windowClosing(WindowEvent we){
            try{
				if(socket != null){
					socket.close();
				}
             server.close();
            }catch(Exception e){
                //e.printStackTrace();
            }
        }
    });
}
public static void main(String a[])throws Exception	{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	Server server = new Server();
	server.setVisible(true);
	server.setSize(800,600);
	new ServerThread(server);
}

}
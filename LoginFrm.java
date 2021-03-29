package cn.edu.jsu.xq.sms.frm;
/**
 *
 */

import cn.edu.jsu.xq.sms.frm.manage.MainManageFrm;
import cn.edu.jsu.xq.sms.frm.stu.Stufrm;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 登录界面主窗口
 */
public class LoginFrm  {

	private static JFrame jf=null;
	private JPanel contentPane;
	private JTextField textFieldid;
	private JPasswordField jPasswordFieldmm;
	private static Socket soc=null;//初始化客户端socket,向服务器请求数据

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrm lf=new LoginFrm();
					lf.jf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 获取连接
	 * @return
	 */
	public static Socket getSocket(){
		if (soc==null){
			try{
				soc=new Socket("127.0.0.1",8888);//连接服务器，建立TCP
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return soc;
	}

	/**
	 * 关闭socket
	 */
	public static void socketClose(){
		//关闭连接
		if (soc!=null){
			try {
				soc.close();
				soc=null;//将soc设置为空
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Create the frame.
	 */
	public LoginFrm() {
		jf=new JFrame("登录界面");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(450,300);
		jf.setLocationRelativeTo(null);
		jf.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\java\\code\\src\\cn\\edu\\jsu\\xq\\sms\\source\\e13.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jf.setContentPane(contentPane);
		contentPane.setLayout(null);

		//设置背景图
		ImageIcon img=new ImageIcon("D:\\java\\code\\src\\cn\\edu\\jsu\\xq\\sms\\source\\loginBG.png");
		JLabel lable=new JLabel(img);
		jf.getLayeredPane().setLayout(null);
		lable.setBounds(0, 0, 450, 300);
		jf.getLayeredPane().add(lable,new Integer(Integer.MIN_VALUE));
		//JPanel j=(JPanel)jf.getContentPane();
		contentPane.setOpaque(false);


		JLabel lblbiaoti = new JLabel("欢迎使用学生信息管理系统");
		lblbiaoti.setFont(new Font("Adobe 仿宋 Std R", Font.PLAIN, 31));
		lblbiaoti.setHorizontalAlignment(SwingConstants.CENTER);
		lblbiaoti.setBounds(21, 32, 385, 63);
		contentPane.add(lblbiaoti);

		JLabel lblid = new JLabel("账号 ：");
		lblid.setBounds(75, 93, 89, 27);
		contentPane.add(lblid);

		JLabel lblmm = new JLabel("密码 ：");
		lblmm.setBounds(75, 139, 89, 27);
		contentPane.add(lblmm);

		textFieldid = new JTextField();
		textFieldid.setBounds(157, 96, 188, 21);
		contentPane.add(textFieldid);
		textFieldid.setColumns(10);

		jPasswordFieldmm = new JPasswordField();
		jPasswordFieldmm.setBounds(157, 142, 188, 21);
		contentPane.add(jPasswordFieldmm);
		jPasswordFieldmm.setColumns(10);

		JRadioButton jRadioButtonT=new JRadioButton("管理员",false);
		JRadioButton jRadioButtonS=new JRadioButton("学生",true);
		jRadioButtonS.setBorder(null);
		jRadioButtonS.setContentAreaFilled(false);
		jRadioButtonT.setBorder(null);
		jRadioButtonT.setContentAreaFilled(false);
		ButtonGroup bg=new ButtonGroup();
		bg.add(jRadioButtonT);
		bg.add(jRadioButtonS);
		jRadioButtonT.setBounds(140, 166, 80, 20);
		jRadioButtonS.setBounds(216, 166, 60, 20);
		contentPane.add(jRadioButtonT);
		contentPane.add(jRadioButtonS);

		JButton btndl = new JButton("登录");
		btndl.setBounds(93, 197, 97, 23);
		contentPane.add(btndl);
		btndl.addActionListener(e -> {
			OutputStream os=null;
			InputStream is=null;
			try{
				String id=textFieldid.getText();
				String password=new String(jPasswordFieldmm.getPassword());
				if (id.length()==0 || password.length()==0){
					//判断id与password是否为空
					JOptionPane.showMessageDialog(null,"账号或密码不能为空");
				}else{
					soc=getSocket();
					os = soc.getOutputStream();//获取输出流对象，发给服务器端数据
					is = soc.getInputStream();//获取输入流对象，接收服务器端的数据

					if (jRadioButtonS.isSelected()){
						//判断账号密码，是否为学生账号
						String message="登录  "+id+" "+password+" "+"0";
						os.write(message.getBytes());//发消息

						byte[] b=new byte[1024*8];
						int len = is.read(b);
						String result=new String(b,0,len);//接收结果


						//为真打开学生界面
						if ("true".equals(result)){
							JOptionPane.showMessageDialog(null,"学生登录成功");
							Stufrm.getInstance().setVisible(true);
							jf.dispose();
						}else{
							JOptionPane.showMessageDialog(null,"错误，请进行修改");
						}

					}else if (jRadioButtonT.isSelected()){
						//判断账号密码，是否为管理员账号
						String message="登录  "+id+" "+password+" "+"1";
						os.write(message.getBytes());//发消息

						byte[] b=new byte[1024*8];
						int len = is.read(b);
						String result=new String(b,0,len);//接收结果

						//为真打开管理员页面
						if ("true".equals(result)){
							JOptionPane.showMessageDialog(null,"管理员登录成功");
							MainManageFrm.getInstance().setVisible(true);
							jf.dispose();
						}else{
							JOptionPane.showMessageDialog(null,"错误，请进行修改");
						}
					}
					is.close();
					os.close();
					socketClose();
					//soc.close();//关闭流对象
				}

			}catch (Exception a){
				a.printStackTrace();
			}

		});

		JButton btnforget = new JButton("忘记密码");
		btnforget.setBounds(227, 197, 97, 23);
		//输出提示信息
		btnforget.addActionListener(e -> JOptionPane.showMessageDialog(null,"请联系管理员修改"));
		contentPane.add(btnforget);


	}

	/**
	 * 关闭窗口
	 */
	public static void close(){
		if (jf!=null)
		System.exit(0);
	}
}

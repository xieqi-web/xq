package cn.edu.jsu.xq.sms.frm.stu;

import cn.edu.jsu.xq.sms.dao.RewardDAO;
import cn.edu.jsu.xq.sms.dao.StudentDAO;
import cn.edu.jsu.xq.sms.frm.LoginFrm;
import cn.edu.jsu.xq.sms.vo.AccountNum;
import cn.edu.jsu.xq.sms.vo.Reward;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * 这是一个学生端的主界面
 * 学生进去的第一页
 * @author 罗自觐
 */

public class Stufrm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private  static Stufrm instance=null;
	private AccountNum accountNum=null;

	public static Stufrm getInstance() {
		if (instance==null){
			instance=new Stufrm();
		}
		return instance;
	}

	/**
	 * Create the dialog.
	 */
	private Stufrm() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\java\\code\\src\\cn\\edu\\jsu\\xq\\sms\\source\\e13.png"));
		//设置背景图
		ImageIcon img=new ImageIcon("");
		JLabel lable=new JLabel(img);
		this.getLayeredPane().setLayout(null);
		lable.setBounds(0, 0, 1000, 532);
		this.getLayeredPane().add(lable,new Integer(Integer.MIN_VALUE));
		JPanel j=(JPanel)this.getContentPane();
		j.setOpaque(false);

		setTitle("学生端界面");

		setBounds(100, 100, 1000, 532);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("欢迎你登录学生信息管理系统学生端");
		lblNewLabel.setBounds(0, 90, 986, 61);
		lblNewLabel.setFont(new Font("Adobe 宋体 Std L", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("亲爱的同学");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 24));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 161, 976, 61);
		getContentPane().add(lblNewLabel_1);


		//从文件中读出账号信息

		File file=new File("D:\\java\\project\\src\\cn\\edu\\jsu\\xq\\sms\\source\\当前登录的账号.txt");
		try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));){
			Object o = ois.readObject();
			accountNum=(AccountNum) o;
		}catch (Exception e){
			e.printStackTrace();
		}


		//获取账号对应的学生名并显示
		String nameBySno = StudentDAO.findNameBySno(accountNum.getId());
		String name=nameBySno;
		JLabel lblNewLabel_2 = new JLabel(name);
		lblNewLabel_2.setFont(new Font("Adobe 宋体 Std L", Font.BOLD | Font.ITALIC, 33));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(0, 256, 976, 73);
		getContentPane().add(lblNewLabel_2);

		JButton btnNewButton = new JButton("退出系统");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Stufrm.super.dispose();//退出当前界面
				JOptionPane.showMessageDialog(null,"退出成功");
				LoginFrm.close();//同时关闭登录窗口
			}
		});

		btnNewButton.setFont(new Font("宋体", Font.BOLD, 20));
		btnNewButton.setBounds(400, 339, 195, 33);
		getContentPane().add(btnNewButton);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnSignalMessage = new JMenu("个人信息");
		menuBar.add(mnSignalMessage);

		JMenuItem mntmLookMessage = new JMenuItem("查看个人信息");
		mntmLookMessage.addActionListener(e->{
			LookSingleMessage look = LookSingleMessage.getInstance(accountNum);
			look.setVisible(true);
		});
		mntmLookMessage.setHorizontalAlignment(SwingConstants.CENTER);
		mnSignalMessage.add(mntmLookMessage);

		JSeparator separator = new JSeparator();
		mnSignalMessage.add(separator);

		JMenuItem mntmUpdatePassword = new JMenuItem("修改密码");
		mntmUpdatePassword.addActionListener(e->{
			UpdatePassword instance = UpdatePassword.getInstance(accountNum);
			instance.setVisible(true);
		});
		mntmUpdatePassword.setHorizontalAlignment(SwingConstants.CENTER);
		mnSignalMessage.add(mntmUpdatePassword);

		JSeparator separator_1 = new JSeparator();
		mnSignalMessage.add(separator_1);

		JMenuItem mntmLookAward = new JMenuItem("奖惩情况");
		mntmLookAward.addActionListener(e->{
			LookRewardMessage.getInstance(accountNum).setVisible(true);
		});
		mntmLookAward.setHorizontalAlignment(SwingConstants.CENTER);
		mnSignalMessage.add(mntmLookAward);

		JSeparator separator_2 = new JSeparator();
		mnSignalMessage.add(separator_2);

		JMenuItem mntmLookCredit = new JMenuItem("个人学分");
		mntmLookCredit.addActionListener(e->{
			Reward rewardBySno = RewardDAO.findRewardBySno(accountNum.getId());
			Double gerenCredit = rewardBySno.getGerenCredit();
			JOptionPane.showMessageDialog(null,"你的总学分是"+gerenCredit);
		});
		mntmLookCredit.setHorizontalAlignment(SwingConstants.CENTER);
		mnSignalMessage.add(mntmLookCredit);

		JMenu mnCourseMessage = new JMenu("课程信息");
		menuBar.add(mnCourseMessage);

		JMenuItem mntmLookCourse = new JMenuItem("查看课程信息");
		mntmLookCourse.addActionListener(e->{
			LookCourseMessage.getInstance(accountNum).setVisible(true);
		});
		mntmLookCourse.setHorizontalAlignment(SwingConstants.CENTER);
		mnCourseMessage.add(mntmLookCourse);


		JSeparator separator_3 = new JSeparator();
		mnCourseMessage.add(separator_3);

		JMenuItem mntmSingalCourse = new JMenuItem("选课查询");
		mntmSingalCourse.addActionListener(e->{
			LookXuanXiuCourseMessage.getInstance(accountNum).setVisible(true);
		});
		mntmSingalCourse.setHorizontalAlignment(SwingConstants.CENTER);
		mnCourseMessage.add(mntmSingalCourse);

	}
}
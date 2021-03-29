package cn.edu.jsu.xq.sms.frm.stu;


import cn.edu.jsu.xq.sms.dao.StudentDAO;
import cn.edu.jsu.xq.sms.vo.AccountNum;
import cn.edu.jsu.xq.sms.vo.Student;

import javax.swing.*;
import java.awt.Font;

/**
 * 这是一个展示学生个人信息的一个弹窗
 * @author 罗自觐
 */

public class LookSingleMessage extends JDialog {
    private JTextField textFieldXh;
    private JTextField textFieldXm;
    private JTextField textFieldSsex;
    private JTextField textFieldAge;
    private JTextField textFieldXy;
    private JTextField textFieldTime;
    private JTextField textFieldClass;
    private static AccountNum accountNum=null;
    /**
     * 单例模式
     */
    private static LookSingleMessage instance=null;

    /**
     * 单例模式返回一个LookSingleMessage对象
     * @return {@code LookSingleMessage}
     */
    public static LookSingleMessage getInstance(AccountNum a){
        accountNum=a;
        if (instance==null){
            instance=new LookSingleMessage();
        }
        return instance;
    }


    /**
     * Create the dialog.
     * 该窗口显示当前登录学生的所有信息
     * @return
     */
    private LookSingleMessage() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("增加学生信息");
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        Student stu=getStudent(accountNum.getId());

        JLabel lblTitle = new JLabel("个人信息");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("宋体", Font.BOLD, 22));
        lblTitle.setBounds(39, 10, 350, 25);
        getContentPane().add(lblTitle);

        JLabel lblXh = new JLabel("学号");
        lblXh.setBounds(34, 45, 58, 15);
        getContentPane().add(lblXh);

        textFieldXh = new JTextField();
        textFieldXh.setText(stu.getSno().toString());
        textFieldXh.setBounds(142, 45, 202, 21);
        getContentPane().add(textFieldXh);
        textFieldXh.setColumns(10);

        JLabel lblXm = new JLabel("姓名");
        lblXm.setBounds(34, 84, 58, 15);
        getContentPane().add(lblXm);

        textFieldXm = new JTextField();
        textFieldXm.setText(stu.getSname());
        textFieldXm.setBounds(114, 81, 90, 21);
        getContentPane().add(textFieldXm);
        textFieldXm.setColumns(10);

        JLabel lblSsex = new JLabel("性别");
        lblSsex.setBounds(270, 84, 58, 15);
        getContentPane().add(lblSsex);

        textFieldSsex = new JTextField();
        textFieldSsex.setText(stu.getSsex());
        textFieldSsex.setBounds(310, 81, 90, 21);
        getContentPane().add(textFieldSsex);
        textFieldSsex.setColumns(10);

        JLabel lblAge = new JLabel("年龄");
        lblAge.setBounds(34, 120, 58, 15);
        getContentPane().add(lblAge);

        textFieldAge = new JTextField();
        textFieldAge.setText(stu.getSage().toString());
        textFieldAge.setBounds(114, 117, 90, 21);
        getContentPane().add(textFieldAge);
        textFieldAge.setColumns(10);

        JLabel lblXy = new JLabel("学院");
        lblXy.setBounds(270, 120, 58, 15);
        getContentPane().add(lblXy);

        textFieldXy = new JTextField();
        textFieldXy.setText(stu.getSxy());
        textFieldXy.setBounds(310, 117, 90, 21);
        getContentPane().add(textFieldXy);
        textFieldXy.setColumns(10);

        JLabel lblTime = new JLabel("入学时间");
        lblTime.setBounds(34, 152, 58, 15);
        getContentPane().add(lblTime);

        textFieldTime = new JTextField();
        textFieldTime.setText(stu.getSschooltime().toString());
        textFieldTime.setBounds(114, 149, 90, 21);
        getContentPane().add(textFieldTime);
        textFieldTime.setColumns(10);

        JLabel lblClass = new JLabel("班级");
        lblClass.setBounds(270, 152, 58, 15);
        getContentPane().add(lblClass);

        textFieldClass = new JTextField();
        textFieldClass.setText(stu.getSclass());
        textFieldClass.setBounds(310, 148, 90, 21);
        getContentPane().add(textFieldClass);
        textFieldClass.setColumns(10);

        JButton btnExit = new JButton("退出");
        btnExit.addActionListener(e->{
            LookSingleMessage.super.dispose();
            JOptionPane.showMessageDialog(null,"退出成功");
        });
        btnExit.setBounds(105, 209, 254, 23);
        getContentPane().add(btnExit);

    }

    /**
     * 这是一个根据id获取学生的个人信息的方法
     * @param id
     * @return {@code Student}返回一个Student对象
     */
    public Student getStudent(Integer id){
        return StudentDAO.findStudentBySno(id);
    }
}



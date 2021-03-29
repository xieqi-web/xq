package cn.edu.jsu.xq.sms.frm.stu;


import cn.edu.jsu.xq.sms.dao.RewardDAO;
import cn.edu.jsu.xq.sms.dao.StudentDAO;
import cn.edu.jsu.xq.sms.vo.AccountNum;
import cn.edu.jsu.xq.sms.vo.Reward;

import javax.swing.*;
import java.awt.Font;

/**
 * 这是查看获奖情况的窗口
 */
public class LookRewardMessage extends JDialog {
    private static AccountNum accountNum=null;
    private static LookRewardMessage instance=null;
    public static LookRewardMessage getInstance(AccountNum a){
        accountNum=a;
        if (instance==null){
            instance=new LookRewardMessage();
        }
        return instance;
    }
    /**
     * Create the dialog.
     */
    private LookRewardMessage() {
        setTitle("学生获奖情况");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(400, 350, 550, 360);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(44, 62, 436, 204);
        getContentPane().add(scrollPane);

        JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        //获取reward对象
        Reward r= RewardDAO.findRewardBySno(accountNum.getId());
        String rewardMessage = r.getRewardMessage();
        //将内容加到JTextArea中
        String[] split = rewardMessage.split("#");
        for (int i = 2; i < split.length; i++) {
            textArea.append(split[i]+"\r\n");
        }



        String title= StudentDAO.findNameBySno(accountNum.getId());
        JLabel lblTitle = new JLabel(title+"同学个人奖惩情况");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Adobe 宋体 Std L", Font.BOLD, 20));
        lblTitle.setBounds(57, 10, 401, 42);
        getContentPane().add(lblTitle);

        JButton btnExit = new JButton("退出");
        btnExit.addActionListener(e->{
            JOptionPane.showMessageDialog(null,"退出成功");
            LookRewardMessage.super.dispose();
        });
        btnExit.setBounds(109, 288, 319, 23);
        getContentPane().add(btnExit);

    }
}


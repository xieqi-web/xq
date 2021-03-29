package cn.edu.jsu.xq.sms.frm.stu;


import cn.edu.jsu.xq.sms.dao.AccountNumDAO;
import cn.edu.jsu.xq.sms.vo.AccountNum;

import javax.swing.*;

/**
 * 更新密码的窗口
 */
public class UpdatePassword extends JDialog {
    private JTextField textFieldOld;
    private JTextField textFieldNew01;
    private JTextField textFieldNew02;
    private static AccountNum accountNum=null;
    private static UpdatePassword instance=null;
    public static UpdatePassword getInstance(AccountNum a){
        accountNum=a;
        if (instance==null){
            instance=new UpdatePassword();
        }
        return instance;
    }

    /**
     * Create the dialog.
     */
    private UpdatePassword() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("修改密码");
        setBounds(100, 100, 260, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblOld = new JLabel("旧密码：");
        lblOld.setBounds(10, 33, 58, 15);
        getContentPane().add(lblOld);

        textFieldOld = new JTextField();
        textFieldOld.setBounds(95, 30, 110, 21);
        getContentPane().add(textFieldOld);
        textFieldOld.setColumns(10);

        JLabel lblNew01 = new JLabel("新密码：");
        lblNew01.setBounds(10, 82, 58, 15);
        getContentPane().add(lblNew01);

        textFieldNew01 = new JTextField();
        textFieldNew01.setBounds(95, 79, 110, 21);
        getContentPane().add(textFieldNew01);
        textFieldNew01.setColumns(10);

        JLabel lblNew02 = new JLabel("确认密码");
        lblNew02.setBounds(10, 126, 58, 15);
        getContentPane().add(lblNew02);

        textFieldNew02 = new JTextField();
        textFieldNew02.setBounds(95, 123, 110, 21);
        getContentPane().add(textFieldNew02);
        textFieldNew02.setColumns(10);

        JButton btnOk = new JButton("确认修改");
        btnOk.addActionListener(e->{
            boolean flag=alterPassword();
            if (flag){
                JOptionPane.showMessageDialog(null,"修改成功");
            }else{
                JOptionPane.showMessageDialog(null,"修改失败，请检查");
            }
        });
        btnOk.setBounds(41, 182, 136, 23);
        getContentPane().add(btnOk);

    }

    /**
     * 该方法返回是否修改成功
     * @return {@code boolean}
     */
    private boolean alterPassword() {
        if (textFieldOld.getText()!=null && textFieldNew01!=null && textFieldNew02!=null){
            if (textFieldNew01.getText().equals(textFieldNew02.getText())
                    && textFieldOld.getText().equals(accountNum.getPassword())){
                accountNum.setPassword(textFieldNew01.getText());
                return AccountNumDAO.doUpdatePassword(accountNum);
            }
        }
        return false;
    }
}


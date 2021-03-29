package cn.edu.jsu.xq.sms.frm.stu;


import cn.edu.jsu.xq.sms.dao.CourseDAO;
import cn.edu.jsu.xq.sms.dao.RewardDAO;
import cn.edu.jsu.xq.sms.dao.ScjDAO;
import cn.edu.jsu.xq.sms.util.MyDefaultTableModel;
import cn.edu.jsu.xq.sms.vo.AccountNum;
import cn.edu.jsu.xq.sms.vo.Course;
import cn.edu.jsu.xq.sms.vo.Reward;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.Collections;
import java.util.Vector;

/**
 * 这是一个显示个人课程信息的类,包括选修，必修
 * @author 罗自觐
 */

public class LookCourseMessage extends JDialog {

    private JTable table;
    private JTextField textFieldKey;
    private DefaultTableModel model;
    private TableRowSorter sorter;
    private Vector<String> titles;
    private static AccountNum accountNum=null;
    private static LookCourseMessage instance=null;


    /**
     * 创建一个单例模式，返回一个实例
     * @param a
     * @return
     */
    public static LookCourseMessage getInstance(AccountNum a){
        accountNum=a;
        if (instance==null){
            instance=new LookCourseMessage();
        }
        return instance;
    }

    /**
     * Create the dialog.
     */
    private LookCourseMessage() {
        titles=new Vector<>();
        Collections.addAll(titles, "课程号","课程名","老师","学分","必修","选修","学习情况");

        setTitle("学生课程情况");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(200, 100, 550, 360);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(42, 55, 438, 203);
        getContentPane().add(scrollPane);

        table = new JTable();
        model=getTableModel(titles);
        table.setModel(model);
        table.setAutoCreateRowSorter(true);
        scrollPane.setViewportView(table);

        textFieldKey = new JTextField();
        textFieldKey.setBounds(111, 23, 66, 21);
        getContentPane().add(textFieldKey);
        textFieldKey.setColumns(10);

        JButton btnCx = new JButton("查询");
        btnCx.addActionListener(e->{
            //根据学号返回数据
            sorter=new TableRowSorter(model);
            sorter.setRowFilter(RowFilter.regexFilter(textFieldKey.getText()+".*"));
            table.setRowSorter(sorter);
        });

        btnCx.setBounds(244, 22, 97, 23);
        getContentPane().add(btnCx);

        JButton btnExit = new JButton("退出");
        btnExit.addActionListener(e->{
            LookCourseMessage.super.dispose();
            JOptionPane.showMessageDialog(null,"退出成功");
        });
        btnExit.setBounds(193, 284, 97, 23);
        getContentPane().add(btnExit);

    }

    /**
     * 返回一个MyDefaultTableModel
     * @param titles
     * @return
     */
    public MyDefaultTableModel getTableModel(Vector<String> titles){
        MyDefaultTableModel model=new MyDefaultTableModel(titles,0);
        Integer sno=accountNum.getId();
        Reward rewardBySno = RewardDAO.findRewardBySno(sno);
        String alterCourse = rewardBySno.getAlterCourse();
        String[] split = alterCourse.split("#");
        //"课程号","课程名","老师","学分","必修","选修","学习情况"
        String cno;
        String cname;
        String cbixiu;
        String cxuanxiu;
        String cxuexiqingkuang;
        for (int i = 2; i < split.length; i++) {
            cno=split[i];
            cname= CourseDAO.findNameByCno(cno);
            if (cno.startsWith("0")){
                cbixiu="是";
                cxuanxiu="否";
            }else
            {
                cbixiu="否";
                cxuanxiu="是";
            }
            Double gradeBySnoCno = ScjDAO.findGradeBySnoCno(sno, cno);
            if (gradeBySnoCno>0)
                cxuexiqingkuang="已学";
            else
                cxuexiqingkuang="正在学";
            Course byCno = CourseDAO.findByCno(cno);
            Vector row=new Vector();
            row.add(cno);
            row.add(cname);
            row.add(byCno.getcTeacher());
            row.add(byCno.getCredit());
            row.add(cbixiu);
            row.add(cxuanxiu);
            row.add(cxuexiqingkuang);
            model.addRow(row);
        }

        return model;
    }

    /**
     * 更新表中的数据
     */
    public void updateData(){
        model=getTableModel(titles);
        table.setModel(model);
    }

    /**
     * 判断是否存在数组中的方法
     * @param cno
     * @param ss
     * @return
     */
    public boolean isnotExist(String cno,String []ss){
        for (String s : ss) {
            if (cno.equals(s)){
                return false;
            }
        }
        return true;

    }
}




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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * 这是一个显示个人选修课情况的，并提供选课操作的界面
 * @author 罗自觐
 */

public class LookXuanXiuCourseMessage extends JDialog {

    private JTable table;
    private JTextField textFieldKey;
    private DefaultTableModel model;
    private MyDefaultTableModel model01;
    private MyDefaultTableModel model02;
    private TableRowSorter sorter;
    private Vector<String> titles;
    private static AccountNum accountNum=null;
    private static LookXuanXiuCourseMessage instance=null;

    /**
     * 创建一个单例模式，返回一个实例
     * @param a
     * @return
     */
    public static LookXuanXiuCourseMessage getInstance(AccountNum a){
        accountNum=a;
        if (instance==null){
            instance=new LookXuanXiuCourseMessage();
        }
        return instance;
    }

    /**
     * Create the dialog.
     */
    private LookXuanXiuCourseMessage() {
        titles=new Vector<>();
        Collections.addAll(titles, "课程号","课程名","老师","学分","学习情况");

        setTitle("学生选课情况");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(200, 100, 550, 360);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(42, 55, 438, 203);
        getContentPane().add(scrollPane);

        table = new JTable();
        model = (DefaultTableModel) table.getModel();
        model01=getTableModel();
        model.setDataVector(model01.getDataVector(),titles);
        model.fireTableDataChanged();
        table.setAutoCreateRowSorter(true);
        scrollPane.setViewportView(table);

        textFieldKey = new JTextField();
        textFieldKey.setBounds(90, 23, 100, 21);
        getContentPane().add(textFieldKey);
        textFieldKey.setColumns(10);

        JButton btnCx = new JButton("查询");
        btnCx.addActionListener(e->{
            //if (table.getModel()==model02){
//                table.setModel(model01);
//                table.updateUI();

//                System.out.println(model01);
                model.setDataVector(model01.getDataVector(),titles);
                model.fireTableDataChanged();
                if (textFieldKey.getText()!=null){
                    sorter=new TableRowSorter(model);
                    sorter.setRowFilter(RowFilter.regexFilter(textFieldKey.getText()+".*"));
                    table.setRowSorter(sorter);
                }
           // }

        });
        btnCx.setBounds(244, 22, 97, 23);
        getContentPane().add(btnCx);

        JButton btnExit = new JButton("退出");
        btnExit.addActionListener(e->{
            LookXuanXiuCourseMessage.super.dispose();
            JOptionPane.showMessageDialog(null,"退出成功");
        });
        btnExit.setBounds(150, 284, 97, 23);
        getContentPane().add(btnExit);

        JButton btnChoose = new JButton("选择课程");
        btnChoose.addActionListener(e->{
//            if (table.getModel()==model01){
//                table.setModel(model02);
//                table.updateUI();
//                System.out.println(model02+"选择课程");
//            }
            model.setDataVector(model02.getDataVector(),titles);
            model.fireTableDataChanged();


        });
        btnChoose.setBounds(350, 22, 97, 23);
        getContentPane().add(btnChoose);

        JButton btnChooseCourse = new JButton("确认选择");
        btnChooseCourse.addActionListener(e->{
            //选择某一行的方法
            if (table.getSelectedRow()!=-1){
//                MyDefaultTableModel model;
//                if (table.getModel()==model02){
//                    model=model02;
//                }else{
//                    model=model01;
//                }
                String s = model.getValueAt(table.getSelectedRow(), 4).toString();
                if ("未学".equals(s)){
                    if (JOptionPane.showConfirmDialog(null,"确定选择该课吗？（不能退课，请谨慎！）","",JOptionPane.YES_NO_OPTION)==0){
                        String cno=model.getValueAt(table.getSelectedRow(), 0).toString();
                        RewardDAO.doInsertCourse(cno,accountNum.getId());
                        JOptionPane.showMessageDialog(null,"选择成功！");
                        getTableModel();
                        //table.setModel(model);
                        model.setDataVector(model01.getDataVector(),titles);
                        model.fireTableDataChanged();
                        LookCourseMessage.getInstance(accountNum).updateData();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"不能选择已选课程");
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null,"请选择要学习的课！");
            }
        });
        btnChooseCourse.setBounds(300, 284, 97, 23);
        getContentPane().add(btnChooseCourse);

    }

    /**
     * 返回一个MyDefaultTableModel
     * @return
     */
    public MyDefaultTableModel getTableModel(){

            ArrayList<Course> allcount = CourseDAO.getAllcount();
            model01=new MyDefaultTableModel(titles,0);
            model02=new MyDefaultTableModel(titles,0);
            Integer sno=accountNum.getId();
            Reward rewardBySno = RewardDAO.findRewardBySno(sno);
            String alterCourse = rewardBySno.getAlterCourse();
            String[] split = alterCourse.split("#");
            //"课程号","课程名","老师","学分","学习情况"
            String cno;
            String cname;
            String cxuexiqingkuang;
            for (int i = 2; i < split.length; i++) {
                cno=split[i];
                if (cno.startsWith("1")){
                    cname= CourseDAO.findNameByCno(cno);
                    Course byCno = CourseDAO.findByCno(cno);
                    Double gradeBySnoCno = ScjDAO.findGradeBySnoCno(sno, cno);
                    if (gradeBySnoCno>0)
                        cxuexiqingkuang="已学";
                    else
                        cxuexiqingkuang="正在学";
                    Vector row=new Vector();
                    row.add(cno);
                    row.add(cname);
                    row.add(byCno.getcTeacher());
                    row.add(byCno.getCredit());
                    row.add(cxuexiqingkuang);
                    model01.addRow(row);
                }

            }

            for (Course course : allcount) {
                if (course.getCno().startsWith("1") &&isnotExist(course.getCno(),split) ){
                    cname= course.getCname();
                    Vector row=new Vector();
                    row.add(course.getCno());
                    row.add(cname);
                    row.add(course.getcTeacher());
                    row.add(course.getCredit());
                    row.add("未学");
                    model01.addRow(row);
                    model02.addRow(row);
                }
            }
        return model01;
    }

    /**
     * 判断cno是否在ss中
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




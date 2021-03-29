package cn.edu.jsu.xq.sms.frm;
/**
 * 创建服务器端，提供相应的数据，接受相关数据
 */

import cn.edu.jsu.xq.sms.dao.AccountNumDAO;
import cn.edu.jsu.xq.sms.vo.AccountNum;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 这是一个服务器类，提供检测账号密码是否正确
 */

public class Server {
    /**
     * 这是个主方法，开启多线程与网络连接
     * @param args
     */
    public static void main(String[] args) {
        try(ServerSocket serverSocket=new ServerSocket(8888);) {
            while(true) {
                Socket soc = serverSocket.accept();//开启服务器端等待客户端请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            InputStream is = soc.getInputStream();
                            OutputStream os = soc.getOutputStream();

                            //接受客户端发来的信息
                            byte[] b=new byte[1024*8];
                            int len = is.read(b);
                            String s=new String(b,0,len);
                            String[] split = s.split("\\s+");
                            if ("登录".equals(split[0])){
                                System.out.println("正在登录");

                                //如果进行的是登录操作，将id,password,分开，构建AccountNum对象
                                int id=Integer.parseInt(split[1]);
                                String password=split[2];
                                int isTeacher=Integer.parseInt(split[3]);
                                AccountNum vo=new AccountNum(id,password,isTeacher);


                                //判断是否为账号表中的数据
                                Boolean result=AccountNumDAO.checkid(vo);
                                os.write(result.toString().getBytes());//返回结果给客户端
                                if (result){
                                    //写入一个临时文件记录账号是谁
                                    File file=new File("D:\\java\\project\\src\\cn\\edu\\jsu\\xq\\sms\\source\\当前登录的账号.txt");
                                    ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
                                    oos.writeObject(vo);//把登录的账号写入临时文件
                                    oos.close();
                                }


                            }//其它操作


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

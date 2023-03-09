package com.iweb.method;

import com.iweb.impl.AddressDAOImpl;
import com.iweb.impl.ConsumerDAOImpl;
import com.iweb.impl.ManagerDAOImpl;
import com.iweb.pojo.Consumer;
import com.iweb.pojo.Manager;
import java.util.List;
import java.util.Scanner;

/**
 * @author 陈郅治
 * @date 2023/3/6  15:43
 **/
public class LoginInterface {
    private static Object object=null;
    private static ConsumerDAOImpl cdi=new ConsumerDAOImpl();
    private static AddressDAOImpl adi=new AddressDAOImpl();
    private static ManagerDAOImpl mdi=new ManagerDAOImpl();
    static Scanner sc=new Scanner(System.in);
    public static void login()  {
        loop:while (true){
            System.out.println("-----------------------------------------");
            System.out.println("              登陆界面");
            System.out.print("1:用户登陆");System.out.print("\t\t2:用户注册    ");
            System.out.print("3:管理员登陆");System.out.println("\t\t0:退出系统");
            System.out.println();
            System.out.print("请输入数字选择对应功能：");
            int choice=sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 0:System.exit(0);
                case 1:  if(userLogin()){
                    break loop;
                }break ;
                case 2:  userRegister(); break;
                case 3:  if(managerLogin()){break loop;} break ;
                default:System.out.println("请输入正确的数字选项");break;
            }
            System.out.println("-----------------------------------------");
        }
    }

    /**用户登录
     * @return 是否登录成功
     */
    public static boolean userLogin(){
        System.out.print("请输入账户名：");
        String name=sc.nextLine();
        System.out.print("请输入密码：");
        String password=sc.nextLine();
        List<Consumer> consumerList=cdi.listAll();
        for (Consumer con:consumerList) {
            if(con.getUser_name().equals(name)){
                //存在该账户
                if(con.getPassword().equals(password)){
                    //密码正确
                    System.out.println("账户密码正确，登陆成功！");
                    object=con;
                    return true;
                }else {
                    //密码错误
                    System.out.println("密码错误，请重新登陆！！");
                    object=null;
                    return false;
                }
            }
        }
        //没找到，账户名不存在
        System.out.println("该账户不存在，请重新登陆！");
        object=null;
        return false;
    }

    /**注册用户
     * @return 是否注册成功
     */
    public static boolean userRegister(){
        System.out.println("请输入账户名：");
        String name=sc.nextLine();
        List<Consumer> consumerList=cdi.listAll();
        for (Consumer c:consumerList) {
            if(name.equals(c.getUser_name())){
                System.out.println("该账户名已经存在！！");
                object=null;
                return false;
            }
        }
        System.out.println("请输入密码：");
        String password=sc.nextLine();
        System.out.println("请输入电话号码：");
        String phone=sc.nextLine();
        System.out.println("请输入初始充值金额：");
        double money=sc.nextDouble(); sc.nextLine();
        Consumer consumer=new Consumer(name,password,phone,money);
        if(cdi.insert(consumer)){
            //用户注册成功
            consumer.setId(getId(name));
            object=consumer;
            if(getId(name)!=-1){
                //注册成功
                System.out.println("请输入地址");
                String address=sc.nextLine();
                if(addAddress(getId(name),address)){
                    System.out.println("地址添加成功");
                    return true;
                }
            }
        }
        object=null;
        return false;
    }

    private static boolean addAddress(int id, String address) {
       return adi.insert(id,address);
    }

    /**管理员登录
     * @return 是否登录成功
     */
    public static boolean managerLogin(){
        System.out.print("请输入管理员账户名：");
        String name=sc.nextLine();
        System.out.print("请输入密码：");
        String password=sc.nextLine();
        List<Manager> managerList=mdi.listAll();
        for (Manager manager:managerList) {
            if(manager.getManager_name().equals(name)){
                //存在该账户
                if(manager.getPassword().equals(password)){
                    //密码正确
                    System.out.println("账户密码正确，登陆成功！");
                    object=manager;
                    return true;
                }else {
                    //密码错误
                    System.out.println("密码错误，请重新登陆！！");
                    object=null;
                    return false;
                }
            }
        }
        //没找到，账户名不存在
        System.out.println("该账户不存在，请重新登陆！");
        object=null;
        return false;
    }

    /**根据用户名找到对应id
     * @param name 用户名
     * @return  对应id
     */
    public static int getId(String name){
        List<Consumer> consumerList=cdi.listAll();
        for (Consumer c:consumerList) {
            if(name.equals(c.getUser_name())){
                return c.getId();
            }
        }
        return -1;
    }


    /**
     * @return 成功登录的对象引用
     */
    public static Object getObject(){
        return object;
    }
}

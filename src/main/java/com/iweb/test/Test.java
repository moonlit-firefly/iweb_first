package com.iweb.test;
import com.iweb.method.LoginInterface;
import com.iweb.method.ManagerFunction;
import com.iweb.method.UserFunction;
import com.iweb.pojo.Consumer;
import com.iweb.pojo.Manager;
import java.io.IOException;
import java.util.Scanner;

/**main()
 * @author 陈郅治
 */
public class Test {
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        while (true){
            LoginInterface.login();
            //用来存放登录者的对象引用
            Object object=LoginInterface.getObject();
            if(object!=null){
                //不为空，说明登录成功了
                if(object instanceof Consumer){
                    //是用户，进入用户界面
                    System.out.println("进入用户界面");
                    UserFunction.userInterface((Consumer) object);

                }else {
                    //是管理员，进入管理员界面
                    System.out.println("进入管理员界面");
                    ManagerFunction.managerInterface((Manager)object);
                }
            }
        }

    }

}

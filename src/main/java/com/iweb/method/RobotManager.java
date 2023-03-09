package com.iweb.method;

import com.iweb.impl.RobotDAOImpl;
import java.util.Scanner;

/**
 * @author : ljb
 * @date : 2023-03-07 16:56
 **/
public class RobotManager {
    RobotDAOImpl robot=new RobotDAOImpl();
    Scanner sc=new Scanner(System.in);
    public void askRespond(){
        System.out.println("亲，怎么啦");
        while (true){
            String question=sc.nextLine();
            if(question.contains("退出")){
                System.out.println("亲，欢迎您下次光临");
                break;
            } else {
                robot.respond(question);
            }
        }
    }
}

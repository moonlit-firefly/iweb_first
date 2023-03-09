package com.iweb.method;

import com.iweb.impl.AddressDAOImpl;
import com.iweb.impl.ConsumerDAOImpl;
import com.iweb.impl.ProductDAOImpl;
import com.iweb.impl.RobotDAOImpl;
import com.iweb.pojo.Consumer;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author 陈郅治
 * @date 2023/3/6  16:41
 **/
public class UserFunction {
    private static ConsumerDAOImpl cdi = new ConsumerDAOImpl();
    private static ProductDAOImpl pdi = new ProductDAOImpl();
    private static RobotManager rdi=new RobotManager();
    private static AddressDAOImpl adi=new AddressDAOImpl();
    static Scanner sc = new Scanner(System.in);

    /**
     * 用户功能界面
     *
     * @param consumer 用户对象
     */
    public static void userInterface(Consumer consumer) throws IOException {
        loop:
        while (true) {
            System.out.println("-----------------------------------------");
            System.out.println("              用户功能界面");
            System.out.println();
            System.out.println("用户信息显示:");
            System.out.println(consumer);
            System.out.print("1:用户充值           ");
            System.out.println("\t\t2:查看商品");
            System.out.print("3:查看购物车         ");
            System.out.println("\t\t4:地址管理");
            System.out.print("5:添加对于商品的评价  ");
            System.out.println("\t\t6:对shop的建议");
            System.out.print("7:联系客服           ");
            System.out.println("\t\t8:注销用户");
            System.out.print("9:查看用户对shop的建议");
            System.out.println("\t\t10:联系客服");
            System.out.print("11:返回登陆界面");
            System.out.println();
            System.out.print("请输入数字选择对应功能：");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:if(cdi.charge(consumer)){
                    System.out.println("充值成功");
                }else {
                    System.out.println("充值失败");
                }
                    break;
                case 2:
                    System.out.println(pdi.listAll());
                    System.out.println(cdi.getProduct_review());
                    sc.nextLine();
                    break;
                case 3:
                    CartFunction.consumerIndex(consumer.getId());
                    break;
                case 4:
                    adi.addressManager(consumer.getId());
                    break;
                case 5:
                    System.out.println(pdi.listAll());
                    System.out.println("请输入您购买的产品id：");
                    Integer product_id=sc.nextInt();
                    System.out.println("请输入你的评价：");
                    String service = sc.nextLine();
                    //向商品评价表中添加评价
                    cdi.setProduct_review(service,product_id,consumer.getId());
                    break;
                case 6:
                    System.out.println("请输入你的建议：");
                    String advice = sc.nextLine();
                    //向建议表中添加建议
                    cdi.setAdvice(advice,consumer.getId());
                    break;
                case 7:
                    break;
                case 8:
                    if(cdi.delete(consumer.getId())){
                        //删除成功
                        return;
                    }else {
                        System.out.println("删除失败");
                        break ;
                    }

                case 9:
                    System.out.println(adi.addressList(consumer.getId()));
                    break;
                case 10:
                    rdi.askRespond();
                    break ;
                case 11:
                    return;
                default:
                    System.out.println("请输入正确的数字选项");
                    break;
            }
            System.out.println("-----------------------------------------");
        }

    }
}



package com.iweb.method;

import com.iweb.impl.ManagerDAOImpl;
import com.iweb.impl.OrderDAOImpl;
import com.iweb.impl.ProductDAOImpl;
import com.iweb.pojo.Manager;
import com.iweb.pojo.Product;

import java.util.Scanner;

/**
 * @author 陈郅治
 * @date 2023/3/6  23:34
 **/
public class ManagerFunction {
    private static ManagerDAOImpl mdi = new ManagerDAOImpl();
    private static ProductDAOImpl pdi = new ProductDAOImpl();
    private static OrderDAOImpl odi = new OrderDAOImpl();
    static Scanner sc = new Scanner(System.in);
    /**
     * 管理员功能界面
     *
     * @param manager 管理员对象
     */
    public static void managerInterface(Manager manager) {
        loop:
        while (true) {
            System.out.println("-----------------------------------------");
            System.out.println("              管理员功能界面");
            System.out.println();
            System.out.print("1:查看所有商品信息"+"\t");
            System.out.println("2:商品进货");
            System.out.print("3:商品下架"+"\t");
            System.out.println("4:商品信息更新");
            System.out.print("5:查看用户订单"+"\t");
            System.out.println("6:处理订单");
            System.out.println("7:返回登陆界面");
            System.out.println();
            System.out.print("请输入数字选择对应功能：");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println(pdi.listAll());
                    break;
                case 2:
                    if(mdi.addProduct()){
                        System.out.println("添加新商品信息成功");
                    }else {
                        System.out.println("添加失败");
                    }
                    break;
                case 3:
                    System.out.println("请输入需要删除的商品id");
                    int id=sc.nextInt();sc.nextLine();
                    if(mdi.delete(id)){
                        System.out.println("删除成功");
                    }else {
                        System.out.println("删除失败");
                    }
                    break;
                case 4:
                    System.out.println("请输入需要修改的商品id");
                    int id2=sc.nextInt();sc.nextLine();
                    if(mdi.updateProduct(id2)){
                        System.out.println("修改成功");
                    }else {
                        System.out.println("修改失败");
                    }
                    break;
                case 5:
                    System.out.println(odi.orderList());
                    break;
                case 6:
                    System.out.println("请输入需要处理的订单id");
                    int id3=sc.nextInt();sc.nextLine();
                    if(odi.updateOrder(id3)){
                        System.out.println("处理成功");
                    }else {
                        System.out.println("未知错误");
                    }
                    break;
                case 7:
                    return;
                default:
                    System.out.println("请输入正确的数字选项");
                    break;
            }
            System.out.println("-----------------------------------------");
        }
    }
}

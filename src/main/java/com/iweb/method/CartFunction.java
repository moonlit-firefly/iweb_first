package com.iweb.method;

import com.iweb.impl.*;
import com.iweb.pojo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author 陈郅治
 * @date 2023/3/8  13:49
 **/
public class CartFunction {
    private static ProductDAOImpl pdi = new ProductDAOImpl();
    private static ConsumerDAOImpl cdi = new ConsumerDAOImpl();
    private static OrderDAOImpl odi=new OrderDAOImpl();
    private static AddressDAOImpl adi=new AddressDAOImpl();

    static Scanner sc=new Scanner(System.in);
    public static void consumerIndex(int id) throws IOException {
        while (true){
            System.out.println("请选择查看数据：");
            System.out.println("0:商品 1:购物车 2:订单 3:退出");
            String pick = sc.nextLine();
            switch (pick){
                case "0":
                    System.out.println(pdi.listAll());
                    break;
                case "1": cartList(id);
                    break;
                case "2": orderList(id);
                    break;
                case "3":return;
                default:
                    System.out.println("请输入正确的数字选项");
            }
        }

    }

    public static void cartList(int id) throws IOException {
        List<Cart> cartList = new CartImpl().cartList(id);
        Double total =0.0;
        for (Cart c:cartList){
            total+= pdi.get(c.getProductId()).getPrice()*c.getNumber();
            System.out.println("商品名称:"+c.getProductName()+",单价"+c.getPrice()+",购买数:"+c.getNumber());
        }
        System.out.println("总价:"+total);
        System.out.println("请选择操作:");
        System.out.println("0:结算购物车 其他数字:返回主界面");
        String select = sc.nextLine();
        if (Objects.equals(select, "0")){
            buyAll(id,total);
        }
    }

    public static void buyAll(int id,Double total) throws IOException {
        System.out.println("根据您的地址，请输入收货地址号:");
        List<Address> addressList = new AddressDAOImpl().addressList(id);
        for(Address a:addressList){
            System.out.println("地址号:"+a.getId()+",地理位置:"+a.getLocation());
        }
        System.out.println("请根据id选择一个地址");
        String addr = sc.nextLine();
        Address address=null;
        for (Address a:addressList) {
            if(a.getId().equals(Integer.parseInt(addr) )){
                address=a;
            }
        }
        if (address==null){
            System.out.println("输入错误,返回主界面");

        }else {
            Double money = cdi.get(id).getMoney();
            System.out.println("用户余额"+money);
            System.out.println(total);
            if (total<money){
                List<Cart> cartList = new CartImpl().cartList(id);
                List<Order_form> formList = new ArrayList<>();
                for (Cart c:cartList){
                    Order_form order = new Order_form();
                    order.setUser_id(id);
                    order.setProduct_id(c.getProductId());
                    order.setAddress_id(address.getId());
                    order.setNumber(c.getNumber());
                    formList.add(order);
                    new CartImpl().delete(c.getId());
                    Product p = pdi.get(c.getProductId());
                    p.setSales(c.getNumber()+p.getSales());
                    p.setStock(p.getStock()-c.getNumber());
                    pdi.update(p);
                }
                odi.insertAll(formList);
                Consumer c = cdi.get(id);
                c.setMoney(money-total);
                System.out.println("购买成功");
            }else {
                System.out.println("余额不足,返回主界面");

            }
        }
    }
    public static void orderList(int id) throws IOException {
        List<Order_form> formList = new OrderDAOImpl().formList(id);
        for (Order_form f:formList){
            String s = "";
            switch (f.getStatus()){
                case 0:s="未发货";
                    break;
                case 1:s="已发货";
                    break;
                case 2:s="退货中";
                    break;
                case 3:s="已退货";
                    break;
            }
            System.out.println("订单号:"+f.getId()+
                    ",商品名:"+pdi.get(f.getProduct_id()).getProduct_name()+
                    ",收货地址:" +adi.get(f.getAddress_id()).getLocation()+
                    "\n,购买数:"+f.getNumber()+
                    ",总价:"+(pdi.get(f.getProduct_id()).getPrice()*f.getNumber()) +
                    ",购买时间:"+f.getPay_time()+s);
        }
    }



}

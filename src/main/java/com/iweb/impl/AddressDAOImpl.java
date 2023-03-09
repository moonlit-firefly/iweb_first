package com.iweb.impl;

import com.iweb.DAO.AddressDAO;
import com.iweb.pojo.Address;
import com.iweb.pojo.Consumer;
import com.iweb.test.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author 陈郅治
 * @date 2023/3/6  16:59
 **/
public class AddressDAOImpl implements AddressDAO {
    private static ConsumerDAOImpl cdi=new ConsumerDAOImpl();

    /**给该用户创建一个地址
     * @param id 用户id
     * @param address 该用户地址
     * @return
     */
    @Override
    public boolean insert(int id, String address) {
        //sql插入语句
        String sql = "insert into address(user_id,location,gmt_create,gmt_modified,is_deleted) " +
                "values(?,?,NOW(),NOW(),?)";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, address);
            ps.setInt(3,0);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param id 用户id
     * @retur该用户的订单
     */
    @Override
    public List<Address> addressList(int id) {
        List<Address> addressList = new ArrayList<>();
        String sql = "select * from address where user_id = ?";
        try(Connection c = JDBCUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("is_deleted")==1){
                    continue;
                }
                Address addr = new Address();
                addr.setUser_id(id);
                addr.setId(rs.getInt("id"));
                addr.setLocation(rs.getString("location"));
                addressList.add(addr);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return addressList;
    }
    /**
     * @param address_id
     * @return 返回该地址对象
     */
    @Override
    public Address get(Integer address_id) {
        Address address=new Address();


        return address;
    }
    /**修改用户的某一个地址
     * @param con 用户对象
     * @return 是否更新地址成功
     */
    @Override
    public boolean updateAdress(Consumer con) {
        Scanner sc=new Scanner(System.in);
        System.out.println("****进入用户地址修改界面****");
        System.out.println(addressList(con.getId()));
        String sql = "UPDATE address SET location=?,gmt_modified=CURRENT_TIME WHERE user_id=? and id=?";
        System.out.println("请您输入要修改地址的id：");
        int id=sc.nextInt();sc.nextLine();
        System.out.println("请输入地址");
        String location=sc.nextLine();
        try (Connection c=JDBCUtil.getConnection();
             PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,location);
            ps.setInt(2,con.getId());
            ps.setInt(3,id);
            ps.execute();
            System.out.println("已经修改成功");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**删除用户的某一个地址
     * @param con 用户对象
     * @return 是否删除地址成功
     */
    @Override
    public boolean deleteAdress(Consumer con) {
        Scanner sc=new Scanner(System.in);
        System.out.println("****进入用户地址删除界面****");
        String sql = "DELETE FROM address WHERE user_id=? AND id=?";
        System.out.println("请输入你要删除的地址的编号：");
        String id=sc.nextLine();
        try (Connection c=JDBCUtil.getConnection();
             PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,con.getId());
            ps.setInt(2,Integer.parseInt(id));
            ps.execute();
            System.out.println("已经删除你选择的地址");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    @Override
    public void addressManager(Integer id) {
        while (true){
            System.out.println("***用户地址管理界面***");
            System.out.println("请您选择：1. 新建地址 2.查询地址  3.更新地址 4.删除地址 5.返回上一层 0.退出系统");
            Scanner sc = new Scanner(System.in);
            int select = sc.nextInt();
            if (select == 1) {
                System.out.println("请输入地址");
                sc.nextLine();
                String address=sc.nextLine();
                if (insert(id,address)) {
                    System.out.println("地址建立成功");
                }
            } else if (select == 2) {
                System.out.println(addressList(id));
            } else if (select == 3) {
                if (updateAdress(cdi.get(id))) {
                    System.out.println("地址修改成功");
                }
            } else if (select == 4) {
                if (deleteAdress(cdi.get(id))) {
                }
            } else if (select == 5) {
                return;
            } else if (select == 0) {
                System.out.println("开始退出系统,欢迎下次光临！");
                System.exit(0);
            }
        }
    }
}

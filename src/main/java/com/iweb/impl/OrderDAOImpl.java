package com.iweb.impl;

import com.iweb.DAO.OrderDAO;
import com.iweb.pojo.Order_form;
import com.iweb.test.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author 陈郅治
 * @date 2023/3/7  15:00
 **/
public class OrderDAOImpl implements OrderDAO {
    static Scanner sc=new Scanner(System.in);

    /**客户id，购买的商品集合 作为键值对
     * @return 订单表
     */
    @Override
    public HashMap<Integer, List<Order_form>> hm_orderList() {
        HashMap<Integer, List<Order_form>> hm_order = new HashMap<>();
        List<Order_form> orderList = orderList();

        for (int i = 0; i < orderList.size(); i++) {
            String sql = "select * from order_form where is_deleted=0 and number=?";
            try (Connection c = JDBCUtil.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                orderList = new ArrayList<>();
                ps.setInt(1, i);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Order_form order = new Order_form();
                    order.setId(rs.getInt("id"));
                    order.setProduct_id(rs.getInt("product_id"));
                    order.setAddress_id(rs.getInt("address_id"));
                    order.setNumber(rs.getInt("number"));
                    //将新获取的数据添加到集合中
                    orderList.add(order);
                }
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            hm_order.put(i, orderList);
        }


        String sql2 = "select id,user_id,product_id,address_id,pay_time,number," +
                "order_status,gmt_create,gmt_modified from order_form where is_deleted=0";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql2)) {
            ResultSet rs = ps.executeQuery();
            int number = 0;
            while (rs.next()) {
                Order_form order = new Order_form();
                order.setId(rs.getInt("id"));
                order.setProduct_id(rs.getInt("product_id"));
                order.setAddress_id(rs.getInt("address_id"));
                number = rs.getInt("number");
                //将新获取的数据添加到集合中
                orderList.add(order);
            }
            hm_order.put(number, orderList);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hm_order;
    }

    /**拿出所有订单
     * @return 订单集合
     */
    @Override
    public List<Order_form> orderList() {
        List<Order_form> orderList=new ArrayList<>();
        String sql = "select id,user_id,product_id,address_id,pay_time,product_number," +
                "order_status,gmt_create,gmt_modified from order_form where is_deleted=0";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order_form order = new Order_form();
                order.setId(rs.getInt("id"));
                order.setUser_id(rs.getInt("user_id"));
                order.setProduct_id(rs.getInt("product_id"));
                order.setAddress_id(rs.getInt("address_id"));
                order.setPay_time(rs.getDate("pay_time"));
                order.setNumber(rs.getInt("product_number"));
                order.setStatus(rs.getByte("order_status"));
                order.setGmt_create(rs.getDate("gmt_create"));
                order.setGmt_modified(rs.getDate("gmt_modified"));
                //将新获取的数据添加到集合中
                orderList.add(order);
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
    /**根据订单id处理订单
     * @param id 订单id
     * @return 是否处理成功
     */
    @Override
    public boolean updateOrder(int id) {
        String sql = "update order_form " +
                "set order_status=?,gmt_modified=NOW()  where id=?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            System.out.println("该订单目前状态修改为0：未发货 1：已发货 2：请求退货 3：已退货");
            ps.setByte(1, sc.nextByte());
            sc.nextLine();
            ps.setInt(2, id);
            ps.execute();
            System.out.println("更新成功");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Integer> insertAll(List<Order_form> formList) {
        List<Integer> list = null;
        for (Order_form f:formList){
            insert(f);
        }
        return list;
    }
    public int insert(Order_form orderForm){
        int id = -1;
        String sql = "insert into order_form(user_id,product_id,address_id," +
                "order_status,order_number,pay_time,gmt_create,gmt_modified) value(?,?,?,?,?,?,?,?)";
        try(Connection c = JDBCUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,orderForm.getUser_id());
            ps.setInt(2,orderForm.getProduct_id());
            ps.setInt(3,orderForm.getAddress_id());
            ps.setInt(4,orderForm.getStatus());
            ps.setInt(5,orderForm.getNumber());
            ps.setDate(6,new java.sql.Date(System.currentTimeMillis()));
            ps.setDate(7,new java.sql.Date(System.currentTimeMillis()));
            ps.setDate(8,new java.sql.Date(System.currentTimeMillis()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = ((Number) rs.getObject(1)).intValue();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    public List<Order_form> formList(int id) {
        List<Order_form> forms = new ArrayList<>();
        String sql = "select * from order_form,product,address where order_form.user_id = ? " +
                "and order_form.product_id = product.id and order_form.address_id = address.id ";
        try(Connection c = JDBCUtil.getConnection();
                PreparedStatement ps =c.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("is_deleted")==1){
                    continue;
                }
                Order_form form = new Order_form();
                form.setId(rs.getInt("order_form.id"));
                form.setProduct_id(rs.getInt("product.id"));
                form.setAddress_id(rs.getInt("address.id"));
                form.setPay_time(rs.getTimestamp("order_form.pay_time"));
                form.setNumber(rs.getInt("order_form.product_number"));
                form.setStatus(rs.getByte("order_form.order_status"));
                form.setGmt_create(rs.getTimestamp("order_form.gmt_create"));
                form.setGmt_modified(rs.getTimestamp("order_form.gmt_modified"));
                forms.add(form);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return forms;
    }


}

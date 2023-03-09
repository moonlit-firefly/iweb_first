package com.iweb.impl;

import com.iweb.DAO.ConsumerDAO;
import com.iweb.pojo.Advice;
import com.iweb.pojo.Consumer;
import com.iweb.pojo.Product_review;
import com.iweb.test.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**用户的功能方法集
 * @author 陈郅治
 */
public class ConsumerDAOImpl implements ConsumerDAO {
    static Scanner sc=new Scanner(System.in);

    /**添加一个用户
     * @param consumer 被插入的用户对象
     * @return
     */
    @Override
    public boolean insert(Consumer consumer) {
        //sql插入语句
        String sql = "insert into consumer(user_name,password,phone,money,gmt_create,gmt_modified,is_deleted) " +
                "values(?,?,?,?,NOW(),NOW(),?)";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, consumer.getUser_name());
            ps.setString(2, consumer.getPassword());
            ps.setString(3, consumer.getPhone());
            ps.setDouble(4, consumer.getMoney());
            ps.setInt(5, 0);
            ps.execute();
            System.out.println("注册成功");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param p 要修改的商品对象 id是where条件
     * @return
     */
    @Override
    public boolean update(Consumer p) {
        return false;
    }

    /**伪删除该id的用户
     * @param id
     * @return 是否成功删除
     */
    @Override
    public boolean delete(Integer id) {
        String sql = "update consumer " +
                "set is_deleted=1 where id=?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return 用户集合
     */
    @Override
    public List<Consumer> listAll() {
        List<Consumer> consumerList = new ArrayList<>();
        String sql = "select id,user_name,password,phone,money" +
                " from consumer where is_deleted=0";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Consumer consumer = new Consumer();
                consumer.setId(rs.getInt("id"));
                consumer.setUser_name(rs.getString("user_name"));
                consumer.setPassword(rs.getString("password"));
                consumer.setPhone(rs.getString("phone"));
                consumer.setMoney(rs.getDouble("money"));
                //将新获取的数据添加到集合中
                consumerList.add(consumer);
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consumerList;
    }

    /**出现了问题
     * @param id 用户的主键id
     * @return
     */
    @Override
    public Consumer get(Integer id) {
        Consumer consumer = new Consumer();
        String sql = "select * from consumer where id="+id;
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    if (rs.getByte("is_deleted") == 1) {
                        //如果该条数据被删除
                        System.out.println("该账户已经注销。");
                        return null;
                    }
                    consumer.setId(rs.getInt("id"));
                    consumer.setUser_name(rs.getString("user_name"));
                    consumer.setPassword(rs.getString("password"));
                    consumer.setPhone(rs.getString("phone"));
                    consumer.setMoney(rs.getDouble("money"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consumer;
    }

    /**该用户添加对系统的建议
     * @param advice 该用户对shop的建议
     * @param user_id 该用户id
     * @return
     */
    @Override
    public boolean setAdvice(String advice, int user_id) {
        String sql = "insert into advice(user_id,info,gmt_create,gmt_modified,is_deleted) " +
                "values(?,?,NOW(),NOW(),0)";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            ps.setString(2, advice);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**得到所有对shop的建议
     * @return 对shop系统的建议集合
     */
    @Override
    public List<Advice> getAdvice() {
        String sql = "select id,user_id,info" +
                " from advice ";
        List<Advice> advices = new ArrayList<>();
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Advice advice1 = new Advice();
                advice1.setId(rs.getInt("id"));
                advice1.setUser_id(rs.getInt("user_id"));
                advice1.setInfo(rs.getString("info"));
                //将新获取的数据添加到集合中
                advices.add(advice1);
            }
            ps.execute();
            return advices;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**给该用户对象充值指定金额
     * @param consumer 用户对象
     * @return
     */
    @Override
    public boolean charge(Consumer consumer) {
        System.out.println("****进入账户充值界面****");
        String sql = "UPDATE consumer SET money=money+?,gmt_modified=CURRENT_TIME WHERE id=?";
        System.out.println("请输入你要充值的金额：");
        double m=sc.nextDouble();
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setDouble(1,m);
            ps.setInt(2,consumer.getId());
            ps.execute();
            consumer.setMoney(consumer.getMoney()+m);
            System.out.println("账户充值成功");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("充值失败");
        }
        return false;
    }

    @Override
    public boolean setProduct_review(String review, int product_id,int user_id) {
        String sql = "insert into product_review(product_id,user_id,review,gmt_create,gmt_modified,is_deleted) " +
                "values(?,?,?,NOW(),NOW(),0)";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, product_id);
            ps.setInt(2,user_id);
            ps.setString(3, review);

            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product_review> getProduct_review() {
        String sql = "select id,product_id,user_id,review" +
                " from product_review ";
        List<Product_review> product_review = new ArrayList<>();
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product_review product_review1 = new Product_review();
                product_review1.setId(rs.getInt("id"));
                product_review1.setProduct_id(rs.getInt("product_id"));
                product_review1.setUesr_id(rs.getInt("user_id"));
                product_review1.setReview(rs.getString("review"));
                //将新获取的数据添加到集合中
                product_review.add(product_review1);
            }
            ps.execute();
            return product_review;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

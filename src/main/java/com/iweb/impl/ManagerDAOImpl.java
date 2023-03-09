package com.iweb.impl;

import com.iweb.DAO.ManagerDAO;
import com.iweb.pojo.Manager;
import com.iweb.pojo.Product;
import com.iweb.test.JDBCUtil;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**管理员的功能方法集
 * @author 陈郅治
 */
public class ManagerDAOImpl implements ManagerDAO {
    //调用商品方法集中的方法

    private static ProductDAOImpl pdi = new ProductDAOImpl();
    static Scanner sc = new Scanner(System.in);

    /**基于对象更新用户数据
     * @param p 要修改成的商品对象
     * @return 返回更新是否成功
     */
    @Override
    public boolean update(Product p) {
        String sql = "update product set product_name=?,cate_id=?,price=?," +
                "stock=?,sales=?,is_recommend=?,gmt_modified=Now() where id=?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,p.getProduct_name());
            ps.setInt(2,p.getCate_id());
            ps.setDouble(3,p.getPrice());
            ps.setInt(4,p.getStock());
            ps.setInt(5,p.getSales());
            ps.setByte(6,p.getIs_recommend());
            ps.setInt(7,p.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**基于id和控制台输入创建出想要的商品对象
     * 配合上一条语句实现商品修改
     * @param id 商品id
     * @return
     */
    @Override
    public boolean updateProduct(int id){
        System.out.print("请输入商品名称：");
        String name=sc.nextLine();
        System.out.println("请输入商品种类id");
        int cate_id=sc.nextInt();sc.nextLine();
        System.out.print("请输入商品价格：");
        double price=sc.nextDouble(); sc.nextLine();
        System.out.print("请输入商品库存：");
        int stock=sc.nextInt(); sc.nextLine();
        System.out.print("请输入商品销量：");
        int sales=sc.nextInt(); sc.nextLine();
        System.out.println("该商品是否推荐：0：不推荐  1：推荐");
        Byte is_recommend=sc.nextByte();sc.nextLine();
        Product product=new Product(id,name,cate_id,price,stock,sales,is_recommend);
        if(update(product)){
            System.out.println("商品修改成功");
            return true;
        }
        return false;
    }

    /**根据主键id删除商品数据（伪删除）
     * 企业的所有数据都是不做删除的 一般会在表中添加一个字段
     * isUse  0:表示不可查询 1:表示可以查询
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) {
        String sql = "update product set is_deleted=1 where id=?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**查询所有数据
     * @return 所有用户对象对应的集合
     */
    @Override
    public List<Manager> listAll() {
        List<Manager> managerList = new ArrayList<>();
        String sql = "select id,manager_name,password " +
                " from manager where is_deleted=0";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Manager manager = new Manager();
                manager.setId(rs.getInt("id"));
                manager.setManager_name(rs.getString("manager_name"));
                manager.setPassword(rs.getString("password"));
                //将新获取的数据添加到集合中
                managerList.add(manager);
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managerList;
    }


    /**根据id获取对应的商品数据
     * @param id 商品的主键id
     * @return 得到的商品对象
     */
    @Override
    public Product getProduct(Integer id) {
        Product product=new Product();
        String sql = "select id,product_name,cate_id,price,stock,sales,is_recommend," +
                "gmt_modified,is_deleted from product where id=?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.getByte("is_deleted") == 0) {
                //如果该条数据被删除
                System.out.println("该商品已经注销。");
                return null;
            }
            product.setId(rs.getInt("id"));
            product.setProduct_name(rs.getString("product_name"));
            product.setCate_id(rs.getInt("cate_id"));
            product.setPrice(rs.getDouble("price"));
            product.setStock(rs.getInt("stock"));
            product.setSales(rs.getInt("sales"));
            product.setIs_recommend(rs.getByte("is_recommend"));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
    /**向商品表中添加商品
     * @return 是否添加成功
     */
    @Override
    public boolean addProduct() {
        System.out.print("请输入商品名称：");
        String name=sc.nextLine();
        System.out.println("请输入商品种类id");
        int cate_id=sc.nextInt();sc.nextLine();
        System.out.print("请输入商品价格：");
        double price=sc.nextDouble(); sc.nextLine();
        System.out.print("请输入商品库存：");
        int stock=sc.nextInt(); sc.nextLine();
        System.out.println("该商品是否推荐：0：不推荐  1：推荐");
        Byte is_recommend=sc.nextByte();sc.nextLine();
        Product product=new Product(name,cate_id,price,stock,is_recommend);
        if(pdi.insert(product)){
            return true;
        };
        return false;
    }
}

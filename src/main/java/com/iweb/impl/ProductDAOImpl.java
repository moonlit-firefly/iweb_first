package com.iweb.impl;

import com.iweb.DAO.ProductDAO;
import com.iweb.pojo.Product;
import com.iweb.test.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**商品方法的实现
 * @author 陈郅治
 */
public class ProductDAOImpl implements ProductDAO {
    /**
     * 实例化一个产品价格比较器
     */
    ProductDAOComparator comparator=new ProductDAOComparator();

    /**向商品表插入一条数据
     * @param p 要插入的数据实体 应该具有商品数据除了id之外的所有属性
     * @return  返回插入是否成功
     */
    @Override
    public boolean insert(Product p) {
        //sql插入语句
        String sql = "insert into product(product_name,cate_id,price,stock," +
                "gmt_create,gmt_modified,is_recommend) " +
                "values(?,?,?,?,NOW(),Now(),?)";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            //插入产品  名称  价格  库存   是否推荐
            ps.setString(1, p.getProduct_name());
            ps.setInt(2, p.getCate_id());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setInt(5,p.getIs_recommend());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**基于id更新商品数据
     * @param p 要修改的商品对象 id是where条件
     * @return 返回更新是否成功
     */
    @Override
    public boolean update(Product p) {
        //修改产品的  价格  库存  销量   是否推荐
        String sql = "update product " +
                "set price=?,stock=?,sales=?,is_recommend=? where id=?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, p.getPrice());
            ps.setInt(2, p.getStock());
            ps.setInt(3, p.getSales());
            ps.setByte(4,p.getIs_recommend());
            ps.setInt(5,p.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**根据主键id删除商品数据（伪删除）
     * 企业的所有数据都是不做删除的 一般会在表中添加一个字段
     * is_deleted  0:表示不可查询 1:表示可以查询
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) {
        String sql = "update product set is_recommend=1 where id=?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**查询所有数据
     * @return 所有商品对象对应的集合
     */
    @Override
    public List<Product> listAll() {
        //保存所有的产品对象的数据   给顾客展示商品信息
        List<Product> productList = new ArrayList<>();
        String sql = "select product_name,cate_id,price,sales,stock,id,is_recommend " +
                "from product where is_deleted=0";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProduct_name(rs.getString("product_name"));
                p.setCate_id(rs.getInt("cate_id"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setSales(rs.getInt("sales"));
                p.setId(rs.getInt("id"));
                p.setIs_recommend(rs.getByte("is_recommend"));
                //将新获取的数据添加到集合中
                productList.add(p);
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    /** 根据商品名称进行模糊查询
     * @param keyword
     * @return 模糊查询的商品对象集合
     */
    @Override
    public List<Product> listByNameLike(String keyword) {
        //模糊搜索
        List<Product> productList = new ArrayList<>();
        String sql = "select product_name,price,sales,stock,id " +
                "from product where product_name like concat('%',?,'%')";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, keyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProduct_name(rs.getString("product_name"));
                p.setStock(rs.getInt("stock"));
                p.setPrice(rs.getDouble("price"));
                p.setId(rs.getInt("id"));
                //将新获取的数据添加到集合中
                productList.add(p);
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**根据id获取对应的商品数据
     * @param id 商品的主键id
     * @return 得到的商品对象
     */
    @Override
    public Product get(Integer id) {
        Product p = null;
        String sql = "select * from product where id =?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                p = new Product();
                p.setProduct_name(rs.getString("product_name"));
                p.setStock(rs.getInt("stock"));
                p.setPrice(rs.getDouble("price"));
                p.setId(rs.getInt("id"));
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
}

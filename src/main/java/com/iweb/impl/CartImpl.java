package com.iweb.impl;


import com.iweb.DAO.CartDAO;
import com.iweb.pojo.Cart;
import com.iweb.test.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**购物车的方法实现
 * @author 陈郅治
 * @date 2023/3/8  13:38
 **/
public class CartImpl implements CartDAO {
    static  Scanner sc=new Scanner(System.in);

    @Override
    public List<Cart> cartList(int id) {
        List<Cart> carts = new ArrayList<>();
        String sql = "select * from cart,product where cart.product_id = product.id and cart.user_id = ? ";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("is_deleted") == 1) {
                    continue;
                }
                Cart cart = new Cart();
                cart.setId(rs.getInt("cart.id"));
                cart.setProductId(rs.getInt("cart.product_id"));
                cart.setProductName(rs.getString("product.product_name"));
                cart.setPrice(rs.getBigDecimal("product.price"));
                cart.setNumber(rs.getInt("cart.product_number"));
                cart.setGmtCreate(rs.getTimestamp("cart.gmt_create"));
                cart.setGmtModified(rs.getTimestamp("cart.gmt_modified"));
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    @Override
    public void delete(int id) {
        Cart p = get(id);
        p.setIsDeleted((byte) 1);
        update(p);
    }

    @Override
    public void update(Cart cart) {
        String sql = "update cart set user_id=?,product_id=?,product_number=?,gmt_create=?,gmt_modified=?,is_deleted=? where id=?";
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cart.getUserId());
            ps.setInt(2, cart.getProductId());
            ps.setInt(3, cart.getNumber());
            ps.setTimestamp(4, cart.getGmtCreate());
            ps.setTimestamp(5, cart.getGmtModified());
            ps.setInt(6, cart.getIsDeleted());
            ps.setInt(7, cart.getId());
            if (cart.getIsDeleted() == null) {
                ps.setInt(4, 0);
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cart get(int id) {
        String sql = "select * from cart where id=?";
        Cart cart = null;
        try (Connection c = JDBCUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("is_deleted") == 1) {
                    return null;
                }
                cart = new Cart();
                cart.setId(id);
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setNumber(rs.getInt("product_number"));
                cart.setGmtCreate(rs.getTimestamp("gmt_create"));
                cart.setGmtModified(rs.getTimestamp("gmt_modified"));
                cart.setIsDeleted(rs.getByte("is_deleted"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }




}

package com.iweb.DAO;

import com.iweb.pojo.Manager;
import com.iweb.pojo.Product;

import java.util.List;

public interface ManagerDAO {

    /**基于对象更新用户数据
     * @param p 要修改成的商品对象
     * @return 返回更新是否成功
     */
    boolean update(Product p);

    /**基于id和控制台输入创建出想要的商品对象
     * 配合上一条语句实现商品修改
     * @param id 商品id
     * @return
     */
    boolean updateProduct(int id);

    /**根据主键id删除商品数据（伪删除）
     * 企业的所有数据都是不做删除的 一般会在表中添加一个字段
     * isUse  0:表示不可查询 1:表示可以查询
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**查询所有数据
     * @return 所有用户对象对应的集合
     */
    List<Manager> listAll();

    /**根据id获取对应的商品数据
     * @param id 商品的主键id
     * @return 得到的商品对象
     */
    Product getProduct(Integer id);


    /**向商品表中添加商品
     * @return 是否添加成功
     */
    boolean addProduct();
}

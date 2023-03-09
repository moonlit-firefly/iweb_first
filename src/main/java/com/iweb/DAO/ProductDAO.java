package com.iweb.DAO;
import com.iweb.pojo.Product;
import java.util.List;

/**商品功能的方法封装
 * @author 陈郅治
 */
public interface ProductDAO {
    /**向商品表插入一条数据
     * @param p 要插入的数据实体 应该具有商品数据除了id之外的所有属性
     * @return  返回插入是否成功
     */
    boolean insert(Product p);

    /**基于id更新商品数据
     * @param p 要修改的商品对象 id是where条件
     * @return 返回更新是否成功
     */
    boolean update(Product p);

    /**根据主键id删除商品数据（伪删除）
     * 企业的所有数据都是不做删除的 一般会在表中添加一个字段
     * is_deleted  0:表示不可查询 1:表示可以查询
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**查询所有数据
     * @return 所有商品对象对应的集合
     */
    List<Product> listAll();


    /** 根据商品名称进行模糊查询
     * @param keyword
     * @return 模糊查询的商品对象集合
     */
    List<Product> listByNameLike(String keyword);

    /**根据id获取对应的商品数据
     * @param id 商品的主键id
     * @return 得到的商品对象
     */
    Product get(Integer id);

}

package com.iweb.DAO;

import com.iweb.pojo.Advice;
import com.iweb.pojo.Consumer;
import com.iweb.pojo.Product_review;

import java.util.List;

/**顾客功能的方法封装
 * @author 陈郅治
 */
public interface ConsumerDAO {
    /**向用户表插入一条数据
     * @param p 要插入的数据实体 应该具有商品数据除了id之外的所有属性
     * @return  返回插入是否成功
     */
    boolean insert(Consumer p);

    /**基于对象更新用户数据
     * @param p 要修改的商品对象 id是where条件
     * @return 返回更新是否成功
     */
    boolean update(Consumer p);

    /**根据主键id删除用户数据（伪删除）
     * 企业的所有数据都是不做删除的 一般会在表中添加一个字段
     * isUse  0:表示不可查询 1:表示可以查询
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**查询所有数据
     * @return 所有用户对象对应的集合
     */
    List<Consumer> listAll();

    /**根据id获取对应的用户数据
     * @param id 商品的主键id
     * @return 得到的用户对象
     */
    Consumer get(Integer id);

    /**该id发用户向shop发表建议
     * @param advice 添加的建议
     * @param id 该用户的id
     * @return
     */
    boolean setAdvice(String advice,int id);

    /**得到所有对shop的建议
     * @return 对shop系统的建议集合
     */
    List<Advice> getAdvice();

    /**给该用户对象充值指定金额
     * @param consumer 用户对象
     * @return
     */
    boolean charge(Consumer consumer);


    /**设置商品评价
     * @param review 评价
     * @param product_id 商品id
     * @param user_id 用户id
     * @return 是否评价成功
     */
    boolean setProduct_review(String review,int product_id,int user_id);

    /**
     * @return 商品评价集
     */
    List<Product_review> getProduct_review();
}

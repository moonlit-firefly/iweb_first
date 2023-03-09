package com.iweb.DAO;

import com.iweb.pojo.Product;
import com.iweb.pojo.Robot;


import java.util.List;

/**
 * @author : ljb
 * @date : 2023-03-07 15:19
 **/
public interface RobotDAO {
    /**找有没有相同的question
     * @param question 需要找到question
     * @return 找到了返回true 没找到返回false
     */
    boolean search(String question);

    /**找有没有相同的question
     * @param id 需要找到id
     * @return 找到了返回true 没找到返回false
     */
    boolean search(Integer id);

    /** 向智能回复表插入一条数据
     * @param r 要插入的数据实体 应该具有智能回复数据除了id之外所有的属性
     * @return 返回插入是否成功
     */
    boolean insert(Robot r);

    /**基于id更新智能回复表数据
     * @param r 要修改的智能回复表数据 其中id为where条件
     * @return 返回更新是否成功
     */
    boolean update(Robot r);

    /**根据主键id删除智能回复表数据
     * 企业中的所有数据都是不做删除的 一般会在表中添加一个字段
     * isUse 0 1
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**查询所有数据
     * @return 所有智能回复表对象对应的集合
     */
    List<Robot> listAll();

    /**智能回复
     * @return 回复的语句
     */
    boolean respond(String question);
}

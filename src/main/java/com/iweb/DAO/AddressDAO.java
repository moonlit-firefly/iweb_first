package com.iweb.DAO;

import com.iweb.pojo.Address;
import com.iweb.pojo.Consumer;

import java.util.List;

/**
 * @author 陈郅治
 * @date 2023/3/6  16:59
 **/
public interface AddressDAO {
    /**给该用户创建一个地址
     * @param id 用户id
     * @param address 该用户地址
     * @return
     */
    boolean insert(int id, String address);


    /**
     * @param id 用户id
     * @retur该用户的订单
     */
    List<Address> addressList(int id);

    /**
     * @param address_id
     * @return 返回该地址对象
     */
    Address get(Integer address_id);

    /**修改用户的某一个地址
     * @param con 用户对象
     * @return 是否更新地址成功
     */
    boolean updateAdress(Consumer con);
    /**删除用户的某一个地址
     * @param con 用户对象
     * @return 是否删除地址成功
     */
    boolean deleteAdress(Consumer con);

    /**管理该用户的地址
     * @param id 用户的id
     */
    void addressManager(Integer id);



}

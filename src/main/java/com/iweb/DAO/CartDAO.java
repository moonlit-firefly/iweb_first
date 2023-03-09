package com.iweb.DAO;

import com.iweb.pojo.Cart;

import java.util.List;

/**购物车的方法实现
 * @author 陈郅治
 * @date 2023/3/8  13:38
 **/
public interface CartDAO {
    List<Cart> cartList(int id);

    /**根据id伪删除购物车数据
     * @param id
     */
    void delete(int id);

    /**
     * @param cart 更新后的cart对象
     */
    void update(Cart cart);

    /**
     * @param id 购物车id
     * @return 购物车对象
     */
    Cart get(int id);
}

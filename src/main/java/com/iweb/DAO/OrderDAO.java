package com.iweb.DAO;
import com.iweb.pojo.Order_form;
import java.util.HashMap;
import java.util.List;

/**订单表操作
 * @author 陈郅治
 * @date 2023/3/7  14:56
 **/
public interface OrderDAO {

    /**客户id，购买的商品集合 作为键值对
     * @return 订单表
     */
    HashMap<Integer,List<Order_form>> hm_orderList();

    /**拿出所有订单
     * @return 订单集合
     */
    List<Order_form> orderList();

    /**根据订单id处理订单
     * @param id 订单id
     * @return 是否处理成功
     */
    boolean updateOrder(int id);
}

package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**订单表对应的类
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Order_form {
    private Integer id;
    private Integer user_id;
    private Integer product_id;
    private Integer address_id;
    private Integer number=1;
    private Date pay_time;
    private Byte status=0;
    private Date gmt_create;
    private Date gmt_modified;
    private Byte is_deleted=0;

    @Override
    public String toString() {
        String info;
        switch (status){
            case 0:info="未发货";break;
            case 1:info="已发货";break;
            case 2:info="请求退货";break;
            case 3:info="已退货";break;
            default:info="订单状态未添加";break;
        }

        return "\n订单表id：" + id +
                "\t\t用户id：" + user_id +
                "\t产品id：" + product_id +
                "\t收获地址id：" + address_id +
                "\t订单编号：" + number +
                "\t支付时间：" + pay_time +
                "\n订单状态：" + info +
                "\t订单创建时间：" + gmt_create +
                "\t订单上次修改时间" + gmt_modified+"\n";
    }
}

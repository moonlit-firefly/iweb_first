package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**顾客对应的类
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Consumer {
    private Integer id;
    private String user_name;
    private String password;
    private String phone;
    private Double money=0.00;
    private Date gmt_create;
    private Date gmt_modified;
    private Byte is_deleted=0;

    public Consumer(String user_name, String password, String phone, Double money) {
        this.user_name = user_name;
        this.password = password;
        this.phone = phone;
        this.money = money;
    }

    @Override
    public String toString() {
        return "客户" +
                "id：" + id + '\t' +
                "用户名称：" + user_name + '\t' +
                "用户密码：" + password + '\t' +
                "电话号码：" + String.valueOf(phone)+'\t' +
                "余额：" + money +'\t' +
                "\n";
    }
}

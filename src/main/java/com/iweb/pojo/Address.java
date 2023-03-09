package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**地址表对应的对象
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Address {
    private Integer id;
    private Integer user_id;
    private String location;
    private Date gmt_create;
    private Date gmt_modified;
    private Byte is_deleted=0;

    @Override
    public String toString() {
        return "地址id：" + id +
                ",用户id:" + user_id +
                ",地址：'" + location +"\n";
    }
}

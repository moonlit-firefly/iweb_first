package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**商品类型表对应的类
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Cartgory {
    private Integer id;
    private String cart_name;
    private Date gmt_create;
    private Date gmt_modified;
    private Byte is_deleted=0;
}

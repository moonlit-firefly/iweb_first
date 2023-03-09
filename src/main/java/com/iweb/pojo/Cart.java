package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**购物车表对应的id
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Cart {
    private int id;
    private int userId;
    private String productName;
    private int productId;
    private int number;
    private BigDecimal price;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Byte isDeleted;
}

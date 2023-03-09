package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Product_review {
    private Integer id;
    private Integer product_id;
    private Integer uesr_id;
    private String review;
    private Date gmt_create;
    private Date gmt_modified;
    private Byte is_deleted=0;

    @Override
    public String toString() {
        return "评价id:" + id +
                ", 商品id:" + product_id +
                ", 评价者id:" + uesr_id +
                ", 评价：" + review+"\n1" +
                "";
    }
}

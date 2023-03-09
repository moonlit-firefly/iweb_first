package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**商品表的类
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Product {
    private Integer id;
    private String product_name;
    private Integer cate_id;
    private Integer stock=200;
    private Integer sales=0;
    private Double price=0.0;
    private Date gmt_create;
    private Date gmt_modified;
    private Byte is_deleted=0;
    private Byte is_recommend=0;
    public Product(String name,Integer cate_id,Double price,Integer stock,Byte is_recommend){
        this.product_name=name;
        this.cate_id=cate_id;
        this.price=price;
        this.stock=stock;
        this.is_recommend=is_recommend;
    }
    public Product(int id,String name,Integer cate_id,Double price,Integer stock,Integer sales,Byte is_recommend){
        this.id=id;
        this.product_name=name;
        this.cate_id=cate_id;
        this.price=price;
        this.stock=stock;
        this.sales=sales;
        this.is_recommend=is_recommend;
    }

    @Override
    public String toString() {
        return  "商品id:" + id +
                "\t  商品名:" + product_name +
                "\t\t  商品种类id:" + cate_id +
                "\t  商品库存:" + stock +
                "\t  商品销量:" + sales +
                "\t  商品价格:" + price +
                "\t  是否被推荐:" + (is_recommend==1?'是':'否') +"\n";
    }
}

package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**系统建议表对应的类
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Advice {
    private Integer id;
    private Integer user_id;
    private String info="系统很棒，孩子很喜欢。下次还在你家买东西。";
    private Date gmt_create;
    private Date gmt_modified;
    private Byte is_deleted=0;

    @Override
    public String toString() {
        return "建议" + '\t'+
                "id：" + id + '\t'+
                "评价用户id：" + user_id + '\t'+
                "评价内容：" + info + '\t'+"\n"
               ;
    }
}

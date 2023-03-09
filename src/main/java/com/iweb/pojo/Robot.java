package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**智能客服
 * @author : ljb
 * @date : 2023-03-07 15:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Robot {
    private Integer pk_id;
    private String question;
    private String answer;
    private Date gmt_create;
    private Date gmt_modified;
    private int is_deleted;

    public Robot(String question, String answer) {
        this.question = question;
        this.answer = answer;
        gmt_create=new Date(System.currentTimeMillis());
        gmt_modified=new Date(System.currentTimeMillis());
    }


    @Override
    public String toString() {
        return "Robot{" +
                "pk_id=" + pk_id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", gmt_create=" + gmt_create +
                ", gmt_modified=" + gmt_modified +
                "}\r\n";
    }
}

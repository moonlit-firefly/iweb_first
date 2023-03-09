package com.iweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**管理员对应的类
 * @author 陈郅治
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Manager {
    private Integer id;
    private String manager_name;
    private String password;
    private Date gmt_create;
    private Date gmt_modified;
    private Byte is_deleted=0;
}

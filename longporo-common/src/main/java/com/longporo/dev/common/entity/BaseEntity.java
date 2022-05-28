package com.longporo.dev.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * Base Entity<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Data
public abstract class BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    // define the other common fields of all entities here
/*    @TableField(value = "create_date", exist = false)
    private Date createDate;*/
}
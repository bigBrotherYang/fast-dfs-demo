package com.yangml.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@TableName("fast_dfs_tb")
@Data
@ToString
public class Fds {
    @TableId(value = "fdt_id",type = IdType.AUTO)
    private Integer fdfId;
    private String fdtVal;
    private String fdtDis;
}

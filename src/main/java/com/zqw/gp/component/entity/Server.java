package com.zqw.gp.component.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zqw
 * @date 2023/3/8 14:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("server")
public class Server {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String name;
    private String url;
    private boolean enable;
}

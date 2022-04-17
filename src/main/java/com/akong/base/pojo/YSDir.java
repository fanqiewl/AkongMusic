package com.akong.base.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 永硕E盘DIR目录
 *
 * @author Akong
 * @since 2022/4/5 20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YSDir {
    // 目录编号
    private String dirId;
    // 目录名称
    private String dirName;
    // 目录备注
    private String dirRemark;
}

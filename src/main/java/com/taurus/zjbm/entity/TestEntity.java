package com.taurus.zjbm.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* @author taurus
*/
@Data
@Accessors(chain = true)
public class TestEntity {

	/**
     * id 主键
     */
    @ApiModelProperty("主键")
	private Long id;

	/**
     * unit 单位
     */
    @ApiModelProperty("单位")
	private Long unit;

	/**
     * userId 用户id
     */
    @ApiModelProperty("用户id")
	private Long userId;

	/**
     * createDate 插入时间
     */
    @ApiModelProperty("插入时间")
	private Date createDate;

	/**
     * updateDate 更新时间
     */
    @ApiModelProperty("更新时间")
	private Date updateDate;

}

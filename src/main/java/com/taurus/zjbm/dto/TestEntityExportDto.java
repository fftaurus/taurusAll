package com.taurus.zjbm.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
* @author taurus
*/
@Data
public class TestEntityExportDto  extends BaseRowModel {

	/**
     * id 主键
     */
    @ExcelProperty(value = "主键", index = 0)
    @ApiModelProperty("主键")
	private Long id;

	/**
     * unit 单位
     */
    @ExcelProperty(value = "单位", index = 1)
    @ApiModelProperty("单位")
	private Long unit;

	/**
     * userId 用户id
     */
    @ExcelProperty(value = "用户id", index = 2)
    @ApiModelProperty("用户id")
	private Long userId;

	/**
     * createDate 插入时间
     */
    @ExcelProperty(value = "插入时间", index = 3)
    @ApiModelProperty("插入时间")
	private Date createDate;

	/**
     * updateDate 更新时间
     */
    @ExcelProperty(value = "更新时间", index = 4)
    @ApiModelProperty("更新时间")
	private Date updateDate;

}

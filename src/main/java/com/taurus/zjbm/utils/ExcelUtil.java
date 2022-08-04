package com.taurus.zjbm.utils;

import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.TableStyle;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    public static void exportFile1(final String templetFilePath,
                                   final Map<String, Object> dataMap,
                                   final OutputStream outputStream) {

        InputStream in = null;
        try {
            final XLSTransformer transformer = new XLSTransformer();
            in = ExcelUtil.class.getClassLoader().getResourceAsStream(templetFilePath);
            final Workbook workbook = transformer.transformXLS(in, dataMap);
            workbook.setForceFormulaRecalculation(true);
            workbook.write(outputStream);
        } catch (final Exception e) {
            LOGGER.error("导出xls失败", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }



    private static TableStyle createTableStyle() {

        final TableStyle tableStyle = new TableStyle();
        // 设置表头样式
        final Font headFont = new Font();
        // 字体是否加粗
        headFont.setBold(false);
        // 字体大小
        headFont.setFontHeightInPoints((short) 11);
        // 字体
        headFont.setFontName("宋体");
        tableStyle.setTableHeadFont(headFont);
        // 背景色
        tableStyle.setTableHeadBackGroundColor(IndexedColors.WHITE);

        // 设置表格主体样式
        final Font contentFont = new Font();
        contentFont.setBold(false);
        contentFont.setFontHeightInPoints((short) 11);
        contentFont.setFontName("宋体");
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);
        return tableStyle;
    }

    public static List splitList(final List list, final int len) {

        //返回结果
        final List<List<T>> result = new ArrayList<>();
        //传入集合长度
        final int size = list.size();
        //分隔后的集合个数
        final int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            final List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
}

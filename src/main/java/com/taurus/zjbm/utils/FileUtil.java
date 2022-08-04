package com.taurus.zjbm.utils;

import com.baomidou.mybatisplus.core.toolkit.IOUtils;
import com.taurus.zjbm.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("nls")
@Slf4j
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static final String FILE_SEPARATOR = "/";

    public static final String DEFAULT_MODULE = "default";

    public static final String UTF_8 = "UTF-8";

    public static final String ISO_8859_1 = "ISO-8859-1";

    public static final String ZIP_SUFFIX = ".zip";

    public static final String POINT = ".";

    /**
     * 字符集
     */
    private static final char[] CODE_OPTION = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    /**
     * 生成相对路径
     *
     * @return path
     */
    public static String generateRelativeDir() {

        return new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return 后缀
     */
    public static final String getFileSuffix(final String fileName) {

        if (fileName == null) {
            return null;
        }
        final int suffixIndex = fileName.lastIndexOf(POINT);
        if (suffixIndex == -1) {
            return null;
        } else {
            return fileName.substring(suffixIndex);
        }

    }

    /**
     * 封装下载返回
     *
     * @param response
     * @param fileName
     */
    public static final void wrapDownloadResponse(final HttpServletResponse response, final String fileName) {

        FileUtil.wrapDownloadResponse(response, fileName, -1);
    }

    /**
     * 封装下载返回
     *
     * @param response
     * @param fileName
     * @param fileLength
     */
    public static final void wrapDownloadResponse(final HttpServletResponse response, final String fileName,
                                                  final int fileLength) {

        response.setContentType("application/octet-stream;charset=UTF-8");
        if (fileLength > 0) {
            response.setContentLength(fileLength);
        }
        String headerValue = "attachment;";
        headerValue += " filename=\"" + encodeUriComponent(fileName) + "\";";
        headerValue += " filename*=utf-8''" + encodeUriComponent(fileName);
        response.setHeader("Content-Disposition", headerValue);
        response.setCharacterEncoding(FileUtil.UTF_8);
    }

    /**
     * <pre>
     * 符合 RFC 3986 标准的“百分号URL编码”
     * 在这个方法里，空格会被编码成%20，而不是+
     * 和浏览器的encodeURIComponent行为一致
     * </pre>
     *
     * @param value
     * @return
     */
    public static String encodeUriComponent(final String value) {

        try {
            return URLEncoder.encode(value, UTF_8).replaceAll("\\+", "%20");
        } catch (final UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public static void exportZipFile(final List<String> fileNames, final HttpServletResponse response, final String url) {

        String fileName = "name" + ".zip";
        // 根据文件名获取 MIME 类型
        final Tika tika = new Tika();
        final String contentType = tika.detect(ZIP_SUFFIX);
        try {
            // 解决中文文件名下载后乱码的问题
            fileName = URLEncoder.encode(fileName, "utf-8");
            fileName = fileName.replace("+", "%20");
        } catch (final IOException e) {
            throw new BusinessException(e.getMessage());
        }
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);
        response.setHeader("Content-type", contentType);
        //开始时间
        final List<File> nFileList = new ArrayList<>();
        ZipOutputStream zipOut = null;
        WritableByteChannel writableByteChannel = null;
        try {
            zipOut = new ZipOutputStream(response.getOutputStream());
            writableByteChannel = Channels.newChannel(zipOut);
            for (final String fileName1 : fileNames) {
                File file = new File(url + fileName1);
                nFileList.add(file);
                int i = 1;
                log.info("开始获取文件: " + i + "当前文件名:" + file.getName()+file.length());
                try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
                    zipOut.putNextEntry(new ZipEntry(file.getName()));
                    fileChannel.transferTo(0,file.length(), writableByteChannel);
                }
                i++;
                log.info("结束获取文件: " + i + "当前文件名:" + file.getName());
            }
        } catch (Exception e) {
            log.info("文件压缩失败:{}",e.getMessage());
        }finally {
            IOUtils.closeQuietly(zipOut);
            IOUtils.closeQuietly(writableByteChannel);
        }
        for (final File file : nFileList) {
            System.gc();
            file.delete();
        }
    }

}
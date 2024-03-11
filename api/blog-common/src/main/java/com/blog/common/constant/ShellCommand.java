package com.blog.common.constant;

/**
 * @description: shell 脚本命令
 * @Author: 308501
 * @date 2024/3/11 14:24
 */

public class ShellCommand {

    // 导出sql文件
    public static final String exportSql = "sudo docker exec mysql bash /opt/docker/files/shell/exportSql.sh";

    // 导出博客数据压缩包
    public static final String exportBlogZip = "sh /opt/docker/files/shell/zipBlog.sh";

}

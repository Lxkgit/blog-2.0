# blog-2.1(微服务版博客)
## 部署说明
### 博客部署

功能说明：博客主体部分包含写文章、文档、日记、文件上传功能，可以创建多个用户使用

系统环境：Centos7.6 （由于使用的是docker部署，所以其他版本应该也可以，目前只测试过Centos7.6版本）

性能要求：4G RAM

部署文档：[博客部署文档](https://github.com/Lxkgit/blog-2.0/blob/blog-2.1/api/file/shell/%E5%8D%9A%E5%AE%A2%E9%83%A8%E7%BD%B2%E8%AF%B4%E6%98%8E.md)

### 树莓派部署

功能说明：配合博客主体安装，主要用于连接家庭内部单片机上的数据上报类与控制类传感器，通过博客网站监测与控制家庭内部设备，以及接收网站下发的命令存储网站文件数据与备份网站全部数据

硬件设备：树莓派4B

系统环境：Debian 12

部署文档：[树莓派部署文档](https://github.com/Lxkgit/blog-2.0/blob/blog-2.1/pi/src/main/resources/file/shell/%E6%A0%91%E8%8E%93%E6%B4%BE%E9%A1%B9%E7%9B%AE%E9%83%A8%E7%BD%B2%E8%AF%B4%E6%98%8E.md)


## 系统架构

#! /bin/bash

# 创建定时任务
# crontab -e写入的定时任务的文件的地址 /var/spool/cron

# Example of job definition:
# .---------------- minute (0 - 59)
# | .------------- hour (0 - 23)
# | | .---------- day of month (1 - 31)
# | | | .------- month (1 - 12) OR jan,feb,mar,apr ...
# | | | | .---- day of week (0 - 6) (Sunday=0 or 7) OR sun,mon,tue,wed,thu,fri,sat
# | | | | |
# * * * * * user-name command to be executed

# /etc/crontab  是系统级定时任务
# /var/spool/cron/{用户名} 是用户级脚本

echo "0 0 * * * /opt/docker/files/log/renameLog.sh  > /dev/null 2>&1 &"  >> /etc/crontab
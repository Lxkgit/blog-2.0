#! /bin/bash
# mysql数据导入脚本

# 数据库名字
nacosSql="nacos"
blogAuthSql="blog_auth"
blogContentSql="blog_content"
blogUserSql="blog_user"
blogFileSql="blog_file"
blogGatewaySql="blog_gateway"

# MySQL登陆密码
mysqlPassword=

mysqlSQL() {
  # 等待MySQL启动完成
  sleep 1m
  mysql -uroot -p${mysqlPassword} <<EOF

  drop database if exists ${nacosSql};
  CREATE DATABASE  ${nacosSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  use ${nacosSql};
  source /opt/docker/files/sql/${nacosSql}.sql;

  drop database if exists ${blogAuthSql};
  CREATE DATABASE  ${blogAuthSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  use ${blogAuthSql};
  source /opt/docker/files/sql/${blogAuthSql}.sql;

  drop database if exists ${blogContentSql};
  CREATE DATABASE  ${blogContentSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  use ${blogContentSql};
  source /opt/docker/files/sql/${blogContentSql}.sql;

  drop database if exists ${blogUserSql};
  CREATE DATABASE  ${blogUserSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  use ${blogUserSql};
  source /opt/docker/files/sql/${blogUserSql}.sql;

  drop database if exists ${blogFileSql};
  CREATE DATABASE  ${blogFileSql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  use ${blogFileSql};
  source /opt/docker/files/sql/${blogFileSql}.sql;

  drop database if exists ${blogGatewaySql};
  CREATE DATABASE  ${blogGatewaySql} DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  use ${blogGatewaySql};
  source /opt/docker/files/sql/${blogGatewaySql}.sql;

  exit

EOF
  exit 0
}

mysqlSQL
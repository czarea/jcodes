# jcodes

#### 介绍
代码生成工厂

#### 软件架构

![images](./docs/jcodes-mind.png)


#### 软件依赖

1. beetl（模版生成）
2. jdbc（获取数据库连接）
3. snakeyaml（配置文件）

#### 源码使用说明

1. 配置codes.yml,配置样例

```
gradleHome: E:/gradle/gradle-4.6
author: zhouzx
project:
    baseDir: D:/codes/test2
    template: D:\lvji_git\jcodes\templates
    name: lvji-codes-test
    groupId: cn.lvji
    version: 1.0
    module: pay
template:
    dir: D:\lvji_git\jcodes\templates\java\guide
    outPath: D:/codes/test2/bb
    basePackage: cn.lvji.order
    tables:
        - eo_api
        - eo_api_cache
    prefixes: eo_
    keys:
        module: zzx
        day: abc
db:
    userName: xxx
    password: xxx
    database: xxx
    url: jdbc:mysql://192.168.32.88:3306/xxx?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC
    driverClass: com.mysql.cj.jdbc.Driver
beetl:
    statementStart: "#"
    statementEnd: "#"
    placeholderStart: "@{"
    placeholderEnd: "}"
```

2. 配置模版文件，项目中有模版文件样例，在templates/java/guide目录下
3. 运行Bootstrap类

#### 脚本使用说明

```
参数：p pj    （创建项目）
      p code  （生产代码）
	  d       （删除代码）
```

1. windows系统下运行bin/jcodes.bat p pj
2. linux系统下运行bin/jcodes.sh p pj

#### idea插件使用说明

待开发
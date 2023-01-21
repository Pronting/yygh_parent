# yyph_parent(预约挂号系统)
## 项目构成
> yyph_parent整合了很多模块,为两套系统[yygh-site](https://github.com/Pronting/yygh_demo/tree/master/yygh-site)以及[vue-admin-template-master](https://gitee.com/pronting/yygh_parent_q)提供了后台逻辑处理
其中yyph-site为用户系统，主要进行用户的查看医院信息，预约排班，挂号缴费等功能。vue-admin-template-master主要是root管理。

## 项目参数修改
* nacos地址修改
* mongodb地址修改
* RabbitMQ地址修改
* GateWay地址修改
* Mysql地址修改


##注意事项
1. 在启动各个微服务之前，请先启动Nacos
2. 在启动Gateway之前，请先启动各个微服务
3. 请根据实际情况修改Nacos的配置文件
4. 请根据实际情况修改Gateway的配置文件
5. 接口测试为每一个服务的swagger地址

## 已实现的模块包括：
* 数据字典
* 用户模块
* 用户信息上传
* 短信与微信登录
* 医院详情
* 部门
* 医生的排班信息

## 项目架构图
![img.png](img.png)

**由于部分原因，该项目尚未集群**

## 项目流程图
![img_1.png](img_1.png)

> npm install 下载前台依赖

> npm run dev  运行前台系统

> 运行Application 后台启动类

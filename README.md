#  后端服务（sky-take-out）

> 面向餐饮外卖 O2O 场景的后端服务，基于 **Spring Boot** 多模块架构，为「商家管理端（Vue）」与「用户端微信小程序」提供统一 REST 接口，集成 JWT 鉴权、Redis 缓存、阿里云 OSS、微信支付 V3、定时任务与 Excel 报表导出等能力。

技术栈 · 架构 · 快速开始 · 接口文档见下文。

---

## 一、技术栈

| 类别 | 选型 | 版本 |
|---|---|---|
| 语言 / JDK | Java | 17 |
| 框架 | Spring Boot | 2.7.3 |
| 持久层 | MyBatis + XML 映射 | 2.2.0 |
| 连接池 | Druid | 1.2.1 |
| 分页 | PageHelper | 1.3.0 |
| 缓存 | Redis（Spring Data Redis） | — |
| 鉴权 | JWT（jjwt） | 0.9.1 |
| 接口文档 | Knife4j（基于 Swagger） | 3.0.2 |
| 对象存储 | 阿里云 OSS SDK | 3.10.2 |
| 支付 | 微信支付 APIv3（wechatpay-apache-httpclient） | 0.4.8 |
| 报表 | Apache POI | 3.16 |
| 其他 | Lombok、fastjson、AspectJ | — |

---

## 二、模块结构（Maven 多模块）

```
sky-take-out (聚合父工程, packaging=pom)
├─ sky-common    # 公共层：Result 统一返回、异常体系、枚举、工具类(JwtUtil/RedisUtil/Base64…)
├─ sky-pojo      # 实体/传输层：DO / DTO / VO / Query，对应各业务表
└─ sky-server    # 服务层：启动类 + controller / service / mapper / config / 拦截器 / AOP / 定时任务
```

`sky-server` 内分层（`com.sky` 包）：

```
SkyApplication          # 启动类：@MapperScan @EnableScheduling @EnableTransactionManagement
├─ controller
│  ├─ admin/            # B 端（管理端）：employee/category/dish/setmeal/order/shop/common
│  ├─ user/            # C 端（小程序）：user/addressBook/shoppingCart/category/dish/setmeal/shop/order
│  └─ nofity/          # 微信支付异步回调 PayNotifyController
├─ service / service.impl   # 业务逻辑
├─ mapper                   # MyBatis 接口（+ resources/mapper/*.xml）
├─ interceptor              # JwtTokenAdminInterceptor / JwtTokenUserInterceptor（双端鉴权）
├─ config                   # WebMvcConfiguration / RedisConfiguration / OssConfiguration
├─ aspect + annotation      # AutoFillAspect + @AutoFill（AOP 自动填充创建/更新时间等公共字段）
├─ handler                  # GlobalExceptionHandler（统一异常处理）
└─ task                     # OrderTask（Spring Task：超时订单取消、配送中订单状态更新）
```

---

## 三、核心设计与机制

- **统一返回**：所有接口返回 `Result{ msg, code, data }`，`code==1` 为成功；前端据此判断业务结果。
- **统一异常处理**：`GlobalExceptionHandler` 捕获自定义 `BaseException`/业务异常，转成 `Result.error(msg)`，避免异常堆栈直出。
- **JWT 双拦截器鉴权**：B 端与 C 端各自 `HandlerInterceptor`，从请求头 `token` 解析并校验，未登录/过期返回业务码 `401`。
- **AOP 自动填充**：自定义注解 `@AutoFill` + `AutoFillAspect`，统一填充实体的 `createTime/updateTime/createUser/updateUser` 等公共字段，免去重复代码。
- **Redis 缓存**：菜品/套餐起售状态、店铺营业状态、用户 token、购物车（Hash）等热点数据入 Redis；更新数据库后**删除缓存**保证一致性。
- **定时任务**：`OrderTask` 用 Spring `@Scheduled` 每分钟轮询——下单超 15 分钟未支付自动取消；支付超 1 小时处于配送中的订单自动置为已完成。
- **分页**：基于 PageHelper，`page/pageSize` → `PageResult`。
- **文件与支付**：图片上传阿里云 OSS；下单支付走微信支付 APIv3，回调 `PayNotifyController` 更新订单状态。
- **报表导出**：营业额/订单/用户统计等数据用 Apache POI 导出 Excel。

---

## 四、功能模块

**B 端（管理端 `/admin`）**：员工管理、分类管理、菜品管理（含口味/规格、起售停售）、套餐管理、订单管理（投递/取消/完成/催单）、店铺营业状态、文件上传。

**C 端（小程序 `/user`）**：微信登录、地址簿、购物车、菜品/套餐浏览、下单支付、历史订单、店铺状态查询。

---

## 五、数据库

- **MySQL**：员工、分类、菜品、菜品口味、套餐、套餐菜品关系（多对多）、订单、订单明细、地址簿、购物车、微信用户、日营业额流水等表。
- **建表脚本**：见 `sql/` 目录（导入即可初始化库）。
- **Redis**：缓存与购物车存储。

> 表结构与 SQL 脚本放置于 `sql/`；导入方式见下。

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS sky_takeout DEFAULT CHARSET utf8mb4;"
mysql -u root -p sky_takeout < sql/sky_takeout_schema.sql
```

---

## 六、快速开始

**环境**：JDK 17、Maven 3.6+、MySQL 8、Redis。

1. **导入数据库**：创建库 `sky_takeout`，执行 `sql/` 下的建表脚本。
2. **改配置**：编辑 `sky-server/src/main/resources/application.yml` 中的
   - 数据源（MySQL 地址/账号/密码）
   - Redis（地址/端口/密码）
   - 阿里云 OSS（endpoint / bucket / accessKey）
   - 微信支付（商户号 / APIv3 密钥 / 证书）
   > ⚠️ 这些是敏感信息，**本地配置请勿把真实密钥提交到公开仓库**，建议用环境变量或本地 profile 覆盖。
3. **启动**：运行 `SkyApplication`，或
   ```bash
   mvn -pl sky-server -am spring-boot:run
   ```
4. **接口文档**：启动后访问
   ```
   http://localhost:8080/doc.html
   ```
   接口速览见 [`docs/API.md`](docs/API.md)。

---

## 七、接口文档

- 在线（Knife4j 自动生成）：`http://localhost:8080/doc.html`
- 速览说明：[`docs/API.md`](docs/API.md)
- 导出 OpenAPI：后端运行时 `curl http://localhost:8080/v2/api-docs -o docs/openapi.json`

---

## 八、关联仓库（三端）

| 端 | 说明 |
|---|---|
| 用户端小程序 | uni-app（`mp-weixin` 为编译产物） |
| 商家管理端 | Vue2 + TypeScript + Element UI |
| 后端 | 本仓库（Spring Boot） |

---

## 九、备注

本项目为餐饮外卖方向的实战/学习项目，实现了从数据库设计、后端服务到管理端与小程序前端的完整链路。重点可关注：多模块分层、JWT 双端鉴权、AOP 公共字段填充、Redis 缓存一致性、Spring Task 订单状态处理、微信支付与文件上传的接入。

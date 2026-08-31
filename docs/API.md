# 接口文档 · 苍穹外卖后端

> 后端已集成 **Knife4j 3.0.2**，接口文档由代码上的 `@Api / @ApiOperation` 注解**自动生成**。本文是速览版；完整出入参请看在线文档或导出的 OpenAPI JSON。

## 查看方式

- **在线文档（推荐）**：启动后端后访问
  ```
  http://localhost:8080/doc.html
  ```
- **导出 OpenAPI JSON**（后端运行时执行，可导入 Apifox / Swagger UI 离线看）：
  ```bash
  curl http://localhost:8080/v2/api-docs -o docs/openapi.json
  ```

## 接口分组

系统分两套接口：
- **B 端（管理端）**：前缀 `/admin/**`，供 Vue 管理后台调用，需携带登录返回的 `token` 请求头。
- **C 端（用户端）**：前缀 `/user/**`，供微信小程序调用。

## B 端 · 管理端接口（`/admin`）

| 模块 | 路径前缀 | Controller | 说明 |
|---|---|---|---|
| 登录/员工 | `/admin/employee` | `EmployeeController` | 员工登录、增删改查、启用/禁用 |
| 分类管理 | `/admin/category` | `CategoryController` | 菜品/套餐分类的增删改查、启停 |
| 菜品管理 | `/admin/dish` | `DishController` | 菜品 CRUD、起售停售、口味/规格 |
| 套餐管理 | `/admin/setmeal` | `SetmealController` | 套餐 CRUD、关联菜品、启停 |
| 订单管理 | `/admin/order` | `OrderController` | 订单查询、投递、取消、完成、催单、统计 |
| 店铺管理 | `/admin/shop` | `ShopController` | 营业状态设置/查询、营业时间 |
| 通用/文件 | `/admin/common` | `CommomController` | 文件上传/下载（图片等） |

## C 端 · 用户端接口（`/user`）

| 模块 | 路径前缀 | Controller | 说明 |
|---|---|---|---|
| 用户 | `/user/user` | `UserController` | 微信登录、用户信息、下单 |
| 地址簿 | `/user/addressBook` | `AddressBookController` | 收货地址增删改查、默认地址 |
| 购物车 | `/user/shoppingCart` | `ShoppingCartController` | 加入/修改/清空购物车（Redis 存储） |
| 分类浏览 | `/user/category` | `CategoryController` | C 端分类查询 |
| 菜品浏览 | `/user/dish` | `DishController` | 按分类查起售菜品 |
| 套餐浏览 | `/user/setmeal` | `SetmealController` | 套餐查询 |
| 店铺 | `/user/shop` | `ShopController` | 查询营业状态（状态存 Redis） |
| 用户订单 | `/user/order` | `OrderController` | 提交订单、支付、历史订单、取消 |

## 回调 / 其他

| 模块 | 路径 | Controller | 说明 |
|---|---|---|---|
| 支付回调 | `/notify/paySuccess` | `PayNotifyController` | 微信支付结果异步通知，更新订单状态 |

## 鉴权约定

- B 端登录成功返回 JWT `token`，前端存 Cookie/Vuex，后续请求放在**请求头 `token`** 中；后端拦截器校验，未登录/过期返回业务码 `401`，前端跳登录页。
- C 端通过微信 `code` 换取 JWT，同样以 `token` 头携带。
- 统一响应结构：`{ "msg": "", "code": 1, "data": {} }`，`code==1` 为成功。

## 约定与备注

- 文件基于 **Spring Boot + MyBatis + MySQL + Redis**；接口以 Spring MVC `@RestController` 实现。
- 分页接口统一使用 `page` / `pageSize` 参数，返回 `PageResult`。
- 本文只列到模块级；**具体每个接口的入参、出参、字段说明以 `/doc.html` 在线文档为准**（那里是逐接口详细的）。

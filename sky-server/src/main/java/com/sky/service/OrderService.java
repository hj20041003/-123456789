package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    /**
     * 用户提交订单
     *
     * @param ordersSubmitDTO 订单提交数据
     * @return 订单提交结果
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 订单支付数据
     * @return 支付所需参数
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo 微信支付交易号
     */
    void paySuccess(String outTradeNo);

    /**
     * 订单详情查询（校验用户归属）
     *
     * @param id 订单id
     * @return 订单详情
     */
    OrderVO detailOrThrow(Long id);

    /**
     * 用户端订单分页查询
     *
     * @param page     页码
     * @param pageSize 每页记录数
     * @param status   订单状态
     * @return 订单分页结果
     */
    PageResult pageQuery4User(int page, int pageSize, Integer status);

    /**
     * 用户取消订单
     *
     * @param id 订单id
     */
    void userCancelById(Long id) throws Exception;

    /**
     * 再来一单
     *
     * @param id 订单id
     */
    void repetition(Long id);

    /**
     * 条件搜索订单
     *
     * @param ordersPageQueryDTO 分页查询条件
     * @return 订单分页结果
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各状态订单数量统计
     *
     * @return 各状态订单数量
     */
    OrderStatisticsVO statistics();

    /**
     * 管理端查询订单详情（不校验用户归属）
     *
     * @param id 订单id
     * @return 订单详情
     */
    OrderVO details(Long id);

    /**
     * 接单
     *
     * @param ordersConfirmDTO 接单数据
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     *
     * @param ordersRejectionDTO 拒单数据
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 商家取消订单
     *
     * @param ordersCancelDTO 取消订单数据
     */
    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /**
     * 派送订单
     *
     * @param id 订单id
     */
    void delivery(Long id);

    /**
     * 完成订单
     *
     * @param id 订单id
     */
    void complete(Long id);

    /**
     * 客户催单
     *
     * @param id 订单id
     */
    void reminder(Long id);
}

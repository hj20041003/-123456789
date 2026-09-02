package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     *
     * @param order 订单信息
     */
    void insert(Orders order);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber 订单号
     * @return 订单信息
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 根据id查询订单
     *
     * @param id 订单id
     * @return 订单信息
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 修改订单信息
     *
     * @param orders 订单信息
     */
    void update(Orders orders);

    /**
     * 修改订单状态
     *
     * @param orderStatus      订单状态
     * @param orderPaidStatus  支付状态
     * @param checkOutTime     支付时间
     * @param id               订单id
     */
    @Update("update orders set status = #{orderStatus}, pay_status = #{orderPaidStatus}, checkout_time = #{checkOutTime} where id = #{id}")
    void updateStatus(@Param("orderStatus") Integer orderStatus,
                      @Param("orderPaidStatus") Integer orderPaidStatus,
                      @Param("checkOutTime") LocalDateTime checkOutTime,
                      @Param("id") Long id);

    /**
     * 根据状态统计订单数量
     *
     * @param status 订单状态
     * @return 订单数量
     */
    @Select("select count(*) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 分页条件查询并按下单时间排序
     *
     * @param ordersPageQueryDTO 分页查询条件
     * @return 订单分页结果
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据状态和下单时间查询订单
     *
     * @param status    订单状态
     * @param orderTime 下单时间
     * @return 订单列表
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 根据时间范围和状态统计营业额
     *
     * @param map 查询条件(begin, end, status)
     * @return 营业额
     */
    Double sumByMap(Map map);

    /**
     * 根据时间范围和状态统计订单数量
     *
     * @param map 查询条件(begin, end, status)
     * @return 订单数量
     */
    Integer countByMap(Map map);

    /**
     * 统计指定时间内销量排名前十的商品
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 销量前十商品列表
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);

}

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


/*插入订单数据*/
    @Mapper
    public interface OrderMapper {
        /**
         * 插入订单数据
         * @param order
         */
        void insert(Orders order);

        /**
         * 根据订单号查询订单
         * @param orderNumber
         */
        @Select("select * from orders where number = #{orderNumber}")
        Orders getByNumber(String orderNumber);

        /*
         * 根据id查询订单
         * @param id
         */
        @Select("select * from orders where id = #{id}")
        Orders getById(Long id);

        /**
         * 修改订单信息
         * @param orders
         */
        void update(Orders orders);
    @Update("update orders set status = #{orderStatus}, pay_status = #{orderPaidStatus}, checkout_time = #{checkOutTime} where id = #{id}")
    void updateStatus(@Param("orderStatus") Integer orderStatus,
                      @Param("orderPaidStatus") Integer orderPaidStatus,
                      @Param("checkOutTime") LocalDateTime checkOutTime,
                      @Param("id") Long id);
    /**
     * 根据状态统计订单数量
     * @param status
     */
    @Select("select count(*) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 分页条件查询并按下单时间排序
     * @param ordersPageQueryDTO
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /*
     * 根据状态和下单时间查询订单
     * @param status
     * @param orderTime
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 根据时间范围和状态统计营业额
     * @param map (begin, end, status)
     */
    Double sumByMap(Map map);
    /**
     * 根据时间范围和状态统计订单数量
     * @param map (begin, end, status)
     */
    Integer countByMap(Map map);

    /**统计指定时间内销量排名前十
     *
     * @param begin
     * @param end
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);


}
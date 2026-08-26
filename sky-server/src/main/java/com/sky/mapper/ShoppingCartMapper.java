package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /*
     * 动态条件查询购物车数据
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /*
     * 根据id修商品数量
     * @param shoppingCart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);
    /*
     * 插入购物车数据
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart (dish_flavor ,user_id, dish_id, setmeal_id, name, amount, image, number, create_time) " +
            "values (#{dishFlavor},#{userId}, #{dishId}, #{setmealId}, #{name}, #{amount}, #{image}, #{number}, #{createTime})")
    void insert(ShoppingCart shoppingCart);
}

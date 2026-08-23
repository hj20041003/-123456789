package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
//    根据套餐id来查询对应的套餐id
//    select setmeal_id from setmeal_dish where dish_id in (1,2,3)
    List<Long> getSetmealDishIds(List<Long> dishIds);

    List<Long> getSetmealIdsByDishIds(List<Long> ids);
}


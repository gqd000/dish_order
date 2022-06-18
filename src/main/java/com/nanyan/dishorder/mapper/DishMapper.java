package com.nanyan.dishorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanyan.dishorder.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}

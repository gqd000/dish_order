package com.nanyan.dishorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyan.dishorder.entity.DishFlavor;
import com.nanyan.dishorder.mapper.DishFlavorMapper;
import com.nanyan.dishorder.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceimpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}

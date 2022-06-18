package com.nanyan.dishorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyan.dishorder.entity.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService extends IService<Category> {
    //添加remove方法，检查删除的分类是否关联了菜品或者套餐
    public void remove(Long id);
}


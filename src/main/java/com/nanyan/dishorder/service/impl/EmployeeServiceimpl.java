package com.nanyan.dishorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyan.dishorder.entity.Employee;
import com.nanyan.dishorder.mapper.EmployeeMapper;
import com.nanyan.dishorder.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceimpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

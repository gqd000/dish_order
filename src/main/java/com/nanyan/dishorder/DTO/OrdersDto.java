package com.nanyan.dishorder.DTO;

import com.nanyan.dishorder.entity.OrderDetail;
import com.nanyan.dishorder.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}

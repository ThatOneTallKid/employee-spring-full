package com.increff.employee.model.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.increff.employee.model.form.OrderForm;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class OrderData {
    private int id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
}

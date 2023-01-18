package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@MappedSuperclass
@Getter
@Setter
public abstract class AbstractPojo implements Serializable {
    @CreationTimestamp
    @Column
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column
    private Timestamp updatedAt;
//  Implement
//    @Version
//    @Column
//    private long version;
}

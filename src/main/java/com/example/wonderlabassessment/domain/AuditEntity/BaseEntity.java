package com.example.wonderlabassessment.domain.AuditEntity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by eddyT on 2023/03/13.
 */


@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @NotNull
    private Date insertDate;
    @NotNull
    private Date updateDate;
    @Version
    protected long version;
    private String uniqueId;

    public BaseEntity() {
    }

    @PrePersist
    public void initData() {
        insertDate = new Date();
        updateDate = new Date();
        if (uniqueId == null) {
            uniqueId = UUID.randomUUID().toString();
        }
    }

    @PreUpdate
    public void updateData() {
        updateDate = new Date();
        if (uniqueId == null) {
            uniqueId = UUID.randomUUID().toString();
        }
    }

}


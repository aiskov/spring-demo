package ee.aiskov.test.client.model;

import ee.aiskov.test.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "client")
public class Address extends BaseEntity {
    @Column(length = 100)
    private String street;

    @Column(length = 100)
    private String index;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;
}

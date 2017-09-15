package ee.aiskov.test.client.model;

import ee.aiskov.test.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

import static org.hibernate.annotations.FetchMode.SELECT;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Client extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "client")
    @Fetch(SELECT)
    private Set<Address> addresses;

    public Client(String id, String name, Set<Address> addresses) {
        this.setId(id);
        this.name = name;
        this.addresses = addresses;
    }
}

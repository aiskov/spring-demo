package ee.aiskov.test.common.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.time.LocalDateTime;

import static ee.aiskov.test.common.model.PersistenceState.ACTIVE;
import static ee.aiskov.test.common.model.PersistenceState.DELETED;
import static java.time.LocalDateTime.now;
import static javax.persistence.EnumType.STRING;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column
    private LocalDateTime updated;

    @Column
    private LocalDateTime created;

    @Column
    @Version
    private Long version;

    @Column
    @Enumerated(STRING)
    private PersistenceState state;

    public void delete() {
        this.state = DELETED;
    }

    public void restore() {
        this.state = ACTIVE;
    }

    @PrePersist
    protected void onCreate() {
        this.state = ACTIVE;

        this.updated = now();
        this.created = now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = now();
    }
}

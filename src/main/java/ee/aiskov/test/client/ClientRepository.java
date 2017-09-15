package ee.aiskov.test.client;

import ee.aiskov.test.client.model.Client;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
public interface ClientRepository extends PagingAndSortingRepository<Client, String>, JpaSpecificationExecutor<Client> {

    @Query("SELECT clients FROM Client clients WHERE clients.state = 'ACTIVE' ORDER BY clients.name")
    List<Client> findActive();

}

package ee.aiskov.test.client;

import ee.aiskov.test.BaseTestWithDb;
import ee.aiskov.test.client.model.Client;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientRepositoryTest extends BaseTestWithDb {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Sql("/database/client/inactive-client.sql")
    public void findActive() throws Exception {
        // Given

        // When
        List<Client> result = this.clientRepository.findActive();

        // Then
        assertThat(result)
                .hasSize(4);
    }

}
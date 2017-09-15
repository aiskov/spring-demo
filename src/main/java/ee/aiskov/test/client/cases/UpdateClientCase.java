package ee.aiskov.test.client.cases;

import ee.aiskov.test.client.ClientController;
import ee.aiskov.test.client.ClientRepository;
import ee.aiskov.test.client.model.Client;
import ee.aiskov.test.client.request.AddOrUpdateClient;
import ee.aiskov.test.common.Converter;
import ee.aiskov.test.common.exception.NoResourceFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@Transactional
@AllArgsConstructor
public class UpdateClientCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    private static final String CLIENT_ADD_COUNTER = "counter.client.update";

    private final CounterService counterService;
    private final ClientRepository clientRepository;
    private final Converter converter;

    public void process(String id, AddOrUpdateClient data) {
        LOGGER.info("Add client {} - {}", id, data);

        Client client = this.retrieve(id).orElseThrow(() -> new NoResourceFoundException(id, Client.class));
        converter.populate(data, client);
        this.clientRepository.save(client);

        this.updateMetrics();
    }

    private Optional<Client> retrieve(String id) {
        return ofNullable(this.clientRepository.findOne(id));
    }

    private void updateMetrics() {
        this.counterService.increment(CLIENT_ADD_COUNTER);
    }
}

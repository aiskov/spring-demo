package ee.aiskov.test.client.cases;

import ee.aiskov.test.client.ClientController;
import ee.aiskov.test.client.ClientRepository;
import ee.aiskov.test.client.model.Client;
import ee.aiskov.test.client.request.AddOrUpdateClient;
import ee.aiskov.test.common.Converter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AddClientCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    private static final String CLIENT_ADD_COUNTER = "counter.client.add";

    private final CounterService counterService;
    private final ClientRepository clientRepository;
    private final Converter converter;

    public String process(AddOrUpdateClient data) {
        LOGGER.info("Add client {}", data);

        Client client = converter.convert(Client.class).apply(data);
        this.clientRepository.save(client);

        this.updateMetrics();

        return client.getId();
    }

    private void updateMetrics() {
        this.counterService.increment(CLIENT_ADD_COUNTER);
    }
}

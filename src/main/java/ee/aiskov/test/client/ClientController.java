package ee.aiskov.test.client;

import ee.aiskov.test.client.cases.AddClientCase;
import ee.aiskov.test.client.cases.DeleteClientCase;
import ee.aiskov.test.client.cases.UpdateClientCase;
import ee.aiskov.test.client.dto.ClientListItemDto;
import ee.aiskov.test.client.model.Client;
import ee.aiskov.test.client.request.AddOrUpdateClient;
import ee.aiskov.test.common.BaseController;
import ee.aiskov.test.common.Converter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static ee.jiss.commons.lang.CheckUtils.isNotEmptyCollection;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Api
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(path = "api/v1/clients", produces = APPLICATION_JSON_UTF8_VALUE)
public class ClientController extends BaseController {
    private final DeleteClientCase deleteClientCase;
    private final AddClientCase addClientCase;
    private final UpdateClientCase updateClientCase;

    private final ClientRepository clientRepository;

    private final Converter converter;

    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved", response = ClientListItemDto[].class),
            @ApiResponse(code = 204, message = "Retrieved but empty")
    })
    @GetMapping
    public ResponseEntity<List<ClientListItemDto>> list() {
        List<ClientListItemDto> result = this.clientRepository.findActive().stream()
                .map(converter.convert(ClientListItemDto.class)).collect(toList());
        return isNotEmptyCollection(result) ? ok(result) : noContent().build();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved", response = Client.class),
            @ApiResponse(code = 404, message = "Client not exists")
    })
    @GetMapping(":id")
    public ResponseEntity<Client> one(@RequestParam String id) {
        return ofNullable(this.clientRepository.findOne(id))
                .map(ResponseEntity::ok).orElseGet(notFound()::build);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved, returns ID", response = String.class),
            @ApiResponse(code = 404, message = "Client not exists")
    })
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> create(@RequestBody @Valid AddOrUpdateClient data) {
        return ok(this.addClientCase.process(data));
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Client not exists")
    })
    @PutMapping(path = ":id", consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> update(@RequestParam String id, @RequestBody @Valid AddOrUpdateClient data) {
        this.updateClientCase.process(id, data);
        return ok().build();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully removed"),
            @ApiResponse(code = 404, message = "Client not exists (even deleted previously)")
    })
    @DeleteMapping(":id")
    public ResponseEntity<Void> delete(@RequestParam String id) {
        this.deleteClientCase.process(id);
        return ok().build();
    }
}

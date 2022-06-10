package br.com.edsontofolo.labscontrol.users.ui.controllers;

import br.com.edsontofolo.labscontrol.users.data.AlbumsServiceClient;
import br.com.edsontofolo.labscontrol.users.service.UsersService;
import br.com.edsontofolo.labscontrol.users.shared.UserDto;
import br.com.edsontofolo.labscontrol.users.ui.model.AlbumResponseModel;
import br.com.edsontofolo.labscontrol.users.ui.model.CreateUserRequestModel;
import br.com.edsontofolo.labscontrol.users.ui.model.CreateUserResponseModel;
import br.com.edsontofolo.labscontrol.users.ui.model.UserResponseModel;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;
    private final Environment env;
    private final RestTemplate restTemplate;
    private final AlbumsServiceClient albumsServiceClient;

    public UsersController(UsersService usersService, Environment env, RestTemplate restTemplate, AlbumsServiceClient albumsServiceClient) {
        this.usersService = usersService;
        this.env = env;
        this.restTemplate = restTemplate;
        this.albumsServiceClient = albumsServiceClient;
    }

    @GetMapping("/status/check")
    public String status() {
        return "Working on port=" + env.getProperty("local.server.port") + " and with token=" + env.getProperty("token.secret");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto requestUserDto = mapper.map(userDetails, UserDto.class);

        UserDto createdUser = usersService.createUser(requestUserDto);

        CreateUserResponseModel userResponse = mapper.map(createdUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
        UserDto userDto = usersService.getUserById(userId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserResponseModel returnValue = mapper.map(userDto, UserResponseModel.class);

        String albumsUrl = String.format("http://albums-ws/users/%s/albums", userId);

        ResponseEntity<List<AlbumResponseModel>> albumsListResponse =
                restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {});

        List<AlbumResponseModel> albumsList = albumsListResponse.getBody();

        returnValue.setAlbums(albumsList);

        return ResponseEntity.ok(returnValue);
    }

    @GetMapping("/feign-client/{userId}")
    public ResponseEntity<UserResponseModel> getUserByFeignClient(@PathVariable String userId) {
        UserDto userDto = usersService.getUserById(userId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserResponseModel returnValue = mapper.map(userDto, UserResponseModel.class);

        logger.info("Before calling albums Microservice");
        List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);
        logger.info("After calling albums Microservice");

        returnValue.setAlbums(albumsList);

        return ResponseEntity.ok(returnValue);
    }
}

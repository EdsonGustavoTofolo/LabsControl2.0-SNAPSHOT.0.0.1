package br.com.edsontofolo.labscontrol.users.ui.controllers;

import br.com.edsontofolo.labscontrol.users.service.UsersService;
import br.com.edsontofolo.labscontrol.users.shared.UserDto;
import br.com.edsontofolo.labscontrol.users.ui.model.CreateUserRequestModel;
import br.com.edsontofolo.labscontrol.users.ui.model.CreateUserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersService usersService;
    private final Environment env;

    public UserController(UsersService usersService, Environment env) {
        this.usersService = usersService;
        this.env = env;
    }

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + env.getProperty("local.server.port");
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
}

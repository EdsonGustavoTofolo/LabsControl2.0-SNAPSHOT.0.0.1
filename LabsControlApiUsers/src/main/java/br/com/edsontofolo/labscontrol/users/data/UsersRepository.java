package br.com.edsontofolo.labscontrol.users.data;

import br.com.edsontofolo.labscontrol.users.shared.UserDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserId(String userId);
}

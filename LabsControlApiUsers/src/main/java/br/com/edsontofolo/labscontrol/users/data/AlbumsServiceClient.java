package br.com.edsontofolo.labscontrol.users.data;

import br.com.edsontofolo.labscontrol.users.ui.model.AlbumResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/*
    name is the spring.application.name
 */
@FeignClient(name = "albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    @Retry(name = "albums-ws")
    @CircuitBreaker(name = "albums-ws", fallbackMethod = "getAlbumsFallback")
    List<AlbumResponseModel> getAlbums(@PathVariable String id);

    default List<AlbumResponseModel> getAlbumsFallback(String id, Throwable throwable) {
        return new ArrayList<>();
    }
}
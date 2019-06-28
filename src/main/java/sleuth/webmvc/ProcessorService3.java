package sleuth.webmvc;

import brave.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class ProcessorService3 {

    private final WebClient webClient;
    private final Tracer tracer;

    public ProcessorService3(WebClient.Builder webClientBuilder, Tracer tracer) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:9004")
                .build();
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProcessorService3.class,
                "--spring.application.name=processor-service-3",
                "--server.port=9003"
        );
    }

    @RequestMapping("/api/processor3")
    public Mono<String> callBackend() {
        return this.webClient
                .get()
                .uri("/api/processor4")
                .retrieve()
                .bodyToMono(String.class);
    }
}

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
public class ProcessorService2A {


    private final WebClient webClient;
    private final Tracer tracer;

    public ProcessorService2A(WebClient.Builder webClientBuilder, Tracer tracer) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:9003")
                .build();
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProcessorService2A.class,
                "--spring.application.name=processor-service-2A",
                "--server.port=9002"
        );
    }

    @RequestMapping("/api/processor2A")
    public Mono<String> callBackend() {
        return this.webClient
                .get()
                .uri("/api/processor3")
                .retrieve()
                .bodyToMono(String.class);
    }
}

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
public class ProcessorService2B {


    private final WebClient webClient;
    private final Tracer tracer;

    public ProcessorService2B(WebClient.Builder webClientBuilder, Tracer tracer) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:9003")
                .build();
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProcessorService2B.class,
                "--spring.application.name=processor-service-2B",
                "--server.port=9008"
        );
    }

    @RequestMapping("/api/processor2B")
    public Mono<String> callBackend() {
        return webClient
                .get()
                .uri("/api/processor3")
                .retrieve()
                .bodyToMono(String.class);
    }
}

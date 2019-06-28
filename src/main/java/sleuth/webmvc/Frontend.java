package sleuth.webmvc;

import brave.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
@CrossOrigin // So that javascript can be hosted elsewhere
public class Frontend {

    private final WebClient webClient;
    private final Tracer tracer;

    public Frontend(WebClient.Builder webClientBuilder, Tracer tracer) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:9001")
                .build();
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Frontend.class,
                "--spring.application.name=frontend",
                "--server.port=9000"
        );
    }

    @RequestMapping("/")
    public Mono<String> callBackend() {
        System.out.println("Current Span: " + this.tracer.currentSpan());
        return this.webClient
                .get()
                .uri("/api/processor1")
                .retrieve()
                .bodyToMono(String.class);
    }
}

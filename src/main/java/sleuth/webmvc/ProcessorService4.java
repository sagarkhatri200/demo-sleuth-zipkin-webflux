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
public class ProcessorService4 {

    private final WebClient webClient;
    private final Tracer tracer;

    public ProcessorService4(WebClient.Builder webClientBuilder, Tracer tracer) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:9005")
                .build();
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProcessorService4.class,
                "--spring.application.name=processor-service-4",
                "--server.port=9004"
        );
    }

    @RequestMapping("/api/processor4")
    public Mono<String> callBackend() {
        System.out.println("Current Span: " + tracer.currentSpan());
        return this.webClient
                .get()
                .uri("/api/backend")
                .retrieve()
                .bodyToMono(String.class);
    }
}

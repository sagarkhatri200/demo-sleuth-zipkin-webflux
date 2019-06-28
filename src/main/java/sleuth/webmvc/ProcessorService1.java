package sleuth.webmvc;

import brave.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class ProcessorService1 {

    private final WebClient webClient2A;
    private final WebClient webClient2B;
    private final Tracer tracer;

    public ProcessorService1(WebClient.Builder webClientBuilder, Tracer tracer) {
        this.webClient2A = webClientBuilder
                .baseUrl("http://localhost:9002")
                .build();
        this.webClient2B = webClientBuilder
                .baseUrl("http://localhost:9008")
                .build();
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProcessorService1.class,
                "--spring.application.name=processor-service-1",
                "--server.port=9001"
        );
    }

    @GetMapping("/api/processor1")
    public Mono<String> callBackend() {
        System.out.println("Got here");

        Mono<String> response2A = this.webClient2A
                .get()
                .uri("/api/processor2A")
                .retrieve()
                .bodyToMono(String.class);

        Mono<String> response2B = this.webClient2B
                .get()
                .uri("/api/processor2B")
                .retrieve()
                .bodyToMono(String.class);

        return Mono.zip(response2A, response2B).map(c -> c.getT2());
    }
}

package sleuth.webmvc;

import brave.ScopedSpan;
import brave.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class Backend {

    private final Tracer tracer;

    public Backend(Tracer tracer) {
        this.tracer = tracer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Backend.class,
                "--spring.application.name=backend",
                "--server.port=9005"
        );
    }

    @RequestMapping("/api/backend")
    public Mono<String> printDate() throws Exception {

        System.out.println("Current Span: " + tracer.currentSpan());

        if (Math.random() < 0.2) {
            throw new Exception("Error Happened");
        }

        /*  Can Opener */
        ScopedSpan looking_for_can_opener = tracer.startScopedSpan("Looking for Can Opener");
        try {

            for (int i = 0; i < 10; i++) {
                System.out.println("printing " + i);
            }

        } catch (RuntimeException | Error e) {
            looking_for_can_opener.error(e);
            throw e;
        } finally {
            looking_for_can_opener.finish();
        }

        /*  Can */
        ScopedSpan looking_for_can = tracer.startScopedSpan("Looking for Can");
        try {

            for (int i = 0; i < 10; i++) {
                System.out.println("printing " + i);
            }
            Thread.sleep(100);

        } catch (RuntimeException | Error e) {
            looking_for_can.error(e);
            throw e;
        } finally {
            looking_for_can.finish();
        }


        /*  Opening the Can  */
        ScopedSpan opening_the_can = tracer.startScopedSpan("Opening the Can");
        try {

            for (int i = 0; i < 10; i++) {
                System.out.println("printing " + i);
            }
            Thread.sleep(10);

        } catch (RuntimeException | Error e) {
            opening_the_can.error(e);
            throw e;
        } finally {
            opening_the_can.finish();
        }

        /*  Scan tuna  */
        ScopedSpan deep_scan_on_tuna = tracer.startScopedSpan("Deep Scan on Tuna");
        try {

            Thread.sleep(1000);

        } catch (RuntimeException | Error e) {
            deep_scan_on_tuna.error(e);
            throw e;
        } finally {
            deep_scan_on_tuna.finish();
        }

        /*  Lunch Time  */
        ScopedSpan lunch_time = tracer.startScopedSpan("Lunch Time");
        try {
            Thread.sleep(500);
        } catch (RuntimeException | Error e) {
            lunch_time.error(e);
            throw e;
        } finally {
            lunch_time.finish();
        }

        return Mono.just(new Date().toString());
    }
}

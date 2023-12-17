package donggukthon.volunmate.controller;

import donggukthon.volunmate.dto.exception.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {
    @GetMapping("/health")
    public ResponseDto<String> health() {
        return ResponseDto.ok("OK");
    }
}

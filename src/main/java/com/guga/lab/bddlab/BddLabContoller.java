package com.guga.lab.bddlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BddLabContoller {

    @Autowired
    private FixerService fixerService;
    @Autowired
    private AuthenticationService authenticationService;
    private final String DEFAULT_CURRENCY = "CLP";

    @GetMapping("/convert")
    public ResponseEntity<String> convert(@RequestHeader(value = "Authorization", required = false) String authorization,
                                          @RequestParam(name = "amount") double amount,
                                          @RequestParam(name = "from") String from,
                                          @RequestParam(name = "to", required = false) String to,
                                          @RequestParam(name = "date", required = false) String date) {
        if (authenticationService.isValid(authorization)) {
            return new ResponseEntity<String>("" + fixerService.retrieveRate(amount, from,
                    Optional.ofNullable(to).orElse(DEFAULT_CURRENCY), date), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bla bla");
    }
}
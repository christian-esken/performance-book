package org.cesken.perfbook.chapter.execution;

import org.cesken.perfbook.util.ConcurrentUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlowHelloService {
    @GetMapping(value = "/hello/{user}", produces = "text/plain;charset=UTF-8")
    public String hello(@PathVariable String user, @RequestParam("delay") int delay) {
        ConcurrentUtil.sleepInterruptably(delay);
        return "Hello " + user;
    }
}

package com.kuncms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class WatchController {
	@RequestMapping("/watch")
    public String watch() {
        return "watch";
    }
}

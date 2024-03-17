package be.kdg.programming5.musicwebsite.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sessionHistory")
public class SessionHistoryController {
    @Value("${session-history.attribute.name}")
    private String SESSION_HISTORY_ATTRIBUTE_NAME;
    @GetMapping
    public String getSessionHistoryPage(Model model){
        model.addAttribute("sessionHistoryAttributeName", SESSION_HISTORY_ATTRIBUTE_NAME);
        return "view/session/sessionHistory";
    }
}

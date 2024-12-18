package edu.bothell.multi_ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicReference;


@Controller
public class WebController {

    
    private final Control c;
    private final List<String> sessions;
    private final int MAX_PLAYERS;
    //private final AtomicReference<String>[] player1Sessions = new AtomicReference<String>[]{null};

    public WebController(Control c) {
        this.c = c;
        this.MAX_PLAYERS = c.getMaxPlayers(); 
        this.sessions = new ArrayList<>();
    }

    @GetMapping("/game")
    public String getGame( HttpSession session, Model m ) {
        String sessionId = session.getId();
        String role = "";
        
        synchronized(sessions){
            if (!sessions.contains(sessionId) && sessions.size() < MAX_PLAYERS) {
                sessions.add(sessionId);
            }
        }

        synchronized(sessions){
            int i = sessions.indexOf(sessionId);
            role = "player:"+ i;
        }

        char[][] s = c.getState().getIt();
        m.addAttribute("s", s);
        m.addAttribute("sessionId", sessionId);
        m.addAttribute("role", role);

        return "game";
    }

    @PostMapping("/update")
    public String updateBoard(@RequestParam("pos") String pos) {
        String p[] = pos.split(",");
        int x = Integer.parseInt(p[0]);
        int y = Integer.parseInt(p[1]);
        this.c.update( new int[]{x,y} );
        return "redirect:/game";
    }
    
    
}

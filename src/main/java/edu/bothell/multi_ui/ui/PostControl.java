package edu.bothell.multi_ui.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.bothell.multi_ui.core.Control;
import jakarta.servlet.http.HttpSession;


@Controller
public class PostControl {

    
    private final Control      c;
    private final List<String> sessions;

    public PostControl(Control c) {
        this.c = c;
        this.sessions = new ArrayList<>();
    }

    @GetMapping("/game")
    public String getGame( HttpSession session, Model m ) {
        String sessionId = session.getId();
        int rnd = (int)Math.ceil(Math.random() * 6);
        int i;
        
        synchronized(sessions){
            if (!sessions.contains(sessionId) && sessions.size() < c.getMaxPlayers() && c.getTurn() < 2) {
                c.addPlayer( (char)(sessions.size() + '1'), sessionId );
                sessions.add(sessionId);
            }
        }

        synchronized(sessions){
            i = sessions.indexOf(sessionId) + 1;
        }

        char[][] s = c.getState().getIt();
        String[][] t = c.getTerrainStrings();
        m.addAttribute("mode", "socket");
        m.addAttribute("die", rnd);
        m.addAttribute("s", s);
        m.addAttribute("t", t);
        m.addAttribute("turn", c.getTurn());
        m.addAttribute("pc", c.getPlayerCount());
        m.addAttribute("sessionId", sessionId);
        m.addAttribute("role", i);

        return "game";
    }

    @PostMapping("/update")
    public String updateBoard(
        @RequestParam(value="pos", required = false) String pos, 
        @RequestParam(value="die", required = false) Integer die, 
        HttpSession s) {
        System.out.println(die + " UPDATE!!!!!!!!");
        if(c.getPlayerCount() <2 ) return "redirect:/game";
        if(die != null){

        }
        if(pos != null){
            String p[] = pos.split(",");
            int x = Integer.parseInt(p[0]);
            int y = Integer.parseInt(p[1]);
            this.c.update( new int[]{x,y}, s.getId() );
            System.out.println( "---------" + c.checkConnected(x, y) );
        }
        return "redirect:/game";
    }
    
    
}

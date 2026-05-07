package SolitaireFrontend.GUI.controller;

import SolitaireFrontend.GUI.entity.Card;
import SolitaireFrontend.GUI.entity.GameBoard;
import SolitaireFrontend.GUI.service.TcpClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private TcpClientService tcpClientService;

    public GameController(TcpClientService tcpClientService) {
        this.tcpClientService = tcpClientService;
    }

    @GetMapping("/")
    public String loadGame(Model model) {
        // Send a command to get the initial board state
        String rawResponse = tcpClientService.sendCommand("SW");

        // Parse the C servers string into our Java object
        GameBoard board = parseToDto(rawResponse);

        // Give the object to Thymeleaf
        model.addAttribute("gameBoard", board);

        return "index";
    }

    @PostMapping("/command")
    public String executeCommand(@RequestParam("command") String command, Model model) {
        // Send the user's move to the C backend
        String rawResponse = tcpClientService.sendCommand(command);
        System.out.println(rawResponse);

        // Parse the new resulting state
        GameBoard board = parseToDto(rawResponse);

        // Update the model and reload the page
        model.addAttribute("gameBoard", board);

        return "index";
    }

    @PostMapping("/move")
    public String executeMove(
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "destination", required = false) String destination,
            Model model) {

        // Only send the command if the user actually picked a card and a destination
        if (source != null && destination != null) {
            String command = source + "->" + destination;
            String rawResponse = tcpClientService.sendCommand(command);
            GameBoard board = parseToDto(rawResponse);
            model.addAttribute("gameBoard", board);
        } else {
            // If they clicked a destination without selecting a card first, just reload the board
            String rawResponse = tcpClientService.sendCommand("SW");
            GameBoard board = parseToDto(rawResponse);
            model.addAttribute("gameBoard", board);
        }

        return "index";
    }

    // helper method to translate c string to java object
    private GameBoard parseToDto(String rawResponse) {
        GameBoard board = new GameBoard();

        if (rawResponse == null || rawResponse.trim().isEmpty()) {
            board.setStatus("ERROR");
            return board;
        }

        try {
            String[] parts = rawResponse.split("\\|");

            // Status
            if (parts.length >= 1) {
                board.setStatus(parts[0].trim());
            }

            // Phase
            if (parts.length >= 2) {
                board.setPhase(parts[1].trim());
            }

            // Game Data
            if (parts.length >= 3) {
                String gameData = parts[2];
                String[] columns = gameData.split(";");

                for (String colData : columns) {
                    if (colData.trim().isEmpty()) continue;

                    String[] keyValuePair = colData.split("=");
                    String key = keyValuePair[0].trim();

                    List<Card> cardList = new ArrayList<>();

                    // Check if there are actually cards after the "="
                    if (keyValuePair.length > 1 && !keyValuePair[1].trim().isEmpty()) {
                        String[] cards = keyValuePair[1].split(",");

                        for (String cardStr : cards) {
                            cardStr = cardStr.trim();

                            if (cardStr.length() >= 3) {
                                String rank = String.valueOf(cardStr.charAt(0));
                                String suit = String.valueOf(cardStr.charAt(1));
                                boolean isVisible = cardStr.charAt(2) == '1';

                                cardList.add(new Card(rank, suit, isVisible));
                            }
                        }
                    }

                    // Assign the list to the correct field in the GameBoard
                    assignColumnToBoard(board, key, cardList);
                }
            }
        } catch (Exception e) {
            board.setStatus("ERROR");
            System.err.println("Failed to parse string: " + rawResponse);
            e.printStackTrace();
        }

        return board;
    }

    private void assignColumnToBoard(GameBoard board, String key, List<Card> cards) {
        switch (key) {
            case "Deck":
                board.setDeck(cards); // Keep a copy in the deck just in case

                // Deal the 52 cards into the 7 columns left-to-right
                for (int i = 0; i < cards.size(); i++) {
                    Card c = cards.get(i);
                    c.setVisible(true);
                    int colIndex = i % 7;
                    switch (colIndex) {
                        case 0: board.getC1().add(c); break;
                        case 1: board.getC2().add(c); break;
                        case 2: board.getC3().add(c); break;
                        case 3: board.getC4().add(c); break;
                        case 4: board.getC5().add(c); break;
                        case 5: board.getC6().add(c); break;
                        case 6: board.getC7().add(c); break;
                    }
                }
                break;
            case "C1": board.setC1(cards); break;
            case "C2": board.setC2(cards); break;
            case "C3": board.setC3(cards); break;
            case "C4": board.setC4(cards); break;
            case "C5": board.setC5(cards); break;
            case "C6": board.setC6(cards); break;
            case "C7": board.setC7(cards); break;
            case "F1": board.setF1(cards); break;
            case "F2": board.setF2(cards); break;
            case "F3": board.setF3(cards); break;
            case "F4": board.setF4(cards); break;
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class Chatbot {

    private Map<String, String> responses = new HashMap<>();

    Chatbot() {
        responses.put("hi", "Hello! How can I help you?");
        responses.put("hello", "Hi there! What can I do for you?");
        responses.put("how are you", "I'm doing great! Thanks for asking.");
        responses.put("your name", "I am an AI Chatbot developed using Java.");
        responses.put("java", "Java is a popular object-oriented programming language.");
        responses.put("oop", "OOP stands for Object-Oriented Programming.");
        responses.put("bye", "Goodbye! Have a nice day.");
        responses.put("thank", "You're welcome!");
    }

    String getResponse(String input) {
        input = input.toLowerCase().trim();

        for (String key : responses.keySet()) {
            if (input.contains(key)) {
                return responses.get(key);
            }
        }
        return "Sorry, I didn't understand that. Please ask something else.";
    }
}

public class AIChatbotGUI extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Chatbot bot;

    public AIChatbotGUI() {

        bot = new Chatbot();

        setTitle("AI Chatbot");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        String userText = inputField.getText().trim();

        if (userText.isEmpty()) {
            return;
        }

        chatArea.append("You: " + userText + "\n");

        String reply = bot.getResponse(userText);
        chatArea.append("Bot: " + reply + "\n\n");

        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AIChatbotGUI().setVisible(true);
        });
    }
}


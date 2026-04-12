import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineExam extends JFrame {
    private JLabel questionLabel, timerLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionsGroup;
    private JButton nextButton, submitButton;
    private int currentQuestion = 0;
    private int examDuration = 120; // seconds
    private Timer timer;

    private List<Question> questions = new ArrayList<>();
    private Map<Integer, String> userAnswers = new HashMap<>();

    private int userId, examId;

    public OnlineExam(int userId, int examId) {
        this.userId = userId;
        this.examId = examId;

        loadQuestions();

        setTitle("Online Exam");
        setLayout(new BorderLayout());

        // Top Panel (Timer)
        timerLabel = new JLabel("Time left: " + examDuration + "s");
        add(timerLabel, BorderLayout.NORTH);

        // Center Panel (Question + Options)
        JPanel centerPanel = new JPanel(new BorderLayout());

        questionLabel = new JLabel();
        centerPanel.add(questionLabel, BorderLayout.NORTH);

        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);

        centerPanel.add(optionsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel (Buttons)
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");

        nextButton.addActionListener(e -> saveAnswerAndNext());
        submitButton.addActionListener(e -> autoSubmitExam());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(nextButton);
        bottomPanel.add(submitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load first question
        showQuestion(currentQuestion);

        // Start timer
        startTimer();

        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Questions
    private void loadQuestions() {
        questions.add(new Question(1,
                "Which of the following is a cloud service model?",
                "IaaS", "PaaS", "SaaS", "All of the above"));

        questions.add(new Question(2,
                "Which company provides AWS cloud services?",
                "Google", "Microsoft", "Amazon", "IBM"));

        questions.add(new Question(3,
                "Which of the following is NOT a cloud deployment model?",
                "Public Cloud", "Private Cloud", "Hybrid Cloud", "Desktop Cloud"));

        questions.add(new Question(4,
                "What is the main advantage of cloud computing?",
                "High upfront cost", "Scalability", "Limited access", "Manual updates"));
    }

    private void showQuestion(int index) {
        if (index < questions.size()) {
            Question q = questions.get(index);

            questionLabel.setText("Q" + (index + 1) + ": " + q.text);

            optionA.setText(q.optionA);
            optionB.setText(q.optionB);
            optionC.setText(q.optionC);
            optionD.setText(q.optionD);

            optionsGroup.clearSelection();
        } else {
            autoSubmitExam();
        }
    }

    private void saveAnswerAndNext() {
        String selected = null;

        if (optionA.isSelected()) selected = optionA.getText();
        else if (optionB.isSelected()) selected = optionB.getText();
        else if (optionC.isSelected()) selected = optionC.getText();
        else if (optionD.isSelected()) selected = optionD.getText();

        if (selected != null) {
            userAnswers.put(questions.get(currentQuestion).id, selected);
        }

        currentQuestion++;
        showQuestion(currentQuestion);
    }

    private void startTimer() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                examDuration--;

                SwingUtilities.invokeLater(() ->
                        timerLabel.setText("Time left: " + examDuration + "s")
                );

                if (examDuration <= 0) {
                    timer.cancel();
                    autoSubmitExam();
                }
            }
        }, 1000, 1000);
    }

    private void autoSubmitExam() {
        timer.cancel();
        JOptionPane.showMessageDialog(this,
                "Exam submitted!\nYour answers: " + userAnswers);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OnlineExam(1, 101));
    }
}

// Question Class
class Question {
    int id;
    String text, optionA, optionB, optionC, optionD;

    Question(int id, String text, String a, String b, String c, String d) {
        this.id = id;
        this.text = text;
        this.optionA = a;
        this.optionB = b;
        this.optionC = c;
        this.optionD = d;
    }
}
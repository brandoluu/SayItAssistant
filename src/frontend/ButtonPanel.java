package frontend;

import javax.swing.*;
import java.awt.*;

import middleware.HistoryManager;
import middleware.SayItAssistant;

import java.awt.*;

public class ButtonPanel extends AppPanels{
    SayItAssistant assistant;

    public ButtonPanel(SayItAssistant sayItAssistant, QnAPanel qna, HistoryPanel history, HistoryManager historyManager) {
        this.assistant = sayItAssistant;
        this.setPreferredSize(new Dimension(400, 20));
        this.setLayout(new GridLayout(2,1));
        this.setBackground(GREY);
        NewQuestionPanel newQuestionPanel = new NewQuestionPanel(assistant, qna, history);
        SecondaryButtonsPanel SecondaryButtonsPanel = new SecondaryButtonsPanel(historyManager, qna, history);
        addSecondaryButtonsPanel(SecondaryButtonsPanel);
        addNewQuestionPanel(newQuestionPanel);
        revalidate();
    }

    public void addNewQuestionPanel(NewQuestionPanel newQuestionPanel) {
        add(newQuestionPanel, BorderLayout.SOUTH);
    }

    public void addSecondaryButtonsPanel(SecondaryButtonsPanel secondaryButtonsPanel) {
        add(secondaryButtonsPanel, BorderLayout.NORTH);
    }
}
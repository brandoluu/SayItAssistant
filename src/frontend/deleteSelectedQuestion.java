package frontend;

import middleware.Answer;
import middleware.HistoryManager;
import middleware.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class deleteSelectedQuestion extends JPanel{
    public deleteSelectedQuestion(HistoryManager historyManager, QnAPanel qna, HistoryPanel history){
        this.setLayout(new GridLayout(1,1));
        
        deleteQuestionButton deleteButton = new deleteQuestionButton(historyManager, qna, history);

        this.addDeleteQuestionButton(deleteButton);

    }

    /*
     * Adds the delete button to the delete question panel.
     * 
     * @param deleteQuestionButton: the button to be added
     */
    public void addDeleteQuestionButton(deleteQuestionButton deleteButton) {
        this.add(deleteButton);
    }

}

class deleteQuestionButton extends AppButtons{
    // formatting for the delete all button
     private final int deleteQuestionButtonWidth = 600;
     private final int deleteQuestionButtonHeight = 50;
     private final static String deleteQuestionLabel = "Delete Question";

    deleteQuestionButton(HistoryManager historyManager, QnAPanel qna, HistoryPanel history){
        super(deleteQuestionLabel);
        this.setBackground(TEAL);
        this.setForeground(WHITE);
        setHorizontalAlignment(SwingConstants.CENTER);
        setPreferredSize(new Dimension(deleteQuestionButtonWidth, deleteQuestionButtonHeight));
        
        //mouse listener that takes the current int from the display and deletes it, setting back the display to the default. 
        addActionListener(e -> {
            if (qna.getStatus() == true){
                historyManager.delete(qna.questionPanel.getCurrentQuestionNumber());
                history.revalidateHistory(qna);
                qna.setQuestion(new Question("Welcome to SayIt Assistant"));
                qna.setAnswer(new Answer(""));
                revalidate();
                qna.isSet = false;
            }
        });
    }
}
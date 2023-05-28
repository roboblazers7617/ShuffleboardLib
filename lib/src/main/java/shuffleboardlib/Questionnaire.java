package shuffleboardlib;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Questionnaire extends SubsystemBase {
    private final String shuffleboardPath;
    private final Question rootQuestion;
    private final NetworkTable networkTable;
    // questions are switchable choosers which include the questions and the answers
    private final ArrayList<SendableChooser<Question>> answerSendableChoosers = new ArrayList<SendableChooser<Question>>();
    // just the question publisher, i don't know why I have this
    private final ArrayList<StringPublisher> questionPublishers = new ArrayList<StringPublisher>();
    private final int numQuestions;

    // a desision tree
    /**
     * create a shuffleboard questionnaire
     * 
     * @param shuffleboardPath the path to the network table, should start with
     *                         "Shuffleboard/"
     * @param rootQuestion     the root question, should be the first question asked
     * @param numQuestions     the number of questions in the deepest path of the
     *                         tree
     */
    public Questionnaire(String shuffleboardPath, Question rootQuestion, int numQuestions) {
        this.shuffleboardPath = shuffleboardPath;
        this.rootQuestion = rootQuestion;
        this.numQuestions = numQuestions;
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        networkTable = inst.getTable(shuffleboardPath);
        ShuffleboardTab tab = Shuffleboard.getTab(shuffleboardPath);

        // create the sendable choosers
        for (int i = 0; i < numQuestions; i++) {
            answerSendableChoosers.add(new SendableChooser<Question>());
            tab.add("Question " + i, answerSendableChoosers.get(i)).withPosition(0, i);
        }

        // create the question publishers
        for (int i = 0; i < numQuestions; i++) {
            questionPublishers.add(networkTable.getStringTopic("Question " + i).publish());
        }

        // add the root question to the sendable choosers
        answerSendableChoosers.get(0).setDefaultOption(rootQuestion.getQuestion(), rootQuestion);

        // configure the first question
        configureQuestion(0, rootQuestion);
        // configure all the questions in a loop
        for (int i = 0; i < numQuestions; i++) {
            configureQuestion(i, answerSendableChoosers.get(i).getSelected());
        }

    }

    private void configureQuestion(int questionNumber, Question question) {
        try {
            // add the question to the network table
            questionPublishers.get(questionNumber).set(question.getQuestion());

            // add the question to the sendable choosers
            answerSendableChoosers.get(questionNumber).setDefaultOption("NA", null);

            // add the answers to the sendable choosers
            // try it because it might be the last question so there won't be more answers

            for (String answer : question.getAnswersHashMap().keySet()) {
                answerSendableChoosers.get(questionNumber).addOption(answer, question.getAnswersHashMap().get(answer));
            }
        } catch (NullPointerException e) {
            // do nothing
        }

    }

    @Override
    public void periodic() {
        // configure the first question
        configureQuestion(0, rootQuestion);
        // configure all the questions in a loop
        for (int i = 0; i < numQuestions; i++) {
            configureQuestion(i, answerSendableChoosers.get(i).getSelected());
        }
    }

}

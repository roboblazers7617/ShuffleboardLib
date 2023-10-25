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
    /** does not include "Shuffleboard/" */
    private final Question rootQuestion;
    private final NetworkTable networkTable;
    // questions are switchable choosers which include the questions and the answers
    private final ArrayList<SendableChooser<Question>> answerSendableChoosers = new ArrayList<SendableChooser<Question>>();
    // just the question publisher, i don't know why I have this
    private final ArrayList<StringPublisher> questionPublishers = new ArrayList<StringPublisher>();
    // private final ArrayList<String> questionStringThings = new
    // ArrayList<String>();
    private final int numQuestions;

    // a desision tree
    /**
     * create a shuffleboard questionnaire
     * 
     * @param shuffleboardPath the path to the network table, should NOT start with
     *                         "Shuffleboard/"
     * @param rootQuestion     the root question, should be the first question asked
     * @param numQuestions     the number of questions in the deepest path of the
     *                         tree
     */
    public Questionnaire(String shuffleboardPath, Question rootQuestion, int numQuestions) {
        this.rootQuestion = rootQuestion;
        this.numQuestions = numQuestions;
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        networkTable = inst.getTable("Shuffleboard/" + shuffleboardPath);
        ShuffleboardTab tab = Shuffleboard.getTab(shuffleboardPath);

        // create the sendable choosers
        for (int i = 0; i < numQuestions; i++) {
            answerSendableChoosers.add(new SendableChooser<Question>());
            tab.add("Answer " + i, answerSendableChoosers.get(i)).withPosition(7, i);
        }

        // create the question publishers
        for (int i = 0; i < numQuestions; i++) {
            questionPublishers.add(networkTable.getStringTopic("Question " + i).publish());
            // this will be the actual question, it will be setup during configureQuestion
            // questionStringThings.add("");
            // tab.addString(shuffleboardPath, null)
            // tab.addString("Question " + i, () ->
            // questionStringThings.get(test)).withPosition(6, i);
            tab.add("Question " + i, "No Question").withPosition(6, i);

        }


        // configure the first question
        configureQuestion(0, rootQuestion);
        // configure all the questions in a loop
        for (int i = 0; i < numQuestions; i++) {
            configureQuestion(i, answerSendableChoosers.get(i).getSelected());
        }

    }

    private void configureQuestion(int questionNumber, Question question) {
        //question and answer may not exist
        try {
            // add the question to the network table
            questionPublishers.get(questionNumber).set(question.getQuestion());

            // add the question to the sendable choosers
            answerSendableChoosers.get(questionNumber).setDefaultOption("NA", null);

            // add the answers to the sendable choosers
            for (String answer : question.getAnswersHashMap().keySet()) {
                answerSendableChoosers.get(questionNumber).addOption(answer, question.getAnswersHashMap().get(answer));
            }
        } catch (NullPointerException e) {
            // question does not exist
            // do nothing
        }

    }

    /**
     * 
     * @return the output of the selected option, if none is selected it returns none
     */
    public String getOutput() {
        for (SendableChooser<Question> question : answerSendableChoosers) {
            if (question.getSelected() != null && question.getSelected().getOutput() != null){
                return question.getSelected().getOutput();
            }
        }
        return null;

    }

    @Override
    public void periodic() {
        // configure the first question
        configureQuestion(0, rootQuestion);
        // configure all the questions in a loop
        for (int i = 1; i < numQuestions; i++) {
            configureQuestion(i, answerSendableChoosers.get(i - 1).getSelected());
        }

    }

}

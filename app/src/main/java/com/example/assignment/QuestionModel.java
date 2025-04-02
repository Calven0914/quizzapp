package com.example.assignment;

public class QuestionModel {

    private String question,optionA,optionB,optionC,Tips;
    private int[] scores;

    public QuestionModel(String question, String optionA, String optionB, String optionC, String tips, int[] scores) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        Tips = tips;
        this.scores = scores;
    }

    public QuestionModel() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getTips() {
        return Tips;
    }

    public void setTips(String tips) {
        Tips = tips;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }
}

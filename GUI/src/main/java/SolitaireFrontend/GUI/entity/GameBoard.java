package SolitaireFrontend.GUI.entity;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    // The 7 playing columns
    private List<Card> c1 = new ArrayList<>();
    private List<Card> c2 = new ArrayList<>();
    private List<Card> c3 = new ArrayList<>();
    private List<Card> c4 = new ArrayList<>();
    private List<Card> c5 = new ArrayList<>();
    private List<Card> c6 = new ArrayList<>();
    private List<Card> c7 = new ArrayList<>();

    // The 4 foundations
    private List<Card> f1 = new ArrayList<>();
    private List<Card> f2 = new ArrayList<>();
    private List<Card> f3 = new ArrayList<>();
    private List<Card> f4 = new ArrayList<>();

    // A field to hold the game status (e.g., "OK" or "ERROR")
    private String status;

    public List<Card> getC1() {
        return c1;
    }

    public void setC1(List<Card> c1) {
        this.c1 = c1;
    }

    public List<Card> getC2() {
        return c2;
    }

    public void setC2(List<Card> c2) {
        this.c2 = c2;
    }

    public List<Card> getC3() {
        return c3;
    }

    public void setC3(List<Card> c3) {
        this.c3 = c3;
    }

    public List<Card> getC4() {
        return c4;
    }

    public void setC4(List<Card> c4) {
        this.c4 = c4;
    }

    public List<Card> getC5() {
        return c5;
    }

    public void setC5(List<Card> c5) {
        this.c5 = c5;
    }

    public List<Card> getC6() {
        return c6;
    }

    public void setC6(List<Card> c6) {
        this.c6 = c6;
    }

    public List<Card> getC7() {
        return c7;
    }

    public void setC7(List<Card> c7) {
        this.c7 = c7;
    }

    public List<Card> getF1() {
        return f1;
    }

    public void setF1(List<Card> f1) {
        this.f1 = f1;
    }

    public List<Card> getF2() {
        return f2;
    }

    public void setF2(List<Card> f2) {
        this.f2 = f2;
    }

    public List<Card> getF3() {
        return f3;
    }

    public void setF3(List<Card> f3) {
        this.f3 = f3;
    }

    public List<Card> getF4() {
        return f4;
    }

    public void setF4(List<Card> f4) {
        this.f4 = f4;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package controller;

import listener.GameListener;
import model.Chessboard;
import model.ChessboardPoint;
import model.Constant;
import view.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;
    private int score = 0;
    private int step = 0;
    private JLabel statusLabel;
    private int clickCount = 0;
    private ChessGameFrame frame;

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public GameController(ChessboardComponent view, Chessboard model, ChessGameFrame frame) {
        this.view = view;
        this.model = model;
        this.frame = frame;
        setStatusLabel(frame.getStatusLabel());
        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
        addleveldialog();//add for select level
        setstatusLabelText();
    }


    public void initialize() {
        Music.Click();
//        addleveldialog();
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
        frame.getTool1Button().setEnabled(true);
        frame.setRefreshClickCount(0);
        setstatusLabelText();
    }
    public void change() {
        Music.Click();
        addleveldialog();
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
        frame.getTool1Button().setEnabled(true);
        frame.setRefreshClickCount(0);
        setstatusLabelText();
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        Music.Click();
    }

    @Override
    public void onPlayerSwapChess() {
        Music.Click();
        System.out.println("Implement your swap here.");
        if (selectedPoint != null && selectedPoint2 != null) {
            if (model.canSwap(selectedPoint, selectedPoint2)) {
                saveGameToFile("1.txt");//todo：新加的，悔棋一步
                model.swapChessPiece(selectedPoint, selectedPoint2);
                model.setStepLeft(model.getStepLeft() - 1);
                if (model.isMatch5(selectedPoint) || model.isMatch5(selectedPoint2)) {
                    JOptionPane.showMessageDialog(null, "Amazing Match!");
                } else if (model.isMatch4(selectedPoint) || model.isMatch4(selectedPoint2)) {
                    JOptionPane.showMessageDialog(null, "Great Match!");
                }
                model.removePiecesOnBoard();
                view.removeAllChessComponentsAtGrids();
                view.initiateChessComponent(model);
                view.repaint();
                selectedPoint = null;
                selectedPoint2 = null;
                frame.getConfirmSwapButton().setEnabled(false);
                //Confirm Swap button should not be pressed simultaneously
                frame.getNextStepButton().setEnabled(true);
                frame.setNextStepState(0);
            } else {
                var grid1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
                grid1.setSelected(false);
                grid1.repaint();
                var grid2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
                grid2.setSelected(false);
                grid2.repaint();
                selectedPoint = null;
                selectedPoint2 = null;
                JOptionPane.showMessageDialog(null, "No match,swap back!");
            }
        }
        //setstatusLabelText();
    }


    public void onPlayerNextStep() {
        Music.Click();
        frame.setNextStepClickCount(frame.getNextStepClickCount() + 1);
        boolean isOver = false;
        switch (frame.getNextStepState()) {
            case 0:
                model.setScore(model.getScore() + model.down());//score+=model.down();
                frame.setNextStepState(1);
                break;
            case 1:
                model.generateNewPieces();
                if (model.canRemove())
                    frame.setNextStepState(2);
                else {
                    frame.setNextStepState(0);
                    frame.getNextStepButton().setEnabled(false);
                    frame.getConfirmSwapButton().setEnabled(true);
                    view.removeAllChessComponentsAtGrids();
                    view.initiateChessComponent(model);
                    setstatusLabelText();
                    view.repaint();//todo：这里四行是新加的，用于弹窗
                    if (frame.getNextStepClickCount() >= 8)
                        JOptionPane.showMessageDialog(null, "Excellent!");
                    frame.setNextStepClickCount(0);
                    saveGameToFile("1.txt");//todo：这里要删掉吗？
                    isOver = true;
                }
                break;
            case 2:
                model.removePiecesOnBoard();
                frame.setNextStepState(0);
                break;
        }
        if (isOver) {
            if (model.getScore() >= Constant.getscoreNeedFromLevel(model.getLevelSelect())) {
                JOptionPane.showConfirmDialog(null, "YOU WIN! Try next Level!");
                model.setLevelSelect(model.getLevelSelect() + 1);
                initialize();
            } else if (model.getStepLeft() == 0) {
                JOptionPane.showConfirmDialog(null, "YOU LOSE! Try again!");
                initialize();
            }
            frame.setNextStepClickCount(0);
        }

        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        setstatusLabelText();
        view.repaint();
        System.out.println("Implement your next step here.");
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        Music.Click();
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
        }
        component.setSelected(true);
        component.repaint();

    }

    public void saveGameToFile(String path) {
        List<String> savelines = model.convertBoardToList();
        for (String line : savelines) {
            System.out.println(line);
        }
        savelines.add(String.valueOf(frame.getConfirmSwapButton().isEnabled()));
        savelines.add(String.valueOf(frame.getNextStepButton().isEnabled()));
        savelines.add(String.valueOf(frame.getNextStepState()));//todo：这里三行还要吗
        if (!path.endsWith(".txt")) {
            JOptionPane.showMessageDialog(null, "请用正确的文件格式保存");
            return;
        }
        try {
            Files.write(Path.of(path), savelines);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Save error!");
            throw new RuntimeException(e);
        }
    }

    public void loadGameFromFile(String path) {
        try {
            // 检查文件格式
            if (!path.endsWith(".txt")) {
                showErrorDialog("文件格式错误101", "不支持的文件格式", 101);
                return;
            }

            File file = new File(path);
            Scanner sc = new Scanner(file);

            // 检查棋盘大小
            int[][] piece = new int[8][8];
            for (int col = 0; col < 8; col++) {
                for (int row = 0; row < 8; row++) {
                    if (!sc.hasNextInt()) {
                        showErrorDialog("棋盘错误102", "棋盘大小不符合要求", 102);
                        return;
                    }
                    piece[col][row] = sc.nextInt();
                }
            }

            // 检查图片错误
            if (sc.hasNextInt()) {
                int imageCode = sc.nextInt();
                if (imageCode < 1 || imageCode > 4) {
                    showErrorDialog("图片错误103", "存在非指定的图片", 103);
                    return;
                }
            }

            model.setScore(sc.nextInt());
            model.setStepLeft(sc.nextInt());
            model.generateNewPieces(piece);

            frame.getConfirmSwapButton().setEnabled(sc.nextBoolean());
            frame.getNextStepButton().setEnabled(sc.nextBoolean());
            frame.setNextStepState(sc.nextInt());

            view.removeAllChessComponentsAtGrids();
            view.initiateChessComponent(model);
            view.repaint();
            setstatusLabelText();
        } catch (IOException e) {
            showErrorDialog("加载错误", "无法读取文件", -1);
            throw new RuntimeException(e);
        }
    }//todo：这里的三种错误还有待观察
    private void showErrorDialog(String title, String message, int errorCode) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
        // 可以根据需要将错误编码传递给其他方法或记录日志等操作
    }
    private void setstatusLabelText() {
        this.statusLabel.setText(model.getLabelText());
    }
    public void addleveldialog()
    {
        LevelDialog dlg = new LevelDialog();
        dlg.setModal(true);
        dlg.show();
        int levelSelect = dlg.getLevelSelect();
        model.setLevelSelect(levelSelect);
        model.setScore(0);
        model.setRequiredScore(Constant.getscoreNeedFromLevel(levelSelect));
        model.setStepLeft(Constant.getStepMaxFromLevel(levelSelect));
        System.out.println(levelSelect);
    }
    public void setScore(int score) {
        this.score = score;
    }

    public void tool1() {
        Music.Click();
        frame.setRefreshClickCount(frame.getRefreshClickCount() + 1);
        model.removeAllChessPiece();
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
        if (frame.getRefreshClickCount() >= 3) {
            frame.getTool1Button().setEnabled(false);
            JOptionPane.showMessageDialog(null, "You have used it three times already!");
        }
    }

    public void hintForRefresh() {
        Music.Click();
        if (model.needRefresh()) {
            JOptionPane.showMessageDialog(null, "You need to refresh the board!");
        } else {
            JOptionPane.showMessageDialog(null, "You don't need to refresh the board!");
        }
    }

    public void hint() {
        JOptionPane.showMessageDialog(null, model.getHintMessage());
    }

    public void Withdraw() {
        Music.Click();
        loadGameFromFile("1.txt");
    }
    public void tool2() {
        if (selectedPoint != null && selectedPoint2 == null) {
            model.knockOut(selectedPoint);
            if (model.canRemove()) {
                frame.getNextStepButton().setEnabled(true);
                frame.getConfirmSwapButton().setEnabled(false);
                frame.setNextStepState(2);
            }
            selectedPoint = null;

        }
        else if (selectedPoint == null && selectedPoint2 != null){
            model.knockOut(selectedPoint2);
            if (model.canRemove()) {
                frame.getNextStepButton().setEnabled(true);
                frame.getConfirmSwapButton().setEnabled(false);
                frame.setNextStepState(2);
            }
            selectedPoint2 = null;
        }
        else return;
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
    }
}


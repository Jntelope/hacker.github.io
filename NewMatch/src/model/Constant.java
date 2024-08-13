package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum Constant {
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8);

    private final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    static Map<String, Color> colorMap = new HashMap<>(){{
        put("💎",Color.blue);
        put("⚪",Color.white);
        put("▲",Color.green);
        put("🔶",Color.orange);
        put("★",Color.black);
    }};

    public static Map<String,Integer> PieceTypeMap = new HashMap<>(){{
        put("💎",1);
        put("⚪",2);
        put("▲",3);
        put("🔶",4);
        put("★",5);
    }};
    //add for level
    private final static int stepMax[] = {10,12,13,14,15};
    private final static int scoreNeed[] ={500,800,1100,1400,2000};
    public final static String[] symbols = new String[]{"💎", "⚪", "▲", "🔶","★"};
    public static int getStepMaxFromLevel(int level)
    {
        return stepMax[level-1];
    }
    public static int getscoreNeedFromLevel(int level)
    {
        return scoreNeed[level-1];
    }
}

package boats.boat.speed;

public class Analysis extends MainActivity {
    private float[][] dotIndexes;

    Analysis(float[][] setDotIndexes) {
        dotIndexes = setDotIndexes;
    }

    public boolean shinsVertical() {
        if (dotIndexes[1][0] - dotIndexes[0][0] <= 40 && dotIndexes[1][0] - dotIndexes[0][0] >= 0) {
            return true;
        }
        return false;
    }

    public int bodyAngle() {
        if (dotIndexes == null) {
            return 99;
        }
        if (dotIndexes[3][0] - dotIndexes[2][0] <= 15 && dotIndexes[3][0] - dotIndexes[2][0] >= 15) {
            return 0;
        } else if (dotIndexes[3][0] - dotIndexes[2][0] >= 15) {
            return 1;
        }
        return -1;
    }
}

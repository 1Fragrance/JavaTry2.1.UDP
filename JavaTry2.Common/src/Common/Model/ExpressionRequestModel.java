package Common.Model;

import java.io.Serializable;

public class ExpressionRequestModel implements Serializable {
    private double x;
    private double y;
    private double z;
    private final OperationType operation;

    public ExpressionRequestModel(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        operation = OperationType.CALCULATE;
    }

    public ExpressionRequestModel() {
        operation = OperationType.EXIT;
    }

    public OperationType getOperation() {
        return operation;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}

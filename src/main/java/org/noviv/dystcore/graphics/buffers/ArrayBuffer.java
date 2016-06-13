package org.noviv.dystcore.graphics.buffers;

import org.joml.Vector3f;
import org.joml.Vector3d;
import org.noviv.dystcore.graphics.matrices.Matrix;

import static java.lang.Math.*;

public class ArrayBuffer {

    private double[][] matrix;
    private int width;
    private int height;

    public ArrayBuffer(double[] array, int stride) {
        width = stride;
        height = array.length / stride;
        matrix = new double[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = array[y * width + x];
            }
        }
    }

    public double[] getLinearMatrix() {
        double[] ret = new double[width * height];
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ret[i] = matrix[y][x];
                i++;
            }
        }
        return ret;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void rotate(Vector3f rot) {
        Vector3d rads = new Vector3d(toRadians(rot.x), toRadians(rot.y), toRadians(rot.z));
        double[] xRot = {1, 0, 0,
            0, cos(rads.x), sin(rads.x),
            0, -sin(rads.x), cos(rads.x)};
        double[] yRot = {cos(rads.y), 0, -sin(rads.y),
            0, 1, 0,
            sin(rads.y), 0, cos(rads.y)};
        double[] zRot = {cos(rads.z), sin(rads.z), 0,
            -sin(rads.z), cos(rads.z), 0,
            0, 0, 1};

        ArrayBuffer xRotMatrix = new ArrayBuffer(xRot, 3);
        ArrayBuffer yRotMatrix = new ArrayBuffer(yRot, 3);
        ArrayBuffer zRotMatrix = new ArrayBuffer(zRot, 3);

        matrix = Matrix.multiply(matrix, xRotMatrix.getMatrix());
        matrix = Matrix.multiply(matrix, yRotMatrix.getMatrix());
        matrix = Matrix.multiply(matrix, zRotMatrix.getMatrix());
    }
}

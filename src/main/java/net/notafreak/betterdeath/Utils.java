package net.notafreak.betterdeath;

public class Utils {
    public static int ConstructColorHex(int R, int G, int B, int A) {
        R = Math.max(0, Math.min(255, R));
        G = Math.max(0, Math.min(255, G));
        B = Math.max(0, Math.min(255, B));
        A = Math.max(0, Math.min(255, A));
        return (A << 24) | (R << 16) | (G << 8) | B;
    }
}

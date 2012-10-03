package com.tor.algoritms;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 03.10.12
 * Time: 13:02
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * + @see http://jaist.dl.sourceforge.net/project/crypto2011/Keccak.java
 */
public class Keccak_SHA3 {

    static final long[] KeccakRoundConstants = {
           0x0000000000000001L, 0x0000000000008082L, 0x800000000000808AL, 0x8000000080008000L,
           0x000000000000808BL, 0x0000000080000001L, 0x8000000080008081L, 0x8000000000008009L,
           0x000000000000008AL, 0x0000000000000088L, 0x0000000080008009L, 0x000000008000000AL,
           0x000000008000808BL, 0x800000000000008BL, 0x8000000000008089L, 0x8000000000008003L,
           0x8000000000008002L, 0x8000000000000080L, 0x000000000000800AL, 0x800000008000000AL,
           0x8000000080008081L, 0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L,
       };
       static final int[] KeccakRhoOffsets = {0, 1, 62, 28, 27, 36, 44, 6, 55, 20, 3, 10, 43, 25, 39, 41, 45, 15, 21, 8, 18, 2, 61, 56, 14};

       static final int nrRounds = 24;
       static final int KeccakPermutationSize = 1600;


    public static void main(String[] args) {
        Keccak_SHA3 k = new Keccak_SHA3();

        String message = "53587B3900800100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

        byte[] byteArray = Util.convertStringToByteArray(message);

        byteArray = k.keccakf(byteArray);

        System.out.println(Util.byteToHex(byteArray));

        byteArray = k.keccakf(byteArray);

        System.out.println(Util.byteToHex(byteArray));

    }


    public byte[] getHash(byte[] val) {
        // TODO Auto-generated method stub
        return null;
    }

    public void init(int hashBits) {
        // TODO Auto-generated method stub

    }

    public void update(byte[] aData, int aLength) {
        // TODO Auto-generated method stub

    }

    public byte[] duplexing(byte[] sigma, int sigmaLength, byte[] z, int zLength) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getBitRate() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getCapacity() {
        // TODO Auto-generated method stub
        return 0;
    }


    byte[] keccakf(byte[] input) {
        byte[][][] matrix = blockToMatrix(input);

        for (int i = 0; i < nrRounds; i++) {
            matrix = theta(matrix);
            matrix = rho(matrix);
            matrix = pi(matrix);
            matrix = chi(matrix);
            matrix = iota(matrix, i);
        }

        return matrixToBlock(matrix);
    }


    byte[][][] theta(byte[][][] a) {
        byte[][][] A = new byte[5][5][8];

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++) {
                A[x][y] = a[x][y].clone();
                int x_aux = x == 0 ? 4 : x - 1;
                for (int y_aux = 0; y_aux < 5; y_aux++)
                    A[x][y] = Util.xor(A[x][y], a[x_aux][y_aux]);

                x_aux = x == 4 ? 0 : x + 1;
                for (int y_aux = 0; y_aux < 5; y_aux++)
                    A[x][y] = Util.xor(A[x][y], rotateLeft(a[x_aux][y_aux], 1, 64));
            }

        return A;
    }

    byte[][][] rho(byte[][][] a) {
        byte[][][] A = new byte[5][5][8];

        A[0][0] = a[0][0];

        int x = 1;
        int y = 0;

        for (int t = 0; t < nrRounds; t++) {
            int bits = ((t + 1) * (t + 2) / 2) % 64;
            A[x][y] = rotateLeft(a[x][y], bits, 64);
            int aux = y;
            y = (2 * x + 3 * y) % 5;
            x = aux;
        }
        return A;
    }

    byte[][][] pi(byte[][][] a) {
        byte[][][] A = new byte[5][5][8];

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++) {
                int xlinha = y;
                int ylinha = (2 * x + 3 * y) % 5;
                A[xlinha][ylinha] = a[x][y];
            }

        return A;
    }

    byte[][][] chi(byte[][][] a) {
        byte[][][] A = new byte[5][5][8];

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++) {
                int x_aux1 = (x + 1) % 5;
                int x_aux2 = (x + 2) % 5;
                A[x][y] = Util.xor(a[x][y], Util.and(a[x_aux2][y], Util.not(a[x_aux1][y])));
            }

        return A;
    }

    byte[][][] iota(byte[][][] a, int roundNumber) {
        a[0][0] = Util.xor(a[0][0], RoundConstants.getRoundConstant(roundNumber));
        return a;
    }

    byte[] rotateLeft(byte[] in, int nbits, int ntotalbits) {
        BigInteger bg = new BigInteger(in);

        for (int i = 0; i < nbits; i++) {
            bg = bg.shiftLeft(1);
            if (bg.testBit(ntotalbits)) {
                bg = bg.clearBit(ntotalbits);
                bg = bg.setBit(0);
            } else
                bg = bg.clearBit(0);
        }

        byte[] aux = bg.toByteArray();
        byte[] out = new byte[ntotalbits / 8];


        for (int i = 1; i <= ntotalbits / 8; i++) {
            int pos = aux.length - i;

            if (pos >= 0)
                out[ntotalbits / 8 - i] = aux[pos];
        }

        return out;
    }

    byte[][][] blockToMatrix(byte[] in) {
        byte[][][] out = new byte[5][5][8];

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                for (int z = 0; z < 8; z++)
                    out[x][y][z] = in[(7 - z) + 8 * (x + 5 * y)];

        return out;
    }

    byte[] matrixToBlock(byte[][][] matrix) {
        byte[] out = new byte[KeccakPermutationSize / 8];

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                for (int z = 0; z < 8; z++)
                    out[z + 8 * (x + 5 * y)] = matrix[x][y][7 - z];

        return out;
    }

    static class RoundConstants {
        public static byte[] getRoundConstant(int roundNumber) {
            switch (roundNumber) {
                case 0:
                    return Util.convertStringToByteArray("0000000000000001");
                case 1:
                    return Util.convertStringToByteArray("0000000000008082");
                case 2:
                    return Util.convertStringToByteArray("800000000000808A");
                case 3:
                    return Util.convertStringToByteArray("8000000080008000");
                case 4:
                    return Util.convertStringToByteArray("000000000000808B");
                case 5:
                    return Util.convertStringToByteArray("0000000080000001");
                case 6:
                    return Util.convertStringToByteArray("8000000080008081");
                case 7:
                    return Util.convertStringToByteArray("8000000000008009");
                case 8:
                    return Util.convertStringToByteArray("000000000000008A");
                case 9:
                    return Util.convertStringToByteArray("0000000000000088");
                case 10:
                    return Util.convertStringToByteArray("0000000080008009");
                case 11:
                    return Util.convertStringToByteArray("000000008000000A");
                case 12:
                    return Util.convertStringToByteArray("000000008000808B");
                case 13:
                    return Util.convertStringToByteArray("800000000000008B");
                case 14:
                    return Util.convertStringToByteArray("8000000000008089");
                case 15:
                    return Util.convertStringToByteArray("8000000000008003");
                case 16:
                    return Util.convertStringToByteArray("8000000000008002");
                case 17:
                    return Util.convertStringToByteArray("8000000000000080");
                case 18:
                    return Util.convertStringToByteArray("000000000000800A");
                case 19:
                    return Util.convertStringToByteArray("800000008000000A");
                case 20:
                    return Util.convertStringToByteArray("8000000080008081");
                case 21:
                    return Util.convertStringToByteArray("8000000000008080");
                case 22:
                    return Util.convertStringToByteArray("0000000080000001");
                case 23:
                    return Util.convertStringToByteArray("8000000080008008");
                default:
                    return new byte[0];
            }
        }
    }

    static class Util {
        /**
         * Convert a plain text String to an array of bytes
         */
        public static byte[] convertStringToByteArray(String plainText) {
            int size = plainText.length();
            byte[] vectorBlock = new byte[size / 2];
            for (int i = 0; i < size; i += 2) {
                String sByte1 = plainText.substring(i, i + 1);
                String sByte2 = plainText.substring(i + 1, i + 2);

                int byte1 = (stringToByte(sByte1) << (byte) 0x04);
                int byte2 = stringToByte(sByte2);
                vectorBlock[i / 2] = (byte) (byte1 ^ byte2);
            }
            return vectorBlock;
        }

        /**
         * Convert a single character to a byte
         */
        public static byte stringToByte(String sByte) {
            if (sByte.equals("0"))
                return (byte) 0x00;
            else if (sByte.equals("1"))
                return (byte) 0x01;
            else if (sByte.equals("2"))
                return (byte) 0x02;
            else if (sByte.equals("3"))
                return (byte) 0x03;
            else if (sByte.equals("4"))
                return (byte) 0x04;
            else if (sByte.equals("5"))
                return (byte) 0x05;
            else if (sByte.equals("6"))
                return (byte) 0x06;
            else if (sByte.equals("7"))
                return (byte) 0x07;
            else if (sByte.equals("8"))
                return (byte) 0x08;
            else if (sByte.equals("9"))
                return (byte) 0x09;
            else if (sByte.toLowerCase().equals("a"))
                return (byte) 0x0A;
            else if (sByte.toLowerCase().equals("b"))
                return (byte) 0x0B;
            else if (sByte.toLowerCase().equals("c"))
                return (byte) 0x0C;
            else if (sByte.toLowerCase().equals("d"))
                return (byte) 0x0D;
            else if (sByte.toLowerCase().equals("e"))
                return (byte) 0x0E;
            else
                return (byte) 0x0F;
        }

        /**
         * Convert a single byte to it's hexadecimal value
         */
        public static String byteToHex(byte b) {
            return Integer.toString((b & 0xFF) + 0x100, 16).substring(1);
        }

        /**
         * Convert an array of bytes to a string
         */
        public static String byteToHex(byte[] b) {
            String result = "";
            for (byte aB : b) result += Integer.toString((aB & 0xff) + 0x100, 16).substring(1);
            return result;
        }

        /**
         * Convert a block (array of bytes) to a matrix representation
         */
        public static void blockToMatrix(byte[] block, byte[][] matrix,
                                         boolean columnMapping) {
            int size = block.length / 3;
            if (columnMapping) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < size; j++) {
                        matrix[i][j] = block[i + 3 * j];
                    }
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < size; j++) {
                        matrix[i][j] = block[size * i + j];
                    }
                }
            }
        }

        /**
         * Convert a matrix to a block representation
         */
        public static void matrixToBlock(byte[] block, byte[][] matrix,
                                         boolean columnMapping) {
            int size = matrix[0].length;
            if (columnMapping) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < size; j++) {
                        block[i + 3 * j] = matrix[i][j];
                    }
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < size; j++) {
                        block[size * i + j] = matrix[i][j];
                    }
                }
            }
        }

        /**
         * Copy a matrix
         */
        public static void copyMatrix(byte[][] in, byte[][] out) {
            for (int i = 0; i < in.length; i++)
                for (int j = 0; j < in[i].length; j++)
                    out[i][j] = in[i][j];
        }

        public static byte[] readFile(String filePath) throws java.io.IOException {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes, 0, (int) file.length());
            return bytes;
        }

        public static boolean saveFile(String filePath, byte[] data)                throws IOException {
            File file = new File(filePath);
            FileOutputStream fis = new FileOutputStream(file);
            fis.write(data);
            fis.close();
            return true;
        }

        /**
         * Computes a XOR operation between two byte arrays
         */
        public static byte[] xor(byte[] a, byte[] b) {
            byte[] output = new byte[a.length];

            for (int i = 0; i < a.length; i++)
                output[i] = (byte) (a[i] ^ b[i]);

            return output;
        }

        public static byte[] not(byte[] in) {
            byte[] output = new byte[in.length];

            for (int i = 0; i < in.length; i++)
                output[i] = (byte) (in[i] ^ 0xFF);

            return output;
        }

        public static byte[] and(byte[] a, byte[] b) {
            byte[] output = new byte[a.length];

            for (int i = 0; i < a.length; i++)
                output[i] = (byte) (a[i] & b[i]);

            return output;
        }

        public static byte[] multiplyByPx(byte[] input) {
            byte[] output = new byte[input.length];

            System.arraycopy(input, 1, output, 0, 9);

            output[9] = (byte) (input[10] ^ T1(input[0]));
            output[10] = (byte) (input[11] ^ T0(input[0]));
            output[11] = input[0];

            return output;
        }

        static byte T1(byte U) {
            return (byte) (U ^ ((U & 0xFF) >>> 3) ^ ((U & 0xFF) >>> 5));
        }

        static byte T0(byte U) {
            return (byte) ((U << 5) ^ (U << 3));
        }

        public static byte[] lpad(byte[] message, int n) {
            byte[] leftPaddedMessage = new byte[n];

            for (int i = 0; i < n - message.length; i++)
                leftPaddedMessage[i] = 0;
            for (int i = n - message.length; i < n; i++) {
                leftPaddedMessage[i] = message[i + message.length - n];
            }

            return leftPaddedMessage;
        }

        public static byte[] rpad(byte[] message, int n) {
            byte[] rightPaddedMessage = new byte[n];

            for (int i = 0; i < message.length; i++)
                rightPaddedMessage[i] = message[i];
            for (int i = message.length; i < n; i++)
                rightPaddedMessage[i] = 0;

            return rightPaddedMessage;
        }
    }
}

package mainTest;

import java.io.IOException;

public class DepositSystem {

    public static void main(String[] args){
        try {
           Solution.exe4(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

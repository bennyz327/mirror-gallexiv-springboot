package playGround.utils;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RandomTest {
    @Test
    public void testUUID() {
        //UUID的原理是什麼？
        //UUID是由一組32位元的16進位數字所組成，這組數字會被分成五個區段，並以"-"字元分隔，如下：
        //UUID如何保持唯一性？
        //UUID的唯一性是透過MAC位址、時間戳、命名空間、隨機數、伺服器編號等資訊來保證。
        //常見的用法是用來產生一組不重複的ID，例如在資料庫中的主鍵。
        //去除"-"字元後，UUID的長度為32位元，因此UUID的理論上有2^128種可能性。
        //若需要多對一且又具辨識性可以產生後再加上一個編號，例如：UUID-1、UUID-2、UUID-3...等。
        String a = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        System.out.println(a);
        String b = UUID.randomUUID().toString();
        System.out.println(b);
        System.out.println("-----------");
        for (int i = 0; i < 10; i++) {
            String c = UUID.randomUUID().toString();
            System.out.println(c);
        }
    }
}

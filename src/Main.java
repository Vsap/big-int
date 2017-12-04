import javafx.util.Pair;

/**
 * Created by Alina on 25.09.17.
 */
public class Main {
    public static void main(String[] args){
        BigInt a = new BigInt("332D0");
        BigInt b = new BigInt("278B9"); // "111"*"B140A41" = 05EEF51
        BigInt little = new BigInt("5");
        BigIntAlgorithm c = BigIntAlgorithm.getInstance();
        BigInt y = c.LongMul(a,b);
        System.out.println();
        System.out.println("A > B = "+ BigInt.LongCmp(a,b));
        System.out.print("  A = " + a.getString());
            //a.Print();
        System.out.println();

        System.out.println("A.bitLength = "+a.BitLength());
        System.out.print("A*2 = " + c.LongMulOneDigit(a, 2).getString());
        System.out.println();

        //c.LongMulOneDigit(a, 2).Print();
        System.out.print("A+B = " + c.LongAdd(a,b).getString());
        System.out.println();

        //c.LongAdd(a,b).Print();
        System.out.print("A-B = " +  c.LongSub(a,b).getString());
        System.out.println();

        //c.LongSub(a,b).Print();
        System.out.print("|B-A| = " + c.LongAbsSub(b,a).getString());
        System.out.println();

        //c.LongAbsSub(b,a).Print();
        System.out.print("A*B = "+ c.LongMul(a,b).getString());
        System.out.println();

        //c.LongMul(a,b).Print();;
        System.out.print("A^B 1= " + c.LongPower1(a,little).getString());
        System.out.println();

        //c.LongPower1(a,little).Print();
        Pair<BigInt,BigInt> p = c.LongDivMod(y,b);
        System.out.print("A*B/B = " + p.getKey().getString());
        System.out.println();

        //p.getKey().Print();
        System.out.print("A*B/B mod B = " + p.getValue().getString());
        System.out.println();

        //p.getValue().Print();
        System.out.print("NSD(A,B) = "+ c.NSD_Euclid(a,b).getString());
        System.out.println();

        //c.NSD_Euclid(a,b).Print();

    }
}

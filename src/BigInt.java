/**
 * Created by Alina on 25.09.17.
 */
import javafx.util.Pair;
import jdk.nashorn.internal.ir.annotations.Ignore;

//import org.*;
public class BigInt implements Const{
    private int[] bigArr;
    private BigInt DoubleBigInt = new BigInt(2);
    private BigInt(int digit){
        bigArr = new int[digit*MAX_LENGTH];
    }

    public BigInt(String s){
        for (int i = min(MAX_LENGTH-1, s.length()-1);  i>=0; i--){
            bigArr[i+MAX_LENGTH-s.length()]=Integer.parseInt(Character.toString(s.charAt(i)),16);

        }
    }
    public BigInt(){
        bigArr = new int[MAX_LENGTH];
    }

    protected String getString(){
        StringBuilder s = new StringBuilder();
        for (int x:this.bigArr) s.append(Integer.toString(x, 16));
        return s.toString();
    }

    protected static int min(int a, int b) { return a>b ? b : a;}
    protected static int max(int a, int b) { return a>b ? a : b;}

    protected static BigInt LongMax(BigInt A, BigInt B){return (LongCmp(A,B)==1) ? A : B; }
    protected static BigInt LongMin(BigInt A, BigInt B){return (LongCmp(A,B)==1) ? B : A; }
    protected static int LongCmp(BigInt A, BigInt B){
        int i = 0;
        try {
            while (A.bigArr[i]==B.bigArr[i]) i++;
        } catch (ArrayIndexOutOfBoundsException e) {}
        if (i==MAX_LENGTH) return 0;
        else if (A.bigArr[i]>B.bigArr[i]) return 1;
        else return -1;
    }

    protected int BitAt(int n){
        int bit = n & (DEGREE_FOR_RADIX-1); // number bit
        int cell = n >> DEGREE_FOR_DEGREE;   // number cell
        int temp = 1 << bit;
        return(bigArr[MAX_LENGTH - cell - 1]&temp)>>bit;

    }

    protected BigInt LongShiftDigitsToHigh (int shift){
        if (shift >=MAX_LENGTH) return new BigInt();
        BigInt Shifted = new BigInt(getString());
        for(int i = 0; i+shift < MAX_LENGTH; i++){
            Shifted.bigArr[i] = bigArr[i+shift];
        }
        for (int i = MAX_LENGTH - shift; i<MAX_LENGTH; i++){
            Shifted.bigArr[i] = 0;
        }
        return Shifted;
    }
    private BigInt LongShiftDigitsToLow (int shift){
        if (shift>=MAX_LENGTH) return new BigInt();
        BigInt Shifted = new BigInt(getString());
        for(int i = shift; i < MAX_LENGTH; i++){
            Shifted.bigArr[i] = bigArr[i-shift];
        }
        for (int i = 0; i<shift; i++){
            Shifted.bigArr[i] = 0;
        }
        return Shifted;
    }
    private BigInt LongShiftBitsToHigh(int shift){
        BigInt C = new BigInt(getString());
        int bit = shift & (DEGREE_FOR_RADIX-1); // number bit
        int cell = shift >> DEGREE_FOR_DEGREE;  // number cell
        if(cell!=0) C=C.LongShiftDigitsToHigh(cell);
        if (bit!=0){
            for (int i = max(MAX_LENGTH - C.BigLength() - 1, 0); i < MAX_LENGTH - cell - 1; i++){
                int temp = C.bigArr[i+1]>>(DEGREE_FOR_RADIX - bit);
                int a1 = C.bigArr[i]<<bit|temp;
                C.bigArr[i] = a1&(RADIX-1);
            }
            C.bigArr[MAX_LENGTH - cell - 1] = (C.bigArr[MAX_LENGTH - cell - 1]<<bit)& (RADIX - 1);
        }
        return C;
    }
    protected BigInt LongShiftBits(int shift){
        if(shift>0) return LongShiftBitsToHigh(shift);
        else if(shift<0) return LongShiftBitsToLow(-shift);
        else return this;
    }
    protected BigInt LongShiftDigits(int shift){
        if(shift>0) return LongShiftDigitsToHigh(shift);
        else if(shift<0) return LongShiftDigitsToLow(-shift);
        else return this;
    }
    protected BigInt LongShiftBitsToLow(int shift){
        if (shift>=BitLength()) return new BigInt();
        BigInt C = new BigInt(getString());
        int bit = shift & (DEGREE_FOR_RADIX-1); // number bit
        int cell = shift >> DEGREE_FOR_DEGREE;  // number cell
        if(cell!=0) C=C.LongShiftDigitsToLow(cell);
        if(bit!=0) {
            for(int i = MAX_LENGTH-1; i>MAX_LENGTH-C.BigLength(); i--){
                int a1 = C.bigArr[i]>>bit;
                int pow = (int) Math.pow(2,bit)-1;
                int and = C.bigArr[i-1]&pow;
                int temp = and<<(DEGREE_FOR_RADIX-bit);
                C.bigArr[i] = temp|a1;
            }
            C.bigArr[MAX_LENGTH-C.BigLength()] = C.bigArr[MAX_LENGTH-C.BigLength()]>>bit;
        }
        return C;
    }

    protected int BigLength(){
        for(int i = 0; i<MAX_LENGTH; i++){
            if(bigArr[i]!=0) return MAX_LENGTH-i;
        }
        return 0;
    }
    protected int BitLength(){
        String arr = Integer.toBinaryString(bigArr[MAX_LENGTH-BigLength()]);
        return 4*(BigLength()-1)+arr.length();
    }

    protected void toOne(int n){
        int bit = n & (DEGREE_FOR_RADIX-1); // number bit
        int cell = n >> DEGREE_FOR_DEGREE;   // number cell
        int index = MAX_LENGTH -cell - 1;
        bigArr[index] = (int)bigArr[index]|(int)(1<<bit);
    }
    @Ignore
    protected void toZero(int n){
                                                    //System.out.println("Run to Zero");
        int k = n & (DEGREE_FOR_RADIX-1); // number bit
                                                    //System.out.println("number bit = " +  k);
        int i = n >> DEGREE_FOR_DEGREE;   // number cell
                                                    //System.out.println("number cell = " + i);
        this.bigArr[i] = this.bigArr[i]&((RADIX - 2)<<k);
    }

    public void Print(){
        for (int b : this.bigArr) {
            System.out.print(b + " ");
        }
        System.out.println();

    }
    @Ignore
    public void PrintToTen(){
        for (int b : this.bigArr) {
            System.out.print(b + " ");
        }
        System.out.println();
    }


    public BigInt LongAdd (BigInt A, BigInt B) {
        int carry = 0;
        int temp;
        for(int i = MAX_LENGTH-1; i >= 0; i--){
            temp = A.bigArr[i] + B.bigArr[i] + carry;
            this.bigArr[i] = temp & (RADIX-1);
            carry = temp >> DEGREE_FOR_RADIX;
        }
        return this;
    }

    public BigInt LongSub(BigInt A, BigInt B) {
        if(LongCmp(A,B)==1){
            int borrow = 0;
            int temp;
            for(int i = MAX_LENGTH-1; i >=0; i--) {
                temp = A.bigArr[i] - B.bigArr[i] - borrow;
                if (temp >= 0) {
                    this.bigArr[i] = temp;
                    borrow = 0;
                } else {
                    this.bigArr[i] = RADIX + temp;
                    borrow = 1;
                }
            }
        } else this.bigArr = new int[MAX_LENGTH];
        return this;
    }
    public BigInt LongAbsSub(BigInt A, BigInt B){
        return  (LongCmp(A,B)==1) ? LongSub(A,B) : LongSub(B,A);
    }

    public BigInt LongMulOneDigit(BigInt A, int c){
        int carry=0;
        int temp;
        for(int i = MAX_LENGTH-1; i>0; i--){
            temp = A.bigArr[i]*c+carry;
            this.bigArr[i] = temp & (RADIX-1);
            carry = temp >> DEGREE_FOR_RADIX;
        }
        this.bigArr[0]=carry;
        return this;
    }

    public BigInt LongMul(BigInt A, BigInt B){
        BigInt temp = new BigInt();
        BigInt b = new BigInt();
        for(int i = MAX_LENGTH -1; i>=0;i--){ // i = 4
            int x =0;
            temp.LongMulOneDigit(A, B.bigArr[i]);
            temp = temp.LongShiftDigitsToHigh(MAX_LENGTH-1-i);
            b.LongAdd(b, temp);
        }
        return b;
    }

    public Pair<BigInt,BigInt> LongDivMod(BigInt A, BigInt B){
        int k = B.BitLength();
        BigInt Q = new BigInt();
        BigInt R = new BigInt(A.getString());
        BigInt C;
        while(LongCmp(R,B)!=-1){
            int t = R.BitLength();
            C = B.LongShiftBitsToHigh(t-k);
            if(LongCmp(R,C)==-1){
                t = t - 1;
                C = B.LongShiftBitsToHigh(t-k);
            }
            R.LongSub(R,C);
            Q.toOne(t-k);
        }
        return new Pair<>(Q,R);
    }

    public BigInt LongPower1(BigInt A, BigInt B){
        BigInt C = new BigInt("1");
        for(int i = 0; i<B.BitLength(); i++){
            if (B.BitAt(i)==1) C = LongMul(C, A);
            A = LongMul(A,A);
        }
        return C;
    }
    @Ignore
    public BigInt LongPower2(BigInt A, BigInt B){
        BigInt C = new BigInt("1");
        for(int i = B.BitLength()-1; i >=0; i--){
            int value = B.BitAt(i);
            if(value==1) C = LongMul(C,A);
            if(i!=0) C = LongMul(C,C);
        }
        return C;
    }

}

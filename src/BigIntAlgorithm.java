import javafx.util.Pair;

public class BigIntAlgorithm extends BigInt{

    private static BigIntAlgorithm ourInstance = new BigIntAlgorithm();
    public static BigIntAlgorithm getInstance() {
        return ourInstance;
    }
    private BigIntAlgorithm(){}

    public BigInt NSD_Euclid(BigInt a, BigInt b){
        BigInt A = new BigInt(a.getString());
        BigInt B = new BigInt(b.getString());
        BigInt D = new BigInt("1");
        while (A.BitAt(0)==0 && B.BitAt(0)==0){
            A = A.LongShiftBits(-1);
            B = B.LongShiftBits(-1);
            D = D.LongShiftBits(1);
        }
        while (A.BitAt(0)==0){
            A = A.LongShiftBits(-1);
        }
        while (BigInt.LongCmp(B,new BigInt())!=0){
            while (B.BitAt(0)==0){
                B = B.LongShiftBits(-1);
            }
            BigInt X = BigInt.LongMin(A,B);
            B = LongAbsSub(A,B);
            A = X;
        }
        D = LongMul(D,A);
        return D;

    }


    private BigInt CalcMu(BigInt A, BigInt N){
        if(A.BitLength()==MAX_LENGTH*4) {
            BigInt b_in_degree = new BigInt("1");
            b_in_degree = b_in_degree.LongShiftBits(MAX_LENGTH - 1);
            Pair<BigInt, BigInt> div = LongDivMod(b_in_degree, N);
            BigInt mu = div.getKey().LongShiftBits(1);
            BigInt mod = div.getValue().LongShiftBits(1);
            Pair<BigInt, BigInt> mod_div = LongDivMod(mod, N);
            mu = LongAdd(mu, mod_div.getKey());
            return mu;
        } else{
            return LongDivMod(new BigInt("1").LongShiftBits(A.BitLength()),N).getKey();
        }
    }       //additional
    private BigInt BarrettReduction(BigInt X, BigInt N, BigInt mu){
        int k = X.BitLength()/2;
        BigInt Q = X.LongShiftDigits(-(k-1));
        Q = LongMul(Q,mu);
        Q = Q.LongShiftDigits(-(k+1));
        BigInt R = LongSub(X, LongMul(Q,N));
        while (BigInt.LongCmp(R,N)!=-1) R = LongSub(R,N);
        return R;
    }


    public BigInt LongModPowerBarrett(BigInt A, BigInt B, BigInt N){
        BigInt mu = CalcMu(A, N);
        BigInt C = new BigInt("1");
        for(int i = 0; i<B.BitLength(); i++){
            if(B.BitAt(i)==1) C = BarrettReduction(LongMul(C,A), N, mu);
            A = BarrettReduction(LongMul(A,A), N, mu);
        }
        return C;
    }



}

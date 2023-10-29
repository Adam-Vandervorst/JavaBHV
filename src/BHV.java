import java.util.Arrays;

public class BHV {
    static int DIMENSION = 8192;
    static int BYTES = DIMENSION/8;

    static {
        System.loadLibrary("BHV");
    }

    protected byte[] data;

    private BHV() {
        data = new byte[BYTES];
    }

    private static native void randInto(byte[] a);
    public static BHV rand() {
        BHV ins = new BHV();
        randInto(ins.data);
        return ins;
    }

    private static native void randomInto(byte[] a, float f);
    public static BHV random(float f) {
        BHV ins = new BHV();
        randomInto(ins.data, f);
        return ins;
    }

    private static native long active(byte[] a);
    public long active() {
        return active(data);
    }

    private static native void orInto(byte[] a, byte[] b, byte[] c);
    public BHV or(BHV other) {
        BHV ins = new BHV();
        orInto(data, other.data, ins.data);
        return ins;
    }

    private static native void andInto(byte[] a, byte[] b, byte[] c);
    public BHV and(BHV other) {
        BHV ins = new BHV();
        andInto(data, other.data, ins.data);
        return ins;
    }

    private static native void xorInto(byte[] a, byte[] b, byte[] c);
    public BHV xor(BHV other) {
        BHV ins = new BHV();
        xorInto(data, other.data, ins.data);
        return ins;
    }

    private static native void majorityInto(Object[] xs, byte[] c);
    public static BHV majority(BHV[] xs) {
        // FIXME creating the bs array is wasteful
        BHV ins = new BHV();
        Object[] bs = new Object[xs.length];
        for (int i = 0; i < xs.length; ++i) {
            bs[i] = xs[i].data;
        }
        majorityInto(bs, ins.data);
        return ins;
    }

    private static native long hamming(byte[] a, byte[] b);
    public long hamming(BHV other) {
        return hamming(data, other.data);
    }

    private static native void permuteInto(byte[] a, long perm, byte[] b);
    public BHV permute(long perm) {
        BHV ins = new BHV();
        permuteInto(data, perm, ins.data);
        return ins;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BHV bhv = (BHV) o;
        return Arrays.equals(data, bhv.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    public static void basic() {
        BHV x = BHV.rand();
        BHV y = BHV.rand();
        BHV z = x.xor(y);

        System.out.println(z.active());
        System.out.println(x.and(BHV.random(0.1f)).active());
        assert x.xor(z).xor(y).active() == 0;
    }

    public static void plainTest() {
        long x = 0x7834d688d8827099L;
        for (int i = 0; i < 5000000; i++) {
            x = x + (x % 7);
        }

        System.out.println(x);

        int N = 201;

        long t0 = System.nanoTime();

        BHV[] rs = new BHV[N];
        for (int i = 0; i < N; i++)
            rs[i] = BHV.rand();

        long t1 = System.nanoTime();
        System.out.println("rand: " + (t1 - t0));

        BHV[] ps = new BHV[N];
        for (int i = 0; i < N; i++)
            ps[i] = rs[i].permute(42);

        long t2 = System.nanoTime();
        System.out.println("new permute: " + (t2 - t1));

        for (int i = 0; i < N; i++)
            assert rs[i].equals(ps[i].permute(-42));

        long t3 = System.nanoTime();
        System.out.println("rpermute eq: " + (t3 - t2));

        BHV m = BHV.majority(rs);

        long t4 = System.nanoTime();
        System.out.println("majority: " + (t4 - t3));

        long t5;
        double[] qs = new double[N];
        if (false) {
            BHV[] ds = new BHV[N];
            for (int i = 0; i < N; i++)
                ds[i] = rs[i].xor(m);

            t5 = System.nanoTime();
            System.out.println("xor: " + (t5 - t4));

            for (int i = 0; i < N; i++)
                qs[i] = ds[i].active();

            long t6 = System.nanoTime();
            System.out.println("active: " + (t6 - t5));
        } else {
            for (int i = 0; i < N; i++)
                qs[i] = m.hamming(rs[i]);

            t5 = System.nanoTime();
            System.out.println("hamming: " + (t5 - t4));
        }

        double sum = 0;
        for (double q : qs)
            sum += q;

        System.out.println(sum / N);
    }

    public static void main(String[] args) {
        plainTest();
    }
}


/*
class NativePackedBHV(AbstractBHV):
v   def __init__(self, native_bhv):
        self.ins = native_bhv

    @classmethod
v   def rand(cls):
        return NativePackedBHV(CNativePackedBHV.rand())

    @classmethod
v   def random(cls, p):
        return NativePackedBHV(CNativePackedBHV.random(p))

v   def __or__(self, other):
        return NativePackedBHV(self.ins | other.ins)

v   def __and__(self, other):
        return NativePackedBHV(self.ins & other.ins)

v   def __xor__(self, other):
        return NativePackedBHV(self.ins ^ other.ins)

    def __invert__(self):
        return NativePackedBHV(~self.ins)

    def select(self, when1, when0):
        return NativePackedBHV(self.ins.select(when1.ins, when0.ins))

    def ternary(self, y, z, op):
        return NativePackedBHV(self.ins.ternary(y.ins, z.ins, op))

    @classmethod
v   def majority(cls, xs):
        return NativePackedBHV(CNativePackedBHV.majority([x.ins for x in xs]))

    @classmethod
    def threshold(cls, xs, t):
        return NativePackedBHV(CNativePackedBHV.threshold([x.ins for x in xs], t))

    @classmethod
    def representative(cls, xs):
        return NativePackedBHV(CNativePackedBHV.representative([x.ins for x in xs]))

v   def active(self):
        return self.ins.active()

v   def hamming(self, other):
        return self.ins.hamming(other.ins)

    def permute_words(self, permutation_id: int):
        return NativePackedBHV(self.ins.permute_words(permutation_id))

    def permute_byte_bits(self, permutation_id: int):
        return NativePackedBHV(self.ins.permute_byte_bits(permutation_id))

    def roll_words(self, d: int):
        return NativePackedBHV(self.ins.roll_words(d))

    def roll_word_bits(self, d: int):
        return NativePackedBHV(self.ins.roll_word_bits(d))

    def roll_bits(self, d: int):
        return NativePackedBHV(self.ins.roll_bits(d))

    def _permute_composite(self, permutation_id: 'tuple'):
        v = self
        for e in permutation_id:
            v = v.permute(e)
        return v

v   def permute(self, permutation_id: 'int | tuple'):
        if isinstance(permutation_id, tuple):
            return self._permute_composite(permutation_id)
        return NativePackedBHV(self.ins.permute(permutation_id))

    def rehash(self):
        return NativePackedBHV(self.ins.rehash())

    def swap_halves(self):
        return NativePackedBHV(self.ins.swap_halves())

    def __eq__(self, other):
        return self.ins == other.ins

    @classmethod
    def from_bytes(cls, bs):
        return NativePackedBHV(CNativePackedBHV.from_bytes(bs))

    def to_bytes(self):
        return self.ins.to_bytes()

    def __getstate__(self):
        return self.to_bytes()

    def __setstate__(self, state):
        self.ins = CNativePackedBHV.from_bytes(state)

NativePackedBHV.ZERO = NativePackedBHV(CNativePackedBHV.ZERO)
NativePackedBHV.ONE = NativePackedBHV(CNativePackedBHV.ONE)
NativePackedBHV.HALF = NativePackedBHV(CNativePackedBHV.HALF)
NativePackedBHV._FEISTAL_SUBKEYS = NativePackedBHV.nrand2(NativePackedBHV._FEISTAL_ROUNDS, 4)

 */
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

    private native long active(byte[] a);
    public long active() {
        return active(data);
    }

    private native void orInto(byte[] a, byte[] b, byte[] c);
    public BHV or(BHV other) {
        BHV ins = new BHV();
        orInto(data, other.data, ins.data);
        return ins;
    }

    private native void andInto(byte[] a, byte[] b, byte[] c);
    public BHV and(BHV other) {
        BHV ins = new BHV();
        andInto(data, other.data, ins.data);
        return ins;
    }

    private native void xorInto(byte[] a, byte[] b, byte[] c);
    public BHV xor(BHV other) {
        BHV ins = new BHV();
        xorInto(data, other.data, ins.data);
        return ins;
    }

    public static void main(String[] args) {
        BHV x = BHV.rand();
        BHV y = BHV.rand();
        BHV z = x.xor(y);

        System.out.println(z.active());
        System.out.println(x.and(BHV.random(0.1f)).active());
        assert x.xor(z).xor(y).active() == 0;
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
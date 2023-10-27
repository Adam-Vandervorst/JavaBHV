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

    private native long active(byte[] a);
    public long active() {
        return active(data);
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
        assert x.xor(z).xor(y).active() == 0;
    }
}

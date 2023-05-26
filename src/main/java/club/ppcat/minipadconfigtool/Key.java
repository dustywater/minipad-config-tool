package club.ppcat.minipadconfigtool;

public class Key {
    private int keyNum;
    private int rt;
    private int crt;
    private int rest;
    private int down;
    private int hid;
    private char key;

    private int rtds;
    private int rtus;
    private int lh;
    private int uh;

    private final int trdt;

    public Key(int keyNum, int rt, int crt, int rest, int down, int hid, int rtds, int rtus, int lh, int uh, int trdt) {
        this.keyNum = keyNum;
        this.rt = rt;
        this.crt = crt;
        this.rest = rest;
        this.down = down;
        this.hid = hid;
        this.rtds = rtds;
        this.rtus = rtus;
        this.lh = lh;
        this.uh = uh;
        this.trdt = trdt;
    }

    public int getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(int keyNum) {
        this.keyNum = keyNum;
    }

    public int isRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public int isCrt() {
        return crt;
    }

    public void setCrt(int crt) {
        this.crt = crt;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int isHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public int getRtds() {
        return rtds;
    }

    public void setRtds(int rtds) {
        this.rtds = rtds;
    }

    public int getRtus() {
        return rtus;
    }

    public void setRtus(int rtus) {
        this.rtus = rtus;
    }

    public int getLh() {
        return lh;
    }

    public void setLh(int lh) {
        this.lh = lh;
    }

    public int getUh() {
        return uh;
    }

    public void setUh(int uh) {
        this.uh = uh;
    }

    public int getTrdt() {
        return trdt;
    }

}

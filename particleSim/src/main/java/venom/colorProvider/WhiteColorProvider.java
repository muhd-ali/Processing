package venom.colorProvider;

import venom.contract.ColorProvider;

public class WhiteColorProvider<T> implements ColorProvider<T> {
    public float col1() {
        return 255;
    }

    public float col2() {
        return 255;
    }

    public float col3() {
        return 255;
    }

    public float col4() {
        return 255;
    }

    public void setDataObject(T obj) {
    }
}

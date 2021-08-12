package venom.behavior;

import venom.contract.Forced;

public interface ExternalInteractive<T extends Forced>  {
    void updateExternal(T external);
}

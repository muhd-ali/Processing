package venom.behavior;

import venom.contract.Forced;

public abstract class ExternalInteractionBehavior<T extends Forced> extends Behavior<T> implements ExternalInteractive<T> {
    T external;

    @Override
    public void updateExternal(T external) {
        this.external = external;
    }
}

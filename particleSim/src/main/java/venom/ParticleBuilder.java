package venom;

import processing.core.PVector;

class ParticleBuilder {
    private PVector _currPos, _pivotPos;
    private float _charge = 1;

    Particle build() {
        return new Particle(_currPos, _pivotPos, _charge);
    }

    ParticleBuilder currPos(PVector _currPos) {
        this._currPos = _currPos;
        return this;
    }

    ParticleBuilder pivotPos(PVector _pivotPos) {
        this._pivotPos = _pivotPos;
        return this;
    }

    ParticleBuilder charge(float _charge) {
        this._charge = _charge;
        return this;
    }
}

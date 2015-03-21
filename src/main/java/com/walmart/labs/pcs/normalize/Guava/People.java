package com.walmart.labs.pcs.normalize.Guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by pzhong1 on 3/19/15.
 */
public class People {
    private String attriName;
    private String attriValue;
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final People other = (People) obj;
        return Objects.equal(this.attriName, other.attriName)
                && Objects.equal(this.attriValue, other.attriValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.attriName, this.attriValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attriName", attriName)
                .add("attriValue", attriValue)
                .toString();
    }
}

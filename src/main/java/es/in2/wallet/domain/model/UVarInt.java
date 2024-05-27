package es.in2.wallet.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static es.in2.wallet.domain.util.ApplicationConstants.MSB;
import static es.in2.wallet.domain.util.ApplicationConstants.MSBALL;

@Getter
public class UVarInt {

    private final long value;
    private final byte[] bytes;
    private final int length;

    public UVarInt(long value) {
        this.value = value;
        this.bytes = bytesFromUInt(value);
        this.length = bytes.length;
    }

    // Converts a long value into a byte array following UVarInt encoding rules
    private byte[] bytesFromUInt(long num) {
        List<Byte> varInt = new ArrayList<>();
        long rest = num;
        while ((rest & MSBALL) != 0) {
            varInt.add((byte) ((rest & 0xFF) | MSB));
            rest = rest >>> 7;
        }
        varInt.add((byte) rest);
        byte[] result = new byte[varInt.size()];
        for (int i = 0; i < varInt.size(); i++) {
            result[i] = varInt.get(i);
        }
        return result;
    }

    // Converts the UVarInt to a hexadecimal string
    @Override
    public String toString() {
        return "0x" + Long.toHexString(value);
    }

}

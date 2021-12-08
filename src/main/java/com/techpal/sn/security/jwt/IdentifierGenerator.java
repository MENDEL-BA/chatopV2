package com.techpal.sn.security.jwt;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
public class IdentifierGenerator {

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        String sessionId = new BigInteger(130, random).toString(32);
        sessionId += "-" + new BigInteger(130, random).toString(32);
        sessionId += "-" + new BigInteger(130, random).toString(32);
        sessionId += "-" + new BigInteger(130, random).toString(32);
        return sessionId;
    }

    public String generate(int blockSize, int length, boolean hasSeperator) {
        StringBuilder id = new StringBuilder(generateBlock(blockSize));
        for (int i = 1; i < length; i++) {
            if (hasSeperator)
                id.append("-");
            id.append(generateBlock(blockSize));
        }
        return id.toString();
    }

    private String generateBlock(int size) {
        return new BigInteger(130, random).toString(32).subSequence(0, size).toString();
    }
}

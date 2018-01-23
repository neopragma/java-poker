package com.neopragma.helpers;

import java.text.MessageFormat;

/**
 * <pre>
 * Wraps java.lang.String to guard against NullPointerException errors and reduce the risk of
 * buffer overflow exploits by explicitly limiting the length of string values.
 *
 * Objects are immutable.
 *
 * Usage:
 *
 * new SmartString().withValue("XXXX");
 * Can't be null or empty string, maximum length is 2GB.
 *
 * new SmartString().nullable.withValue("XXXX");
 * Can be null or empty string, maximum length is 2GB.
 *
 * new SmartString().withMaximumLength(400).withValue("XXXX");
 * Can't be null or empty string, maximum length is 400.
 *
 * new SmartString().withMaximumLength(200).nullable().withValue("XXXX");
 * Can be null or empty string, maximum length is 200.
 *
 * SmartString ss = smartString.copy();
 * Makes a deep copy.
 *
 * smartString1.equals(smartString2);
 * Standard equals() behavior.
 *
 * smartString1.compareTo(smartString2);
 * Comparison is based only on the wrapped String value.
 * (this == null && that != null) returns -1 (this < that)
 * (this != null && that == null) returns 1 (this > that)
 * (this == null && that == null) returns 0 (this == that)
 * otherwise returns this.value().compareTo(that.value())
 *
 * @throws RuntimeException when the value provided does not conform with the specified constraints
 * or when .value() is called before .withValue() has been called.
 * </pre>
 *
 * @author neopragma
 */
public final class SmartString implements Comparable<SmartString> {

    private final static String EMPTY_STRING = "";
    private final static boolean DEFAULT_NULLABLE = false;
    private final static long DEFAULT_MAXIMUM_LENGTH = 2147483648L;

    private String value;
    private boolean nullable = DEFAULT_NULLABLE;
    private long maximumLength = DEFAULT_MAXIMUM_LENGTH;
    private boolean valid = false;

    private String valueExceedsMaximumLengthMessage =
            MessageFormat.format("Value exceeds maximum length ({0,number,integer} bytes).", maximumLength);
    private final static String VALUE_CANNOT_BE_NULL = "Value cannot be null or empty.";

    public SmartString nullable() {
        this.nullable = true;
        return this;
    }

    public SmartString notNullable() {
        this.nullable = false;
        return this;
    }

    public SmartString withMaximumLength(long maximumLength) {
        this.maximumLength = maximumLength;
        return this;
    }

    public SmartString withValue(String value) {
        validate(value);
        this.value = value;
        valid = true;
        return this;
    }

    public String value() {
        if (valid) {
            return value;
        } else {
            throw new RuntimeException("SmartString object is not valid: No value has been set. Use .withValue(\"x\") on creation.");
        }
    }

    public SmartString copy() {
        if (nullable) {
            return new SmartString().nullable().withMaximumLength(maximumLength).withValue(value);
        } else {
            return new SmartString().notNullable().withMaximumLength(maximumLength).withValue(value);
        }
    }

    public boolean isNullable() {
        return nullable;
    }

    public long maximumLength() {
        return maximumLength;
    }

    private void validate(String value) {
        if (!nullable && empty(value)) {
            throw new RuntimeException(VALUE_CANNOT_BE_NULL);
        }
        if (value != null && value.length() > maximumLength) {
            throw new RuntimeException(valueExceedsMaximumLengthMessage);
        }
    }

    private boolean empty(String value) {
        return value == null || value == EMPTY_STRING;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (value != null ? value.hashCode() : 0);
        hash = 53 * hash + ((int) (maximumLength % 9));
        hash += nullable ? 1 : 2;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!SmartString.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final SmartString that = (SmartString) obj;
        if ((this.value == null) ? (that.value != null) : !this.value.equals(that.value)) {
            return false;
        }
        if (this.maximumLength() != that.maximumLength()) {
            return false;
        }
        if (this.isNullable() != that.isNullable()) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(SmartString that) {
        int lessThan = -1;
        int greaterThan = 1;
        int equal = 0;
        if (this.value() == null && that.value() != null) {
            return lessThan;
        }
        if (this.value() != null && that.value() == null) {
            return greaterThan;
        }
        if (this.value() == null && that.value() == null) {
            return equal;
        }
        return this.value().compareTo(that.value());
    }
}

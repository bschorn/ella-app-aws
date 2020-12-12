package org.schorn.ella.aws.file;

import org.schorn.ella.app.receipt.IAppReceipt;

public class AWSFileReceipt implements IAppReceipt {

    private Exception exception = null;

    private AWSFileReceipt(AWSFileReceipt.Builder builder) {
        this.exception = builder.exception;
    }


    static AWSFileReceipt.Builder builder() {
        return new AWSFileReceipt.Builder();
    }

    static class Builder {

        private Exception exception = null;

        AWSFileReceipt.Builder set(Exception exception) {
            this.exception = exception;
            return this;
        }


        AWSFileReceipt build() {
            return new AWSFileReceipt(this);
        }
    }

}

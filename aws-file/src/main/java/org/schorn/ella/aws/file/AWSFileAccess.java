package org.schorn.ella.aws.file;

import org.schorn.ella.app.access.IAppAccess;


/**
 *
 */
public class AWSFileAccess implements IAppAccess {

    private final int x;

    private AWSFileAccess(AWSFileAccess.Builder builder) {
        this.x = builder.x;
    }


    static AWSFileAccess.Builder builder() {
        return new Builder();
    }

    static class Builder {

        private int x;

        Builder addX(int x) {
            this.x = x;
            return this;
        }


        AWSFileAccess build()  {
            return new AWSFileAccess(this);
        }
    }

}

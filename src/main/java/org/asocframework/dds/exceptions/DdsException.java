package org.asocframework.dds.exceptions;

/**
 * @author jiqing
 * @version $Id: DdsException，v 1.0 2018/3/21 15:15 jiqing Exp $
 * @desc
 */
public class DdsException extends RuntimeException{

    public DdsException() {
        super();
    }

    public DdsException(String message) {
        super(message);
    }

    public DdsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DdsException(Throwable cause) {
        super(cause);
    }
}

package com.github.d1le.pipeline.bitbucket

/**
 * @author Alexey Lapin
 */
interface Bitbucket extends Serializable {

    public static final String PROTOCOL_SSH = "ssh"
    public static final String PROTOCOL_HTTP = "http"

    def checkout(CheckoutDef checkoutDef)
}

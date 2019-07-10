package ru.sbrf.pegi18.pipeline.bitbucket

/**
 * @author Alexey Lapin
 */
interface Bitbucket extends Serializable {

    public static final String PROTOCOL_SSH = "ssh"
    public static final String PROTOCOL_HTTP = "http"

    def checkout(CheckoutDef checkoutDef)
}

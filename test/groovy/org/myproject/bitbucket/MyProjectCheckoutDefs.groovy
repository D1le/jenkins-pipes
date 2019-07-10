package org.myproject.bitbucket

import ru.sbrf.pegi18.pipeline.bitbucket.CheckoutDef

/**
 * @author Alexey Lapin
 */
class MyProjectCheckoutDefs implements Serializable {

    static CheckoutDef mycoFirstApp(String branch) {
        new CheckoutDef().protocol("ssh").repo("myco-first-app").branch(branch)
    }

    static CheckoutDef mycoSecondApp(String branch) {
        new CheckoutDef().protocol("ssh").repo("myco-second-app").branch(branch)
    }

}

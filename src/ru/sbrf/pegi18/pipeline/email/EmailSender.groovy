package ru.sbrf.pegi18.pipeline.email

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import ru.sbrf.pegi18.pipeline.jenkins.Executable

/**
 * @author Alexey Lapin
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '', includes = 'to,subject')
class EmailSender implements Serializable {

    Executable exec

    Map<Class<? extends EmailBlock>, EmailBlock> emailBlocks = [:]
    List<String> to = []
    def replyTo
    EmailSubject subject

    EmailSender send() {
        exec.emailext(
            mimeType: 'text/html',
            subject: subject.build(),
            body: getBodyText(),
            attachLog: true,
            compressLog: true,
            to: to.join(","),
            replyTo: replyTo
        )
        this
    }

    EmailSender to(String address) {
        to = [address]
        this
    }

    EmailSender andTo(String address) {
        to.add(address)
        this
    }

    EmailSender andTo(List<String> addressList) {
        to.addAll(addressList)
        this
    }

    EmailSender addBlock(EmailBlock block) {
        emailBlocks.put(block.getClass(), block)
        this
    }

    private String getBodyText() {
        def bodyText = new HtmlStyleBlock(exec.impl()).asHtml() + "<BODY>"
        for (EmailBlock emailBlock : emailBlocks.values()) {
            if (emailBlock) {
                try {
                    String blockHtml = emailBlock.asHtml()
                    if (blockHtml && !blockHtml.isEmpty()) {
                        bodyText += blockHtml + "<BR/>"
                    }
                } catch (Throwable throwable) {
                    exec.impl().echo "INFO: Can not build email block ${emailBlock.class.toString()}. Reason: ${throwable.getMessage()}"
                }
            }
        }
        bodyText += "</BODY>"
        bodyText
    }

}

# jenkins-pipes
Jenkins shared library with small Object Oriented helper framework
```groovy
// create specific implementation of BuildContext
pipe(MyProjectBuildContext.newInstance(this)) {

    // initialize BuildContext
    ctx().init()

    // checkout repos from various vcs of your project context
    staged("bitbucket example") {
        def checkout = new CheckoutDef().protocol("ssh").repo("ansible-playbooks").branch("develop")
        ctx().getBitbuckets().ci().checkout(checkout)
        ctx().getBitbuckets().ci().checkout(MyProjectCheckoutDefs.mycoFirstApp("develop"))
        ctx().getBitbuckets().ci().checkout(MyProjectCheckoutDefs.mycoSecondApp("release/new"))
    }

    // run builds on various jenkinses of your project context
    staged("jenkins example") {
        def build = new BuildDef().name("backup-first-server").propagateErrors(false).waitForCompletion(false)
        ctx().getJenkinses().ci().newBuild(build)
        ctx().getJenkinses().ci().newBuild(MyProjectBuildDefs.deployFirstApp())
        ctx().getJenkinses().ci().newBuild(MyProjectBuildDefs.deploySecondApp("2.0").parameter("backup", true))
        ctx().getJenkinses().staging().newBuild(MyProjectBuildDefs.deploySecondApp("1.9").parameter("backup", false))
    }

    // download or upload artifacts from various nexuses of your context
    staged("nexus example") {
        def artifact = new ArtifactDef().group("org.myco.team1").id("first-app").version("1.69.1")
        ctx().getNexuses().prod().download(artifact, "path/to/download")
        ctx().getNexuses().prod().download(MyProjectArtifactDefs.firstApp("2.0"), "path/to/download")
        ctx().getNexuses().prod().upload(MyProjectArtifactDefs.secondApp("1.5.14"), "path/to/upload")
    }

    // write colored messages to build log
    ctx().getLogger().debug("debug")
    ctx().getLogger().info("info")
    ctx().getLogger().warn("warn")
    ctx().getLogger().error("error")
    ctx().getLogger().log("log")

    def logger = LoggerManager.getLogger("SharedLibraryClass")
    logger.debug("debug from logger")

    // send customizable email notifications
    ctx().getEmailSender()
        .to("me@myco.org")
        .subject({ "Build finished" })
        .addBlock(new StageResultsBlock(this, ctx().getStageExecutor().getResults()))
        .send()
}
```
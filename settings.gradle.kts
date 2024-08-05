plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "CoronaPluginsReforge"


include("auth")
include("common")
include("tpmachine")
include("friends")
include("arena")
include("bot")

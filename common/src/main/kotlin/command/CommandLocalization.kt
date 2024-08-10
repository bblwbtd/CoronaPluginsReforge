package xyz.ldgame.corona.common.command

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.output.HelpFormatter
import com.github.ajalt.clikt.output.Localization
import com.github.ajalt.clikt.parameters.groups.ChoiceGroup
import com.github.ajalt.clikt.parameters.groups.MutuallyExclusiveOptions
import org.bukkit.command.CommandSender
import xyz.ldgame.corona.common.i18n.translate

class CommandLocalization(val getSender: () -> CommandSender?) : Localization {
    private val defaultLocal = object : Localization {}

    private fun translate(key: String, args: Map<String, String> = emptyMap()): String {
        val sender = getSender()
        return key.translate(sender, args)
    }

    override fun usageError(): String = translate("usageError") ?: defaultLocal.usageError()

    override fun badParameter(): String = translate("badParameter") ?: defaultLocal.badParameter()

    override fun badParameterWithMessage(message: String) =
        translate("badParameterWithMessage", mapOf("message" to message))
            ?: defaultLocal.badParameterWithMessage(message)

    override fun badParameterWithParam(paramName: String) =
        translate("badParameterWithParam", mapOf("paramName" to paramName))
            ?: defaultLocal.badParameterWithParam(paramName)

    /** Message for [BadParameterValue] */
    override fun badParameterWithMessageAndParam(paramName: String, message: String) =
        translate("badParameterWithMessageAndParam", mapOf("paramName" to paramName, "message" to message))
            ?: defaultLocal.badParameterWithMessageAndParam(paramName, message)

    /** Message for [MissingOption] */
    override fun missingOption(paramName: String) = translate("missingOption", mapOf("paramName" to paramName))
        ?: defaultLocal.missingOption(paramName)

    /** Message for [MissingArgument] */
    override fun missingArgument(paramName: String) = translate("missingArgument", mapOf("paramName" to paramName))
        ?: defaultLocal.missingArgument(paramName)

    /** Message for [NoSuchSubcommand] */
    override fun noSuchSubcommand(name: String, possibilities: List<String>): String {
        if (possibilities.isEmpty()) return translate("noSuchSubcommand", mapOf("name" to name))
            ?: defaultLocal.noSuchSubcommand(name, possibilities)

        return translate(
            "noSuchSubcommandWithSuggestion",
            mapOf("name" to name, "suggestions" to possibilities.joinToString())
        )
            ?: defaultLocal.noSuchSubcommand(name, possibilities)
    }

    /** Message for [NoSuchOption] */
    override fun noSuchOption(name: String, possibilities: List<String>): String {
        val suggestion = possibilities.firstOrNull() ?: return translate("noSuchOption", mapOf("name" to name))
            ?: defaultLocal.noSuchOption(name, possibilities)

        return translate("noSuchOptionWithSuggestion", mapOf("name" to name, "suggestion" to suggestion))
            ?: defaultLocal.noSuchOption(name, possibilities)
    }

    /** One extra argument is present */
    override fun extraArgumentOne(name: String) = translate("extraArgumentOne", mapOf("name" to name))
        ?: defaultLocal.extraArgumentOne(name)

    /** More than one extra argument is present */
    override fun extraArgumentMany(name: String, count: Int) =
        translate("extraArgumentMany", mapOf("name" to name, "count" to count.toString()))
            ?: defaultLocal.extraArgumentMany(name, count)

    /** Required [MutuallyExclusiveOptions] was not provided */
    override fun requiredMutexOption(options: String) = translate("requiredMutexOption", mapOf("options" to options))
        ?: defaultLocal.requiredMutexOption(options)

    /**
     * [ChoiceGroup] value was invalid
     *
     * @param choices non-empty list of possible choices
     */
    override fun invalidGroupChoice(value: String, choices: List<String>): String {
        return translate("invalidGroupChoice", mapOf("value" to value, "choices" to choices.joinToString()))
            ?: defaultLocal.invalidGroupChoice(value, choices)
    }

    /** Invalid value for a parameter of type [Int] or [Long] */
    override fun intConversionError(value: String) = translate("intConversionError", mapOf("value" to value))
        ?: defaultLocal.intConversionError(value)

    /** Invalid value for a parameter of type [Boolean] */
    override fun boolConversionError(value: String) = translate("boolConversionError", mapOf("value" to value))
        ?: defaultLocal.boolConversionError(value)

    /** Invalid value falls outside range */
    override fun rangeExceededMax(value: String, limit: String) =
        translate("rangeExceededMax", mapOf("value" to value, "limit" to limit))
            ?: defaultLocal.rangeExceededMax(value, limit)

    /** Invalid value falls outside range */
    override fun rangeExceededMin(value: String, limit: String) =
        translate("rangeExceededMin", mapOf("value" to value, "limit" to limit))
            ?: defaultLocal.rangeExceededMin(value, limit)

    /** Invalid value falls outside range */
    override fun rangeExceededBoth(value: String, min: String, max: String) =
        translate("rangeExceededBoth", mapOf("value" to value, "min" to min, "max" to max))
            ?: defaultLocal.rangeExceededBoth(value, min, max)

    /**
     * Invalid value for `choice` parameter
     *
     * @param choices non-empty list of possible choices
     */
    override fun invalidChoice(choice: String, choices: List<String>): String {
        return translate("invalidChoice", mapOf("choice" to choice, "choices" to choices.joinToString()))
            ?: defaultLocal.invalidChoice(choice, choices)
    }

    /** Metavar used for options with unspecified value type */
    override fun defaultMetavar() = translate("defaultMetavar") ?: defaultLocal.defaultMetavar()

    /** Metavar used for options that take [String] values */
    override fun stringMetavar() = translate("stringMetavar") ?: defaultLocal.stringMetavar()

    /** Metavar used for options that take [Float] or [Double] values */
    override fun floatMetavar() = translate("floatMetavar") ?: defaultLocal.floatMetavar()

    /** Metavar used for options that take [Int] or [Long] values */
    override fun intMetavar() = translate("intMetavar") ?: defaultLocal.intMetavar()

    /** The title for the usage section of help output */
    override fun usageTitle(): String = translate("usageTitle") ?: defaultLocal.usageTitle()

    /** The title for the options section of help output */
    override fun optionsTitle(): String = translate("optionsTitle") ?: defaultLocal.optionsTitle()

    /** The title for the arguments section of help output */
    override fun argumentsTitle(): String = translate("argumentsTitle") ?: defaultLocal.argumentsTitle()

    /** The title for the subcommands section of help output */
    override fun commandsTitle(): String = translate("commandsTitle") ?: defaultLocal.commandsTitle()

    /** The placeholder that indicates where options may be present in the usage help output */
    override fun optionsMetavar(): String = translate("optionsMetavar") ?: defaultLocal.optionsMetavar()

    /** The placeholder that indicates where subcommands may be present in the usage help output */
    override fun commandMetavar(): String = translate("commandMetavar") ?: defaultLocal.commandMetavar()

    /** The placeholder that indicates where arguments may be present in the usage help output */
    override fun argumentsMetavar(): String = translate("argumentsMetavar") ?: defaultLocal.argumentsMetavar()

    /** Text rendered for parameters tagged with [HelpFormatter.Tags.DEFAULT] */
    override fun helpTagDefault(): String = translate("helpTagDefault") ?: defaultLocal.helpTagDefault()

    /** Text rendered for parameters tagged with [HelpFormatter.Tags.REQUIRED] */
    override fun helpTagRequired(): String = translate("helpTagRequired") ?: defaultLocal.helpTagRequired()

    /** The default message for the `--help` option. */
    override fun helpOptionMessage(): String = translate("helpOptionMessage") ?: defaultLocal.helpOptionMessage()

}
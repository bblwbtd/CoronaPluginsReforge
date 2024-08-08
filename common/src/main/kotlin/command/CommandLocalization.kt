package xyz.ldgame.corona.common.command

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.output.HelpFormatter
import com.github.ajalt.clikt.output.Localization
import com.github.ajalt.clikt.parameters.groups.ChoiceGroup
import com.github.ajalt.clikt.parameters.groups.MutuallyExclusiveOptions
import org.bukkit.command.CommandSender
import xyz.ldgame.corona.common.i18n.locale

class CommandLocalization(val getSender: () -> CommandSender?) : Localization {
    private val defaultLocal = object : Localization {}

    private fun getText(key: String, variables: Map<String, String> = emptyMap()): String? {
        val localizedText = key.locale(getSender())
        if (localizedText != key) {
            val sb = StringBuilder(localizedText)
            variables.forEach { (k, v) ->
                val placeholder = "$$k"
                var index = sb.indexOf(placeholder)
                while (index != -1) {
                    sb.replace(index, index + placeholder.length, v)
                    index = sb.indexOf(placeholder, index + v.length)
                }
            }
            return sb.toString()
        }
        return null
    }

    override fun usageError(): String = getText("usageError") ?: defaultLocal.usageError()

    override fun badParameter(): String = getText("badParameter") ?: defaultLocal.badParameter()

    override fun badParameterWithMessage(message: String) =
        getText("badParameterWithMessage", mapOf("message" to message))
            ?: defaultLocal.badParameterWithMessage(message)

    override fun badParameterWithParam(paramName: String) =
        getText("badParameterWithParam", mapOf("paramName" to paramName))
            ?: defaultLocal.badParameterWithParam(paramName)

    /** Message for [BadParameterValue] */
    override fun badParameterWithMessageAndParam(paramName: String, message: String) =
        getText("badParameterWithMessageAndParam", mapOf("paramName" to paramName, "message" to message))
            ?: defaultLocal.badParameterWithMessageAndParam(paramName, message)

    /** Message for [MissingOption] */
    override fun missingOption(paramName: String) = getText("missingOption", mapOf("paramName" to paramName))
        ?: defaultLocal.missingOption(paramName)

    /** Message for [MissingArgument] */
    override fun missingArgument(paramName: String) = getText("missingArgument", mapOf("paramName" to paramName))
        ?: defaultLocal.missingArgument(paramName)

    /** Message for [NoSuchSubcommand] */
    override fun noSuchSubcommand(name: String, possibilities: List<String>): String {
        if (possibilities.isEmpty()) return getText("noSuchSubcommand", mapOf("name" to name))
            ?: defaultLocal.noSuchSubcommand(name, possibilities)

        return getText(
            "noSuchSubcommandWithSuggestion",
            mapOf("name" to name, "suggestions" to possibilities.joinToString())
        )
            ?: defaultLocal.noSuchSubcommand(name, possibilities)
    }

    /** Message for [NoSuchOption] */
    override fun noSuchOption(name: String, possibilities: List<String>): String {
        val suggestion = possibilities.firstOrNull() ?: return getText("noSuchOption", mapOf("name" to name))
            ?: defaultLocal.noSuchOption(name, possibilities)

        return getText("noSuchOptionWithSuggestion", mapOf("name" to name, "suggestion" to suggestion))
            ?: defaultLocal.noSuchOption(name, possibilities)
    }

    /** One extra argument is present */
    override fun extraArgumentOne(name: String) = getText("extraArgumentOne", mapOf("name" to name))
        ?: defaultLocal.extraArgumentOne(name)

    /** More than one extra argument is present */
    override fun extraArgumentMany(name: String, count: Int) =
        getText("extraArgumentMany", mapOf("name" to name, "count" to count.toString()))
            ?: defaultLocal.extraArgumentMany(name, count)

    /** Required [MutuallyExclusiveOptions] was not provided */
    override fun requiredMutexOption(options: String) = getText("requiredMutexOption", mapOf("options" to options))
        ?: defaultLocal.requiredMutexOption(options)

    /**
     * [ChoiceGroup] value was invalid
     *
     * @param choices non-empty list of possible choices
     */
    override fun invalidGroupChoice(value: String, choices: List<String>): String {
        return getText("invalidGroupChoice", mapOf("value" to value, "choices" to choices.joinToString()))
            ?: defaultLocal.invalidGroupChoice(value, choices)
    }

    /** Invalid value for a parameter of type [Int] or [Long] */
    override fun intConversionError(value: String) = getText("intConversionError", mapOf("value" to value))
        ?: defaultLocal.intConversionError(value)

    /** Invalid value for a parameter of type [Boolean] */
    override fun boolConversionError(value: String) = getText("boolConversionError", mapOf("value" to value))
        ?: defaultLocal.boolConversionError(value)

    /** Invalid value falls outside range */
    override fun rangeExceededMax(value: String, limit: String) =
        getText("rangeExceededMax", mapOf("value" to value, "limit" to limit))
            ?: defaultLocal.rangeExceededMax(value, limit)

    /** Invalid value falls outside range */
    override fun rangeExceededMin(value: String, limit: String) =
        getText("rangeExceededMin", mapOf("value" to value, "limit" to limit))
            ?: defaultLocal.rangeExceededMin(value, limit)

    /** Invalid value falls outside range */
    override fun rangeExceededBoth(value: String, min: String, max: String) =
        getText("rangeExceededBoth", mapOf("value" to value, "min" to min, "max" to max))
            ?: defaultLocal.rangeExceededBoth(value, min, max)

    /**
     * Invalid value for `choice` parameter
     *
     * @param choices non-empty list of possible choices
     */
    override fun invalidChoice(choice: String, choices: List<String>): String {
        return getText("invalidChoice", mapOf("choice" to choice, "choices" to choices.joinToString()))
            ?: defaultLocal.invalidChoice(choice, choices)
    }

    /** Metavar used for options with unspecified value type */
    override fun defaultMetavar() = getText("defaultMetavar") ?: defaultLocal.defaultMetavar()

    /** Metavar used for options that take [String] values */
    override fun stringMetavar() = getText("stringMetavar") ?: defaultLocal.stringMetavar()

    /** Metavar used for options that take [Float] or [Double] values */
    override fun floatMetavar() = getText("floatMetavar") ?: defaultLocal.floatMetavar()

    /** Metavar used for options that take [Int] or [Long] values */
    override fun intMetavar() = getText("intMetavar") ?: defaultLocal.intMetavar()

    /** The title for the usage section of help output */
    override fun usageTitle(): String = getText("usageTitle") ?: defaultLocal.usageTitle()

    /** The title for the options section of help output */
    override fun optionsTitle(): String = getText("optionsTitle") ?: defaultLocal.optionsTitle()

    /** The title for the arguments section of help output */
    override fun argumentsTitle(): String = getText("argumentsTitle") ?: defaultLocal.argumentsTitle()

    /** The title for the subcommands section of help output */
    override fun commandsTitle(): String = getText("commandsTitle") ?: defaultLocal.commandsTitle()

    /** The placeholder that indicates where options may be present in the usage help output */
    override fun optionsMetavar(): String = getText("optionsMetavar") ?: defaultLocal.optionsMetavar()

    /** The placeholder that indicates where subcommands may be present in the usage help output */
    override fun commandMetavar(): String = getText("commandMetavar") ?: defaultLocal.commandMetavar()

    /** The placeholder that indicates where arguments may be present in the usage help output */
    override fun argumentsMetavar(): String = getText("argumentsMetavar") ?: defaultLocal.argumentsMetavar()

    /** Text rendered for parameters tagged with [HelpFormatter.Tags.DEFAULT] */
    override fun helpTagDefault(): String = getText("helpTagDefault") ?: defaultLocal.helpTagDefault()

    /** Text rendered for parameters tagged with [HelpFormatter.Tags.REQUIRED] */
    override fun helpTagRequired(): String = getText("helpTagRequired") ?: defaultLocal.helpTagRequired()

    /** The default message for the `--help` option. */
    override fun helpOptionMessage(): String = getText("helpOptionMessage") ?: defaultLocal.helpOptionMessage()

}
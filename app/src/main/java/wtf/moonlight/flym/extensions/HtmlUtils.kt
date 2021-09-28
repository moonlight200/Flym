package wtf.moonlight.flym.extensions

import net.frju.flym.utils.HtmlUtils
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull


object HtmlUtils {
    private const val URL_SPACE = "%20"
    private val IMG_REGEX = "<img\\s+[^>]*src=\\s*['\"]([^'\"]+)['\"][^>]*>".toRegex(RegexOption.IGNORE_CASE)

    fun improveHtmlContent(content: String, baseUrl: String): String {
        // TODO refactor
        return HtmlUtils.improveHtmlContent(content, baseUrl)
    }

    fun getMainImageUrl(content: String): String? {
        if (content.isBlank()) return null

        return IMG_REGEX.findAll(content)
            .mapNotNull { it.groupValues[1].replace(" ", URL_SPACE) }
            .firstOrNull()
    }
}

fun String.toBaseUrl(): String? =
    this.toHttpUrlOrNull()
        ?.newBuilder()
        ?.encodedPath("")
        ?.encodedQuery("")
        ?.encodedFragment("")
        ?.build()
        ?.toString()
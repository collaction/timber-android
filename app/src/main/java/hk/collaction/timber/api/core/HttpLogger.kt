package hk.collaction.timber.api.core

import com.himphen.logger.Logger
import hk.collaction.timber.utils.JsonUtils
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    private var mMessage: StringBuffer = StringBuffer("")

    override fun log(message: String) {
        var tempMessage = message
        if (message.startsWith("--> POST") ||
            message.startsWith("--> GET") ||
            message.startsWith("--> PATCH") ||
            message.startsWith("--> PUT")
        ) {
            mMessage.setLength(0)
        }
        if (message.startsWith("{") && message.endsWith("}") ||
            message.startsWith("[") && message.endsWith("]")
        ) {
            tempMessage = JsonUtils.formatJson(message)
        }
        mMessage.append(tempMessage + "\n")

        if (message.startsWith("<-- END HTTP")) {
            Logger.log(Logger.INFO, "api", mMessage.toString(), null)
        }
    }
}

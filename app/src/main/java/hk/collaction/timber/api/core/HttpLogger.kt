package hk.collaction.timber.api.core

import com.orhanobut.logger.Logger
import hk.collaction.timber.utils.JsonUtils
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    private var mMessage: StringBuffer = StringBuffer("")
    private var isMultipart = false

    override fun log(message: String) {
        var tempMessage = message

        if (message.contains("�")) {
            if (!isMultipart) {
                isMultipart = true
                tempMessage = "<BINARY DATA>"
            } else {
                return
            }
        }

        if (message.startsWith("--> POST") ||
            message.startsWith("--> GET") ||
            message.startsWith("--> PATCH") ||
            message.startsWith("--> PUT")
        ) {
            mMessage.setLength(0) // If not 0, previous log will be printed
        }

        if (message.startsWith("{") && message.endsWith("}") ||
            message.startsWith("[") && message.endsWith("]")
        ) {
            tempMessage = JsonUtils.formatJson(JsonUtils.decodeUnicode(message))
        }

        mMessage.append(tempMessage + "\n")

        if (message.startsWith("<-- END HTTP")) {
            Logger.log(Logger.INFO, "api", mMessage.toString(), null)
        }
    }
}

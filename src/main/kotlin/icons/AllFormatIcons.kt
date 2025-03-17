package icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object AllFormatIcons {

    @JvmField
    val Logo: Icon = load("/icons/AllFormat.svg")

    @JvmStatic
    fun load(path: String): Icon {
        return IconLoader.getIcon(path, AllFormatIcons::class.java)
    }
}
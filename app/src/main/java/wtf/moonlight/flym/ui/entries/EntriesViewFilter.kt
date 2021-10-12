package wtf.moonlight.flym.ui.entries

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import net.fred.feedex.R

enum class EntriesViewFilter(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    ALL(R.string.all, R.drawable.ic_all_white_24dp),
    UNREAD(R.string.unreads, R.drawable.ic_unread_white_24dp),
    FAVORITE(R.string.favorites, R.drawable.ic_star_white_24dp)
}
package org.hackatum.zoolens.i18n

import androidx.compose.runtime.staticCompositionLocalOf

data class NewsCardData(val date: String, val headline: String, val description: String, val link: String)

data class TicketCardData(val title: String, val buttonText: String, val link: String)

data class AppStrings(
    val home: String,
    val search: String,
    val ai: String,
    val map: String,
    val settings: String,
    val toggleLanguage: String,
    val newsHeadline: String,
    val newsCard1: String,
    val newsCard2: String,
    val newsCard3: String,
    val ticketsHeadline: String,
    val newsCards: List<NewsCardData>,
    val ticketCards: List<TicketCardData>
)

val newsCardsEn = listOf(
    NewsCardData(
        "Nov 19, 2025",
        "Many new males at Hellabrunn: Elk, Kudu, Zebra and Mhorr gazelle",
        "Hellabrunn Zoo welcomes several new male animals, including elk, kudu, zebra, and Mhorr gazelle. Visit us to see the new arrivals and learn more about their habitats.",
        "https://www.hellabrunn.de/newsdetailseite/viele-neue-maennchen-in-hellabrunn-verstaerkung-bei-elch-kudu-zebra-und-mhorrgazelle"
    ),
    NewsCardData(
        "Nov 14, 2025",
        "New 'Mia San Tier' episode: Cannibalism in the animal kingdom",
        "The latest episode of 'Mia San Tier' explores the phenomenon of cannibalism among animals. Discover why it happens and what it means for species survival.",
        "https://www.hellabrunn.de/newsdetailseite/neue-mia-san-tier-folge-kannibalismus-im-tierreich"
    ),
    NewsCardData(
        "Nov 13, 2025",
        "Pied tamarins new at Hellabrunn: Small primates with a big message for species conservation",
        "Hellabrunn Zoo is now home to pied tamarins, small primates with an important message about conservation. Learn how you can help protect endangered species.",
        "https://www.hellabrunn.de/newsdetailseite/zweifarbentamarine-neu-in-hellabrunn-kleine-primaten-mit-grosser-botschaft-fuer-den-artenschutz"
    )
)

val newsCardsDe = listOf(
    NewsCardData(
        "19. Nov 2025",
        "Viele neue Männchen in Hellabrunn: Elch, Kudu, Zebra und Mhorrgazelle",
        "Gleich vier neue männliche Tiere sind in den letzten Wochen eingetroffen und bringen frischen Wind in den Tierpark: Ein Zebrahengst, ein Elchbulle, ein Kudu- und ein Mhorrgazellen-Bock sorgen dafür, dass bei diesen Arten nun wieder Nachwuchs möglich ist.",
        "https://www.hellabrunn.de/newsdetailseite/viele-neue-maennchen-in-hellabrunn-verstaerkung-bei-elch-kudu-zebra-und-mhorrgazelle"),
    NewsCardData(
        "14. Nov 2025",
        "Neue 'Mia San Tier' Folge: Kannibalismus im Tierreich",
        "Ja, manche Tiere fressen andere Tiere, auch im Zusammenhang mit Kannibalismus. Das ist im Tierreich weit verbreitet, sei es bei Vielfraßen, Spinnen, Bartgeiern, Hai-Jungtieren oder Löwen. In der Natur wird nichts verschwendet. Kannibalismus hat viele unterschiedliche Gründe – ein paar davon wie den territorialen Kannibalismus, Infantizid oder Kainismus stellen wir Euch in der neuen „Mia san Tier“ Podcast-Folge vor. Begleitet unseren Zoologischen Leiter Carsten Zehrer in dieses ungewöhnliche, aber gleichsam spannende Thema. \n" +
            "\n",
        "https://www.hellabrunn.de/newsdetailseite/neue-mia-san-tier-folge-kannibalismus-im-tierreich"),
    NewsCardData(
        "13. Nov 2025", "Zweifarbentamarine neu in Hellabrunn: Kleine Primaten mit großer Botschaft für den Artenschutz",
        "Der Tierpark Hellabrunn freut sich über eine besondere Bereicherung in der „Welt der kleinen Affen“: Seit wenigen Wochen leben hier erstmals Zweifarbentamarine (Saguinus bicolor). Die auffällig gezeichneten Krallenaffen mit dem charakteristischen schwarzen, unbehaarten Gesicht und dem schneeweißen „Mantel“ gelten in ihrem natürlichen Verbreitungsgebiet in Brasilien als vom Aussterben bedroht.\n" +
            "\n",
        "https://www.hellabrunn.de/newsdetailseite/zweifarbentamarine-neu-in-hellabrunn-kleine-primaten-mit-grosser-botschaft-fuer-den-artenschutz")
)

val ticketCardsEn = listOf(
    TicketCardData("Day Ticket", "Go to Shop", "https://hellabrunn.ticketfritz.de/Shop/Index/tageskarten/10942"),
    TicketCardData("Voucher", "Get now", "https://hellabrunn.ticketfritz.de/Shop/Index/gutscheine/11122")
)

val ticketCardsDe = listOf(
    TicketCardData("Tageskarte", "Zum Shop", "https://hellabrunn.ticketfritz.de/Shop/Index/tageskarten/10942"),
    TicketCardData("Gutscheine", "Jetzt holen", "https://hellabrunn.ticketfritz.de/Shop/Index/gutscheine/11122")
)

val EnStrings = AppStrings(
    home = "Home",
    search = "Search",
    ai = "AI",
    map = "Map",
    settings = "Settings",
    toggleLanguage = "Toggle Language",
    newsHeadline = "News",
    newsCard1 = "This is a placeholder news card in English.",
    newsCard2 = "Another English news card.",
    newsCard3 = "More English news here.",
    ticketsHeadline = "Tickets",
    newsCards = newsCardsEn,
    ticketCards = ticketCardsEn
)

val DeStrings = AppStrings(
    home = "Start",
    search = "Suche",
    ai = "KI",
    map = "Karte",
    settings = "Einstellungen",
    toggleLanguage = "Sprache umschalten",
    newsHeadline = "Aktuelle Meldungen",
    newsCard1 = "Dies ist eine Platzhalter-Nachrichtenkarte auf Deutsch.",
    newsCard2 = "Noch eine deutsche Nachrichtenkarte.",
    newsCard3 = "Weitere deutsche Nachrichten hier.",
    ticketsHeadline = "Tickets",
    newsCards = newsCardsDe,
    ticketCards = ticketCardsDe
)

val LocalStrings = staticCompositionLocalOf { EnStrings }

fun stringsFor(language: String?): AppStrings = when (language) {
    "de", "de-DE", "de_AT", "de-AT", "de-CH", "de-CH" -> DeStrings
    else -> EnStrings
}

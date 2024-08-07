package domain.model

enum class RateStatus(val title: String) {
    Idel(title = "Rates"),
    Fresh(title = "Fresh rates"),
    Stale(title = "Rates are not fresh")
}

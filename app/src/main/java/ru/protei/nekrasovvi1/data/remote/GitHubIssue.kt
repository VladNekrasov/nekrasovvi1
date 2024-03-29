package ru.protei.nekrasovvi1.data.remote

data class GitHubIssue(
    val number: Long?,
    val title: String,
    val body: String,
    var state: String?
) {
    constructor(number: Long?,
                title: String,
                body: String) :
            this(number, title, body, null)
}

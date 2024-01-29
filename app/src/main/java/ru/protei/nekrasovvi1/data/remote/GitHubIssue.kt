package ru.protei.nekrasovvi1.data.remote

import ru.protei.nekrasovvi1.domain.Note

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

fun toNote(gitHubIssue: GitHubIssue): Note {
    return Note(
        id = null,
        title = gitHubIssue.title,
        text = gitHubIssue.body,
        remoteId = gitHubIssue.number
    )
}

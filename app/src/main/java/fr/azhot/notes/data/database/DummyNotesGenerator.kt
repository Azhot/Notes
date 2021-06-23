package fr.azhot.notes.data.database

import fr.azhot.notes.domain.model.Note

object DummyNotesGenerator {

    private val notes = mutableListOf(
        Note(
            title = "Malesuada fames ac ante ipsum",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        ),
        Note(title = "Vestibulum", "Suspendisse potenti."),
        Note(
            title = "Sagittis aliquet",
            "Morbi ut nunc quis nibh lacinia laoreet at ut enim. Vestibulum et tortor nunc. Morbi sagittis arcu non libero congue vulputate."
        ),
        Note(
            title = "In mattis nunc vitae",
            "Interdum et malesuada fames ac ante ipsum primis in faucibus."
        ),
        Note(
            title = "Morbi",
            "Praesent faucibus sit amet sapien ac fermentum. Ut ac maximus felis, eget accumsan odio. Praesent ornare convallis quam, ut dignissim ante maximus eu. Donec porttitor sit amet lectus non eleifend. Vivamus luctus diam elit, a vestibulum est pretium eget."
        ),
        Note(
            title = "Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
            "Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque."
        ),
    )

    fun getNotes() = notes

    fun getNote(id: String): Note {
        for (note in notes) {
            if (note.id == id) {
                return note
            }
        }
        throw NoSuchElementException()
    }

    fun addNote(note: Note) {
        notes.add(note)
    }

    fun updateNote(note: Note) {
        for (n in notes) {
            if (n.id == note.id) {
                n.title = note.title
                n.text = note.text
            }
        }
    }

    fun deleteNote(note: Note) {
        notes.remove(note)
    }
}
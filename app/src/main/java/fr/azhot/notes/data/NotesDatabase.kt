package fr.azhot.notes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Provider

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {

    // dao
    abstract fun noteDao(): NotesDao


    // companion
    companion object {

        fun notesDatabaseCallback(notesDaoProvider: Provider<NotesDao>) =
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch {
                        populateDatabaseWithDummyData(notesDaoProvider)
                    }
                }
            }

        suspend fun populateDatabaseWithDummyData(notesDaoProvider: Provider<NotesDao>) {
            notesDaoProvider.get().insertNotes(
                NoteEntity(
                    title = "Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    text = "Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    position = 1,
                ),
                NoteEntity(
                    title = "Morbi",
                    text = "Praesent faucibus sit amet sapien ac fermentum. Ut ac maximus felis, eget accumsan odio. Praesent ornare convallis quam, ut dignissim ante maximus eu. Donec porttitor sit amet lectus non eleifend. Vivamus luctus diam elit, a vestibulum est pretium eget.",
                    position = 2
                ),
                NoteEntity(
                    title = "In mattis nunc vitae",
                    text = "Interdum et malesuada fames ac ante ipsum primis in faucibus.",
                    position = 3
                ),
                NoteEntity(
                    title = "Sagittis aliquet",
                    text = "Morbi ut nunc quis nibh lacinia laoreet at ut enim. Vestibulum et tortor nunc. Morbi sagittis arcu non libero congue vulputate.",
                    position = 4
                ),
                NoteEntity(
                    title = "Vestibulum",
                    text = "Suspendisse potenti.",
                    position = 5
                ),
                NoteEntity(
                    title = "Malesuada fames ac ante ipsum",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    position = 6
                ),
                NoteEntity(
                    title = "Malesuada fames ac ante ipsum",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    position = 7
                ),
                NoteEntity(
                    title = "Vestibulum",
                    text = "Suspendisse potenti.",
                    position = 8
                ),
                NoteEntity(
                    title = "Sagittis aliquet",
                    text = "Morbi ut nunc quis nibh lacinia laoreet at ut enim. Vestibulum et tortor nunc. Morbi sagittis arcu non libero congue vulputate.",
                    position = 9
                ),
                NoteEntity(
                    title = "In mattis nunc vitae",
                    text = "Interdum et malesuada fames ac ante ipsum primis in faucibus.",
                    position = 10
                ),
                NoteEntity(
                    title = "Morbi",
                    text = "Praesent faucibus sit amet sapien ac fermentum. Ut ac maximus felis, eget accumsan odio. Praesent ornare convallis quam, ut dignissim ante maximus eu. Donec porttitor sit amet lectus non eleifend. Vivamus luctus diam elit, a vestibulum est pretium eget.",
                    position = 11
                ),
                NoteEntity(
                    title = "Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    text = "Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    position = 12
                ),
                NoteEntity(
                    title = "Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    text = "Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    position = 13,
                ),
                NoteEntity(
                    title = "Morbi",
                    text = "Praesent faucibus sit amet sapien ac fermentum. Ut ac maximus felis, eget accumsan odio. Praesent ornare convallis quam, ut dignissim ante maximus eu. Donec porttitor sit amet lectus non eleifend. Vivamus luctus diam elit, a vestibulum est pretium eget.",
                    position = 14
                ),
                NoteEntity(
                    title = "In mattis nunc vitae",
                    text = "Interdum et malesuada fames ac ante ipsum primis in faucibus.",
                    position = 15
                ),
                NoteEntity(
                    title = "Sagittis aliquet",
                    text = "Morbi ut nunc quis nibh lacinia laoreet at ut enim. Vestibulum et tortor nunc. Morbi sagittis arcu non libero congue vulputate.",
                    position = 16
                ),
                NoteEntity(
                    title = "Vestibulum",
                    text = "Suspendisse potenti.",
                    position = 17
                ),
                NoteEntity(
                    title = "Malesuada fames ac ante ipsum",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    position = 18
                ),
                NoteEntity(
                    title = "Malesuada fames ac ante ipsum",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    position = 19
                ),
                NoteEntity(
                    title = "Vestibulum",
                    text = "Suspendisse potenti.",
                    position = 20
                ),
                NoteEntity(
                    title = "Sagittis aliquet",
                    text = "Morbi ut nunc quis nibh lacinia laoreet at ut enim. Vestibulum et tortor nunc. Morbi sagittis arcu non libero congue vulputate.",
                    position = 21
                ),
                NoteEntity(
                    title = "In mattis nunc vitae",
                    text = "Interdum et malesuada fames ac ante ipsum primis in faucibus.",
                    position = 22
                ),
                NoteEntity(
                    title = "Morbi",
                    text = "Praesent faucibus sit amet sapien ac fermentum. Ut ac maximus felis, eget accumsan odio. Praesent ornare convallis quam, ut dignissim ante maximus eu. Donec porttitor sit amet lectus non eleifend. Vivamus luctus diam elit, a vestibulum est pretium eget.",
                    position = 23
                ),
                NoteEntity(
                    title = "Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    text = "Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    position = 24
                ),
            )
        }
    }
}
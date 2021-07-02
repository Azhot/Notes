package fr.azhot.notes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.azhot.notes.domain.model.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Provider

@Database(
    entities = [Note::class],
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
                Note(
                    title = "Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    "Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    System.currentTimeMillis() + 12
                ),
                Note(
                    title = "Morbi",
                    "Praesent faucibus sit amet sapien ac fermentum. Ut ac maximus felis, eget accumsan odio. Praesent ornare convallis quam, ut dignissim ante maximus eu. Donec porttitor sit amet lectus non eleifend. Vivamus luctus diam elit, a vestibulum est pretium eget.",
                    System.currentTimeMillis() + 11
                ),
                Note(
                    title = "In mattis nunc vitae",
                    "Interdum et malesuada fames ac ante ipsum primis in faucibus.",
                    System.currentTimeMillis() + 10
                ),
                Note(
                    title = "Sagittis aliquet",
                    "Morbi ut nunc quis nibh lacinia laoreet at ut enim. Vestibulum et tortor nunc. Morbi sagittis arcu non libero congue vulputate.",
                    System.currentTimeMillis() + 9
                ),
                Note(
                    title = "Vestibulum",
                    "Suspendisse potenti.",
                    System.currentTimeMillis() + 8
                ),
                Note(
                    title = "Malesuada fames ac ante ipsum",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    System.currentTimeMillis() + 7
                ),
                Note(
                    title = "Malesuada fames ac ante ipsum",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    System.currentTimeMillis() + 6
                ),
                Note(
                    title = "Vestibulum",
                    "Suspendisse potenti.",
                    System.currentTimeMillis() + 5
                ),
                Note(
                    title = "Sagittis aliquet",
                    "Morbi ut nunc quis nibh lacinia laoreet at ut enim. Vestibulum et tortor nunc. Morbi sagittis arcu non libero congue vulputate.",
                    System.currentTimeMillis() + 4
                ),
                Note(
                    title = "In mattis nunc vitae",
                    "Interdum et malesuada fames ac ante ipsum primis in faucibus.",
                    System.currentTimeMillis() + 3
                ),
                Note(
                    title = "Morbi",
                    "Praesent faucibus sit amet sapien ac fermentum. Ut ac maximus felis, eget accumsan odio. Praesent ornare convallis quam, ut dignissim ante maximus eu. Donec porttitor sit amet lectus non eleifend. Vivamus luctus diam elit, a vestibulum est pretium eget.",
                    System.currentTimeMillis() + 2
                ),
                Note(
                    title = "Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    "Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                    System.currentTimeMillis() + 1
                ),
            )
        }
    }
}
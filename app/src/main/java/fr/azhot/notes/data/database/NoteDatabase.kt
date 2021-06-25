package fr.azhot.notes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.azhot.notes.data.database.dao.NoteDao
import fr.azhot.notes.domain.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    // dao
    abstract fun noteDao(): NoteDao


    // companion
    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDatabase {
            return INSTANCE ?: synchronized(NoteDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "Database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    database.noteDao().insert(
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
                        ), Note(
                            title = "Morbi",
                            "Praesent faucibus sit amet sapien ac fermentum. Ut ac maximus felis, eget accumsan odio. Praesent ornare convallis quam, ut dignissim ante maximus eu. Donec porttitor sit amet lectus non eleifend. Vivamus luctus diam elit, a vestibulum est pretium eget."
                        ),
                        Note(
                            title = "Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque.",
                            "Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque. Finibus mollis cursus. Cras sagittis aliquet interdum. In mattis nunc vitae ipsum sagittis faucibus. Aliquam erat volutpat. Etiam faucibus elit a dolor gravida, quis malesuada eros tristique. Nunc tempor, eros non consequat accumsan, augue mi porttitor sapien, at fringilla lacus eros sed nisi. Aliquam erat volutpat. Fusce at elit non ligula consequat rhoncus. Nulla auctor, enim quis efficitur porta, eros justo dapibus quam, vitae imperdiet neque risus vel risus. Duis sodales nisl ligula, in rutrum velit luctus id. Proin eget elit eu nunc tincidunt pharetra. In egestas non felis at pellentesque."
                        )
                    )
                }
            }
        }
    }
}
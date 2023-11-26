import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// DatabaseMigrations.kt

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE user_answers ADD COLUMN questionType TEXT NOT NULL DEFAULT ''")
    }
}

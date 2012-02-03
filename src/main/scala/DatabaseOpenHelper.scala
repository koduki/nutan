package cn.orz.pascal.nutan.persistance

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベース処理クラス
 */
class DatabaseOpenHelper(context: Context) extends SQLiteOpenHelper(context, "testdb", null, 1) {
  /**
   * データベースの生成に呼び出されるので、 スキーマの生成を行う
   */
  override def onCreate(db: SQLiteDatabase) = {
    def record(english: String, japanese: String, partOfSpeech: String, example: String) = {
      val record = new ContentValues();

      record.put("english", english)
      record.put("japanese", japanese)
      record.put("part_of_speech", partOfSpeech)
      record.put("example", example)

      println(japanese)

      record
    }

    db.beginTransaction();

    try {
      // テーブルの生成
      db.execSQL("create table word(id integer primary key autoincrement, english text, japanese text, part_of_speech text, example text)");

      // サンプルデータの投入
      db.insert("word", null, record("valid", "理にかなった", "形", "this is valid test."));
      db.insert("word", null, record("valid2", "理にかなった", "形", "this is valid test."));
      db.insert("word", null, record("valid3", "理にかなった", "形", "this is valid test."));

      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
  }

  /**
   * データベースの更新
   *
   * 親クラスのコンストラクタに渡すversionを変更したときに呼び出される
   */
  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    // データベースの更新
  }
}

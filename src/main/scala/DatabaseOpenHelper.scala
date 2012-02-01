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
    db.beginTransaction();

    try {
      // テーブルの生成

      db.execSQL("create table test1(name text)");

      // サンプルデータの投入

      val values = new ContentValues();
      values.put("name", "ts01");
      db.insert("test1", null, values);

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

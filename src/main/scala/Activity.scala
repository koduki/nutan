package cn.orz.pascal.nutan

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.view.View
import android.app.AlertDialog
import android.content.DialogInterface;
import cn.orz.pascal.nutan.model._
import scala.util._

import android.view.Menu
import android.view.MenuItem
import android.content.Intent

class MainActivity extends Activity {
  val words = List(
    Word("complicated", "複雑な、分かりにくい"),
    Word("look up A", "Aを調べる"),
    Word("confess A", "Aを人に告白する"),
    Word("enter A", "Aを入力する"),
    Word("location", "位置、所在地"),
    Word("destination", "目的地"),
    Word("mobile phone", "携帯電話"),
    Word("relatively", "比較的"),
    Word("drive", "車を運転する"),
    Word("territory", "領域"),
    Word("predict", "予期する"),
    Word("happen to A", "AをたまたまAをする"),
    Word("stance", "立場"),
    Word("coincidence", "偶然の一致"),
    Word("logical", "論理的な"),
    Word("ridiculous", "ばかげた"),
    Word("valid", "理にかなった"),
    Word("philosophy", "哲学"))

  val MENU_ID_MENU1 = (Menu.FIRST + 1);
  val MENU_ID_MENU2 = (Menu.FIRST + 2);

  var visible = true

  implicit def func2OnClickListener(func: (View) => Any) = {
    new View.OnClickListener() {
      override def onClick(v: View) = func(v)
    }
  }

  implicit def func2OnClickListenerForDialog(func: () => Any) = {
    new DialogInterface.OnClickListener() {
      override def onClick(dialog: DialogInterface, which: Int) = func()
    }
  }

  def $[T](id: Int): T = {
    this.findViewById(id).asInstanceOf[T]
  }

  def pickup(list: List[Word], n: Int) = (new Random).shuffle(list).toList.take(n)
  def pickup(list: List[Int]) = (new Random).shuffle(list).toList(0)

  lazy val label = $[TextView](R.id.wordLabel)

  lazy val answerCheckBtn = $[Button](R.id.answerCheckBtn)
  lazy val answer01 = $[RadioButton](R.id.answer01)
  lazy val answer02 = $[RadioButton](R.id.answer02)
  lazy val answer03 = $[RadioButton](R.id.answer03)

  var answer: Word = null

  def nextProblem() {
    val problems = pickup(words, 3)
    this.answer = problems(pickup(List(0, 1, 2)))
    label.setText(answer.japanese)
    answer01.setText(problems(0).english)
    answer02.setText(problems(1).english)
    answer03.setText(problems(2).english)

  }

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    nextProblem

    answerCheckBtn.setOnClickListener { (v: View) =>
      val radioGroup = $[RadioGroup](R.id.answerRadioGroup)
      val checkedAnswer = $[RadioButton](radioGroup.getCheckedRadioButtonId())

      val alertDialogBuilder = new AlertDialog.Builder(this);
      if (answer.english == checkedAnswer.getText) {
        alertDialogBuilder.setTitle("OK");
      } else {
        alertDialogBuilder.setTitle("NG");
      }

      alertDialogBuilder.setMessage("answer is " + this.answer.english);
      alertDialogBuilder.setPositiveButton("Next", { () =>
        nextProblem
      });

      val alertDialog = alertDialogBuilder.create();

      alertDialog.show();

    }

  }

  // オプションメニューが最初に呼び出される時に1度だけ呼び出されます
  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    // メニューアイテムを追加します
    menu.add(Menu.NONE, MENU_ID_MENU1, Menu.NONE, "Input");
    menu.add(Menu.NONE, MENU_ID_MENU2, Menu.NONE, "Menu2");

    super.onCreateOptionsMenu(menu);
  }

  // オプションメニューが表示される度に呼び出されます
  override def onPrepareOptionsMenu(menu: Menu) = {
    menu.findItem(MENU_ID_MENU2).setVisible(visible);
    visible = !visible;

    super.onPrepareOptionsMenu(menu);
  }

  override def onOptionsItemSelected(item: MenuItem) = {
    item.getItemId() match {
      case MENU_ID_MENU1 => {
        //次の画面に遷移させる
        val intent = new Intent();
        intent.setClassName(
          "cn.orz.pascal.nutan",
          "cn.orz.pascal.nutan.WordInputActivity");
        startActivity(intent);
        true
      }
      case MENU_ID_MENU2 => true
      case _ => super.onOptionsItemSelected(item);
    }

  }
}

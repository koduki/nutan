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

class MainActivity extends Activity {
  val words = List(
    Word("cat", "猫"),
    Word("dog", "犬"),
    Word("run", "走る"), 
    Word("look", "見る"), 
    Word("consider", "を熟考する"))

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

}

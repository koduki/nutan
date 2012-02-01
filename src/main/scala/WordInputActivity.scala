package cn.orz.pascal.nutan

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.view.View
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuItem

class WordInputActivity extends Activity {
  implicit def func2OnClickListener(func: (View) => Any) = {
    new View.OnClickListener() {
      override def onClick(v: View) = func(v)
    }
  }

  def $[T](id: Int): T = {
    this.findViewById(id).asInstanceOf[T]
  }

  lazy val okButton = $[Button](R.id.wordInput_OK)
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.wordinput)

    okButton.setOnClickListener { (v: View) => finish() }
  }

}
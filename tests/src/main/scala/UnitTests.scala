package cn.orz.pascal.nutan.tests

import junit.framework.Assert._
import _root_.android.test.AndroidTestCase

class UnitTests extends AndroidTestCase {
  def testPackageIsCorrect {
    assertEquals("cn.orz.pascal.nutan", getContext.getPackageName)
  }
}
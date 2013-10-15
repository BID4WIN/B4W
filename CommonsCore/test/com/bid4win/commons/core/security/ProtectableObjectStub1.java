package com.bid4win.commons.core.security;

import com.bid4win.commons.core.Bid4WinCoreTester;

public class ProtectableObjectStub1 extends ProtectableObject
{
  public void accessFromHierarchy1(ProtectableObjectStub1 object)
  {
    object.accessibleFromHierarchy1();
  }
  public void accessFromHierarchy2(ProtectableObjectStub1 object)
  {
    object.accessibleFromHierarchy2();
  }
  public void accessibleFromHierarchy1()
  {
    this.checkHierarchy(ProtectableObjectStub1.class);
  }
  public void accessibleFromHierarchy2()
  {
    this.checkHierarchy(ProtectableObjectStub1.class);
  }

  public void accessFromTest()
  {
    this.accessibleFromTest();
  }
  public void accessibleFromTest()
  {
    this.checkHierarchy(Bid4WinCoreTester.class);
  }
}

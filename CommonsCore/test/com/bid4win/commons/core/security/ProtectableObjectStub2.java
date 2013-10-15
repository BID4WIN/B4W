package com.bid4win.commons.core.security;

public class ProtectableObjectStub2 extends ProtectableObjectStub1
{
  @Override
  public void accessFromHierarchy2(ProtectableObjectStub1 object)
  {
    object.accessibleFromHierarchy2();
  }

  @Override
  public void accessibleFromHierarchy2()
  {
    this.checkHierarchy(ProtectableObjectStub2.class);
  }
}

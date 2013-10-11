package com.bid4win.commons.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityTester<ACCOUNT extends AccountAbstract<ACCOUNT>,
                                 GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinCoreTester
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("EntityGenerator")
  private GENERATOR generator = null;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinCoreTester#getGenerator()
   */
  @Override
  public final GENERATOR getGenerator()
  {
    return this.generator;
  }

  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkVersion(int expected, ENTITY result)
  {
    this.checkVersion("Wrong version", expected, result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkVersion(String message, int expected, ENTITY result)
  {
    assertEquals(message, expected, result.getVersion());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkIdenticalRelationNone(ENTITY expected, ENTITY result)
  {
    this.checkIdenticalRelationNone("Wrong result", expected, result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkIdenticalRelationNone(String message, ENTITY expected, ENTITY result)
  {
    assertTrue(message, expected.sameRelationNoneInternal(result, true));
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotIdenticalRelationNone(ENTITY expected, ENTITY result)
  {
    this.checkNotIdenticalRelationNone("Wrong result", expected, result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotIdenticalRelationNone(String message, ENTITY expected, ENTITY result)
  {
    assertFalse(message, expected.sameRelationNoneInternal(result, true));
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkIdentical(ENTITY expected, ENTITY result)
  {
    this.checkIdentical(expected, result, expected.getFullRelationNodeList());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkIdentical(String message, ENTITY expected, ENTITY result)
  {
    this.checkIdentical(message, expected, result, expected.getFullRelationNodeList());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkIdentical(ENTITY expected, ENTITY result, Bid4WinList<Bid4WinRelationNode> list)
  {
    this.checkIdentical("Wrong result", expected, result, list);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkIdentical(String message, ENTITY expected, ENTITY result, Bid4WinList<Bid4WinRelationNode> list)
  {
    assertTrue(message, expected.identical(result, list));
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotIdentical(ENTITY expected, ENTITY result)
  {
    this.checkNotIdentical(expected, result, expected.getFullRelationNodeList());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotIdentical(String message, ENTITY expected, ENTITY result)
  {
    this.checkNotIdentical(message, expected, result, expected.getFullRelationNodeList());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotIdentical(ENTITY expected, ENTITY result, Bid4WinList<Bid4WinRelationNode> list)
  {
   this.checkNotIdentical("Wrong result", expected, result, list);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotIdentical(String message, ENTITY expected, ENTITY result, Bid4WinList<Bid4WinRelationNode> list)
  {
    assertFalse(message, expected.identical(result, list));
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkSameRelationNone(ENTITY expected, ENTITY result)
  {
    this.checkSameRelationNone("Wrong result", expected, result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkSameRelationNone(String message, ENTITY expected, ENTITY result)
  {
    assertTrue(message, expected.sameRelationNoneInternal(result, false));
   }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotSameRelationNone(ENTITY expected, ENTITY result)
  {
    this.checkNotSameRelationNone("Wrong result", expected, result);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotSameRelationNone(String message, ENTITY expected, ENTITY result)
  {
    assertFalse(message, expected.sameRelationNoneInternal(result, false));
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkSame(ENTITY expected, ENTITY result)
  {
    this.checkSame(expected, result, expected.getFullRelationNodeList());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkSame(String message, ENTITY expected, ENTITY result)
  {
    this.checkSame(message, expected, result, expected.getFullRelationNodeList());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkSame(ENTITY expected, ENTITY result, Bid4WinList<Bid4WinRelationNode> list)
  {
    this.checkSame("Wrong result", expected, result, list);
  }
   /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkSame(String message, ENTITY expected, ENTITY result, Bid4WinList<Bid4WinRelationNode> list)
  {
    assertTrue(message, expected.same(result, list));
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotSame(ENTITY expected, ENTITY result)
  {
    this.checkNotSame(expected, result, expected.getFullRelationNodeList());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotSame(String message, ENTITY expected, ENTITY result)
  {
    this.checkNotSame(message, expected, result, expected.getFullRelationNodeList());
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
   public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
          void checkNotSame(ENTITY expected, ENTITY result, Bid4WinList<Bid4WinRelationNode> list)
  {
    this.checkNotSame("Wrong result", expected, result, list);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param message TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ?>>
         void checkNotSame(String message, ENTITY expected, ENTITY result, Bid4WinList<Bid4WinRelationNode> list)
  {
    assertFalse(message, expected.same(result, list));
  }
}

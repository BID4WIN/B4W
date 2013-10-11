package com.bid4win.persistence.dao.locale;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.persistence.entity.locale.Language;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class I18nRootDaoTest
       extends PropertyRootAbstractDaoTester<I18nRoot, I18n, Account, EntityGenerator>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("I18nRootDaoStub")
  private I18nRootDaoStub dao;
  /** Référence du DAO des propriétés */
  @Autowired
  @Qualifier("I18nDaoStub")
  private I18nDaoStub propertyDao;

  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @param index1 {@inheritDoc}
   * @param index2 {@inheritDoc}
   * @param index3 {@inheritDoc}
   * @param index4 {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester#addProperty(com.bid4win.commons.persistence.entity.property.PropertyRootAbstract, int, int, int, int)
   */
  @Override
  protected I18n addProperty(I18nRoot root, int index1, int index2, int index3, int index4)
            throws ModelArgumentException, UserException
  {
    Bid4WinList<Language> list = new Bid4WinList<Language>(Language.getLanguageSet());
    String language = list.get(index1 - 1).getCode();
    I18n i18n = root.getProperty(language);
    boolean newLanguage = false;
    if(i18n == null)
    {
      i18n = root.addProperty(language, "");
      newLanguage = true;
    }
    i18n = i18n.addProperty("b" + index2 + ".c" + index3 + ".d" + index4, "");
    if(newLanguage)
    {
      return i18n.getProperty();
    }
    return i18n;
  }
  /**
   * Getter du DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester#getDao()
   */
  @Override
  protected I18nRootDaoStub getDao()
  {
    return this.dao;
  }
  /**
   * Getter du DAO des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester#getPropertyDao()
   */
  @Override
  protected I18nDaoStub getPropertyDao()
  {
    return this.propertyDao;
  }
}

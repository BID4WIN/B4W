//package com.bid4win.commons.j2ee;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.core.Ordered;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <BASE> TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public abstract class BeanContainer<BASE> implements BeanPostProcessor, Ordered
//{
//  /** TODO A COMMENTER */
//  private Class<BASE> baseClass = null;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param baseClass TODO A COMMENTER
//   */
//  protected BeanContainer(Class<BASE> baseClass)
//  {
//    this.setBaseClass(baseClass);
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see org.springframework.core.Ordered#getOrder()
//   */
//  @Override
//  public int getOrder()
//  {
//    return Ordered.LOWEST_PRECEDENCE;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param bean {@inheritDoc}
//   * @param beanName {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
//   */
//  @Override
//  public Object postProcessBeforeInitialization(final Object bean, String beanName)
//  {
//    return bean;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param bean {@inheritDoc}
//   * @param beanName {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
//   */
//  @SuppressWarnings("unchecked")
//  @Override
//  public Object postProcessAfterInitialization(Object bean, String beanName)
//  {
//    if(this.getBaseClass().isAssignableFrom(bean.getClass()))
//    {
//      this.register((BASE)bean);
//    }
//    return bean;
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param bean TODO A COMMENTER
//   * @throws SecurityRuntimeException TODO A COMMENTER
//   */
//  private void register(BASE bean)
//  {
//    for(KEY key : this.getKeys(bean))
//    {
//      if(this.getBeanMap().containsKey(key))
//      {
//        throw new SecurityRuntimeException(this.getBaseClass().getSimpleName() + " " +
//                                           key + " already registered");
//      }
//      this.getBeanMap().put(key, bean);
//    }
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param bean TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  public abstract KEY[] getKeys(BASE bean);
//  /**
//   *
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  private Map<KEY, BASE> getBeanMap()
//  {
//    return this.beanMap;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param key TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  protected BASE getBean(KEY key)
//  {
//    return this.getBeanMap().get(key);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  protected Collection<BASE> getBeans()
//  {
//    return this.getBeanMap().values();
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return TODO A COMMENTER
//   */
//  public Class<BASE> getBaseClass()
//  {
//    return this.baseClass;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param baseClass TODO A COMMENTER
//   */
//  private void setBaseClass(Class<BASE> baseClass)
//  {
//    this.baseClass = baseClass;
//  }
//}

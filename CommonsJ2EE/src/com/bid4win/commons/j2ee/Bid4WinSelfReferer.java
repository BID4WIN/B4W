package com.bid4win.commons.j2ee;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("Bid4WinSelfReferer")
@Scope("singleton")
public class Bid4WinSelfReferer implements BeanPostProcessor, Ordered//<BEAN extends Bid4WinSelfReferencedBean<BEAN>>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see org.springframework.core.Ordered#getOrder()
   */
  @Override
  public int getOrder()
  {
    return Ordered.LOWEST_PRECEDENCE;
  }

  /**
   *
   * TODO A COMMENTER
   * @param bean {@inheritDoc}
   * @param beanName {@inheritDoc}
   * @return {@inheritDoc}
   * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
   */
  @Override
  public Object postProcessBeforeInitialization(final Object bean, String beanName)
  {
    return bean;
  }

  /**
   *
   * TODO A COMMENTER
   * @param bean {@inheritDoc}
   * @param beanName {@inheritDoc}
   * @return {@inheritDoc}
   * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
  {
    if(bean instanceof Bid4WinSelfReferencedBean)
    {
      ((Bid4WinSelfReferencedBean)bean).setSelf((Bid4WinSelfReferencedBean)bean);
    }
    return bean;
  }
}

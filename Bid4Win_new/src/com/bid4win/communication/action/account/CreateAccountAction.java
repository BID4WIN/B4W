package com.bid4win.communication.action.account;

import org.apache.struts2.convention.annotation.Action;

import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.communication.json.factory.JSONAccountFactory;
import com.bid4win.communication.json.factory.account.security.JSONCredentialFactory;
import com.bid4win.communication.json.factory.contact.JSONEmailFactory;
import com.bid4win.communication.json.model.JSONMessage.Type;
import com.bid4win.persistence.entity.account.Account;

/**
 * Classe d'action. Cr�e un compte utilisateur.
 *
 * @author Maxime Ollagnier
 */
public class CreateAccountAction extends AccountAction
{
  /**
   * M�thode ex�cut�e
   * @return {@inheritDoc}
   * @see com.opensymphony.xwork2.ActionSupport#execute()
   */
  @Action("CreateAccountAction")
  @Override
  public String execute()
  {
    try
    {
      // Essaye de r�cup�rer le certificat de connexion du compte utilisateur � cr�er
      Credential credential = JSONCredentialFactory.getInstance().get(this, Type.WARN);
      // Essaye de r�cup�rer l'email du compte utilisateur � cr�er
      Email email = JSONEmailFactory.getInstance().get(this, Type.WARN);
      // Essaye de cr�er le compte utilisateur
      Account account = this.getAccountService().createAccount(credential, email);
      this.putJSONObject(JSONAccountFactory.getInstance().getJSONAccount(account));
      this.setSuccess(true);
    }
    catch(Throwable th)
    {
      this.putErrorMessage(MessageRef.UNKNOWN_ERROR);
      th.printStackTrace();
    }
    return SUCCESS;
  }
}
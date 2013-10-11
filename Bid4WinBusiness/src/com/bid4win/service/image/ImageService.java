package com.bid4win.service.image;

import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.service.resource.ResourceRepositoryService_;
import com.bid4win.manager.image.ImageManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion des images et de leurs utilisations incluant la gestion des
 * transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("ImageService")
@Scope("singleton")
public class ImageService
       extends ResourceRepositoryService_<ImageStorage, ImageUsage, ImageType, SessionData, Account, ImageService>
{
  /** R�f�rence du manager de gestion des images et de leurs utilisations */
  @Autowired
  @Qualifier("ImageManager")
  private ImageManager manager = null;

  /**
   * Cette m�thode permet de charger une image en fonction de son identifiant
   * @param id Identifiant de l'image � charger
   * @param format Format de l'image � charger
   * @param outputStream Flux de sortie dans lequel charger l'image
   * @return La r�f�rence de l'image charg�e sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de l'image � fournir
   * @throws NotFoundEntityException Si aucune image n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public ImageStorage loadImage(long id, Format format, OutputStream outputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().loadImage(id, format, outputStream);
  }
  /**
   * Cette m�thode permet de charger une image en fonction de son identifiant
   * @param id Identifiant de l'image � charger
   * @param format Format de l'image � charger
   * @return L'image charg�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de l'image � fournir
   * @throws NotFoundEntityException Si aucune image n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public InputStream loadImage(long id, Format format)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().loadImage(id, format);
  }

  /**
   * L'oscar du r�le d'administration des images et de leurs utilisations est
   * attribu� aux gestionnaires m�tier
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.BIZ;
  }
  /**
   * Getter de la r�f�rence du manager de gestion des images
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceRepositoryService_#getManager()
   */
  @Override
  protected ImageManager getManager()
  {
    return this.manager;
  }
}

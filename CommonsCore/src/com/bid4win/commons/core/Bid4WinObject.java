package com.bid4win.commons.core;

import java.io.Serializable;

import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.collection.Bid4WinCollectionAbstract;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinObjectTypeContainer;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.RuntimeArgumentException;
import com.bid4win.commons.core.exception.RuntimeInstantiationException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.ObjectProtection;
import com.bid4win.commons.core.security.ObjectProtector;
import com.bid4win.commons.core.security.ProtectableObject;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe est la classe de base de tout objet du projet. Elle d�fini les
 * m�thodes et comportement n�cessaires ou utiles.<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinObject<CLASS extends Bid4WinObject<CLASS>>
       extends ProtectableObject
{
  /**
   * Constructeur de base avec initialisation de la potentielle protection
   */
  public Bid4WinObject()
  {
    super();
  }
  /**
   * Constructeur de base avec pr�cision de la potentielle protection
   * @param protection Potentielle protection de l'objet
   */
  public Bid4WinObject(ObjectProtection protection)
  {
    super(protection);
  }

  /**
   * Cette m�thode est red�fini afin d'utiliser la m�thode hashCodeInternal() et
   * de forcer � son impl�mentation
   * @return {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode()
  {
    return this.hashCodeInternal();
  }
  /**
   * Cette m�thode permet de retourner le code de hachage d�fini par d�faut par
   * la classe Object
   * @return Le code de hachage d�fini par d�faut par la classe Object
   */
  public final int hashCodeDefault()
  {
    return super.hashCode();
  }
  /**
   * Cette m�thode est � impl�menter afin de retourner un code de hachage qui v�rifie
   * le contrat d'�quivalence entre l'�galit� de deux objets et l'�galit� de leur
   * code de hachage
   * @return La valeur du code de hachage de l'objet
   */
  protected abstract int hashCodeInternal();

  /**
   * Cette m�thode est red�finie afin de d�finir l'�galit� de l'object courant
   * avec celui en param�tre. Cette fonction v�rifiera la nullit� de l'objet en
   * param�tre, ainsi que l'�galit� de leur classe avant d'appeler equalsInternal()
   * (tout cela au travers du comparateur par d�faut Bid4WinObjectComparator) pour
   * valider l'�galit� des deux objets
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object toBeCompared)
  {
    if(!Bid4WinObject.class.isInstance(toBeCompared))
    {
      return false;
    }
    // D�l�gue la comparaison de l'objet courant avec celui en param�tre au comparateur
    // par d�faut
    return Bid4WinObjectComparator.getInstanceObject().equals(this, (Bid4WinObject<?>)toBeCompared);
  }
  /**
   * Cette m�thode est � impl�menter afin de tester l'�galit� des contenus entre
   * l'objet courant et celui en param�tre sans avoir � v�rifier autre chose (
   * nullit�, classe) car ces tests doivent �tre effectu�s par les m�thodes appelantes
   * @param toBeCompared Objet avec lequel on doit tester l'�galit� des contenus
   * @return True si le contenu de l'objet en param�tre est consid�r� �gal � celui
   * de l'objet courant, false sinon
   */
  protected abstract boolean equalsInternal(CLASS toBeCompared);

  /**
   * Cette m�thode est red�finie afin d'effectuer le rendu de l'objet en cha�ne
   * de caract�res. Elle se basera sur le retour de la m�thode render()
   * @return {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public final String toString()
  {
    return this.render().toString();
  }
  /**
   * Cette m�thode est � impl�menter afin d'effectuer le rendu de l'objet en cha�ne de caract�res.
   * @return Le rendu de l'objet en cha�ne de caract�res
   */
  public abstract StringBuffer render();

  /**
   * Cette m�thode permet de d�finir la base des r�f�rences de messages li�es �
   * l'objet courant
   * @return La base des r�f�rences de messages li�es � l'objet courant
   */
  protected MessageRef getMessageRefBase()
  {
    return MessageRef.UNKNOWN;
  }
  /**
   * Cette m�thode permet de r�cup�rer une r�f�rence de message � partir de la base
   * d�finie pour l'objet courant et des sous-codes en arguments
   * @param partialCodes Sous-codes permettant de parcourir la base des r�f�rences
   * de messages li�es � l'objet courant jusqu'� la sous-r�f�rence choisie
   * @return La r�f�rence de message trouv�e � partir de la base d�finie pour l'
   * objet courant et des sous-codes en arguments
   */
  public final MessageRef getMessageRef(String ... partialCodes)
  {
    return this.getMessageRefBase().getSubMessageRef(partialCodes);
  }

  /**
   * Cette m�thode permet d'ajouter l'objet en argument � la collection donn�e de
   * l'objet courant
   * @param collection Collection de l'objet courant � laquelle ajouter l'objet
   * en argument
   * @param toBeAdded Objet � ajouter � la collection en argument
   * @param base Base des messages li�s � l'objet en argument
   * @throws ProtectionException Si la collection en argument est prot�g�e
   * @throws UserException Si l'objet en argument est nul ou d�j� r�f�renc� par
   * la collection
   */
  protected <TYPE> void add(Bid4WinCollectionAbstract<TYPE, ?, ?> collection,
                            TYPE toBeAdded, MessageRef base)
            throws ProtectionException, UserException
  {
    // V�rifie que l'objet � ajouter n'est pas nul
    UtilObject.checkNotNull("toBeAdded", toBeAdded,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // V�rifie que l'objet � ajouter n'est pas d�j� contenu dans la collection
    UtilBoolean.checkFalse("contained", collection.contains(toBeAdded),
                           base.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Ajoute l'objet en argument � la collection
    collection.add(toBeAdded);
  }
  /**
   * Cette m�thode permet de retirer l'objet en argument de la collection donn�e
   * de l'objet courant
   * @param collection Collection de l'objet courant de laquelle retirer l'objet
   * en argument
   * @param toBeRemoved Objet � retirer de la collection en argument
   * @param base Base des messages li�s � l'objet en argument
   * @throws ProtectionException Si la collection en argument est prot�g�e
   * @throws UserException Si l'objet en argument est nul ou pas r�f�renc� par la
   * collection
   */
  protected <TYPE> void remove(Bid4WinCollectionAbstract<TYPE, ?, ?> collection,
                               TYPE toBeRemoved, MessageRef base)
            throws ProtectionException, UserException
  {
    // V�rifie que l'objet � retirer n'est pas nul
    UtilObject.checkNotNull("toBeRemoved", toBeRemoved,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // V�rifie le retrait de l'objet de la collection et le retourne
    UtilBoolean.checkTrue("contained", collection.remove(toBeRemoved),
                          base.getMessageRef(MessageRef.SUFFIX_UNDEFINED_ERROR));
  }
  /**
   * Cette m�thode permet d'ajouter le coule en argument � la map donn�e de l'
   * objet courant
   * @param map Map de l'objet courant � laquelle ajouter l'objet en argument
   * @param key Cl� du couple � ajouter � la map en argument
   * @param value Valeur du couple � ajouter � la map en argument
   * @param base Base des messages li�s au couple en argument
   * @throws ProtectionException Si la map en argument est prot�g�e
   * @throws UserException Si le couple en argument est nul ou d�j� r�f�renc� par
   * la map
   */
  protected <KEY, VALUE> void add(Bid4WinMap<KEY, VALUE> map, KEY key,
                                  VALUE value, MessageRef base)
            throws ProtectionException, UserException
  {
    // V�rifie que le couple � ajouter n'est pas nul
    UtilObject.checkNotNull("key", key,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilObject.checkNotNull("value", value,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // V�rifie que la cl� � ajouter n'est pas d�j� contenue dans la map
    UtilBoolean.checkFalse("contained", map.containsKey(key),
                           base.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Ajoute le couple en argument � la map
    map.put(key, value);
  }
  /**
   * Cette m�thode permet de retirer le coule en argument de la map donn�e de l'
   * objet courant
   * @param map Map de l'objet courant de laquelle retirer l'objet en argument
   * @param key Cl� du couple � retirer de la map en argument
   * @param value Valeur du couple � retirer de la map en argument
   * @param base Base des messages li�s au couple en argument
   * @throws ProtectionException Si la map en argument est prot�g�e
   * @throws UserException Si le couple en argument est nul ou pas r�f�renc� par
   * la map
   */
  protected <KEY, VALUE> void remove(Bid4WinMap<KEY, VALUE> map, KEY key,
                                     VALUE value, MessageRef base)
            throws ProtectionException, UserException
  {
    // V�rifie que le couple � retirer n'est pas nul
    UtilObject.checkNotNull("key", key,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilObject.checkNotNull("value", value,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Retire le couple de la map
    VALUE removed = map.remove(key);
    // V�rifie le retrait de l'objet de la collection et le retourne
    try
    {
      UtilBoolean.checkTrue("contained", removed == value,
                            base.getMessageRef(MessageRef.SUFFIX_UNDEFINED_ERROR));
    }
    catch(UserException ex)
    {
      map.put(key, value);
      throw ex;
    }
  }

  //*************************************************************************//
  //******************* D�finition des types d'objet ************************//
  //*************************************************************************//

  /**
   * Cette classe d�fini un type d'objet comparable � une �num�ration ayant la
   * notion d'appartenance � un type sup�rieur. La d�claration du premier type
   * d'une classe fera de lui le type par d�faut<BR>
   * <BR>
   * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinObjectType<CLASS extends Bid4WinObjectType<CLASS>>
         extends Bid4WinObject<CLASS> implements Serializable
  {
    /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
     * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
     * avec les versions pr�c�dentes */
    private static final long serialVersionUID = 3178766485328107089L;
    /** Identifiant de protection des maps et conteneur */
    private static final String PROTECTION_ID = ObjectProtector.startProtection();
    /** Object de protection des maps et conteneur */
    private static final ObjectProtection PROTECTION = ObjectProtector.getProtection();
    static
    {
      ObjectProtector.stopProtection(Bid4WinObjectType.PROTECTION_ID);
    }
    /** Conteneur des types d'objets par d�faut */
    private static Bid4WinMap<Class<? extends Bid4WinObjectType<?>>, String>
        defaultCodeMap = new Bid4WinMap<Class<? extends Bid4WinObjectType<?>>, String>(PROTECTION);
    /** Conteneur des bases de message li�es au diff�rents types d'objet */
    private static Bid4WinMap<Class<? extends Bid4WinObjectType<?>>, MessageRef>
        messageRefBaseMap = new Bid4WinMap<Class<? extends Bid4WinObjectType<?>>, MessageRef>(PROTECTION);
    /** Conteneur de tous les types d'objet d�j� cr��s */
    private static Bid4WinObjectTypeContainer container = new Bid4WinObjectTypeContainer(PROTECTION);

    /**
     * Cette m�thode permet de r�cup�rer n'importe quel type d'objet d�j� cr��
     * @param <TYPE> D�finition du type de type d'objet � r�cup�rer
     * @param typeClass Classe du type d'objet � r�cup�rer
     * @param code Code du type d'objet � r�cup�rer
     * @return Le type d'objet dont la classe et le code sont pr�cis�s en argument
     * ou null si ce type d'objet n'a jamais exist�
     */
    private static <TYPE extends Bid4WinObjectType<TYPE>>
            TYPE getTypeInternal(Class<TYPE> typeClass, String code)
    {
      TYPE type = Bid4WinObjectType.container.getType(typeClass, code);
      if(type == null)
      {
        try
        {
          // Pour s'assurer de l'initialisation de la classe
          Class.forName(typeClass.getName());
        }
        catch(ClassNotFoundException ex)
        {
          ex.printStackTrace();
          return null;
        }
        type = Bid4WinObjectType.container.getType(typeClass, code);
      }
      return type;
    }
    /**
     * Cette m�thode permet de r�cup�rer n'importe quel type d'objet d�j� cr��
     * @param <TYPE> D�finition du type de type d'objet � r�cup�rer
     * @param typeClass Classe du type d'objet � r�cup�rer
     * @param code Code du type d'objet � r�cup�rer
     * @return Le type d'objet dont la classe et le code sont pr�cis�s en argument
     * @throws UserException Si aucun type d'objet ne correspond au code en argument
     */
    public static <TYPE extends Bid4WinObjectType<TYPE>>
           TYPE getType(Class<TYPE> typeClass, String code) throws UserException
    {
      return UtilObject.checkNotNull(typeClass.getSimpleName(),
                                     Bid4WinObjectType.getTypeInternal(typeClass, code),
                                     Bid4WinObjectType.getMessageRef(typeClass,
                                                                     MessageRef.SUFFIX_UNKNOWN_ERROR));
    }
    /**
     * Getter du type d�fini par d�faut pour la classe en argument
     * @param <TYPE> D�finition du type de type d'objet � r�cup�rer
     * @param typeClass Classe du type d'objet � r�cup�rer
     * @return Le type d�fini par d�faut pour la classe en argument
     */
    public final static <TYPE extends Bid4WinObjectType<TYPE>>
           TYPE getDefaultType(Class<TYPE> typeClass)
    {
      return Bid4WinObjectType.getTypeInternal(typeClass,
                                               Bid4WinObjectType.getDefaultCode(typeClass));
    }
    /**
     * Getter du code du type d�fini par d�faut pour la classe en argument
     * @param <TYPE> D�finition du type de type d'objet du code � r�cup�rer
     * @param typeClass Classe du type d'objet du code � r�cup�rer
     * @return Le code du type d�fini par d�faut pour la classe en argument
     */
    public final static <TYPE extends Bid4WinObjectType<TYPE>>
           String getDefaultCode(Class<TYPE> typeClass)
    {
      String code = Bid4WinObjectType.defaultCodeMap.get(typeClass);
      if(code == null)
      {
        try
        {
          // Pour s'assurer de l'initialisation de la classe
          Class.forName(typeClass.getName());
        }
        catch(ClassNotFoundException ex)
        {
          ex.printStackTrace();
          return null;
        }
        code = Bid4WinObjectType.defaultCodeMap.get(typeClass);
      }
      return code;
    }
    /**
     *
     * TODO A COMMENTER
     * @param <TYPE> TODO A COMMENTER
     * @param typeClass TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    public final static <TYPE extends Bid4WinObjectType<TYPE>>
           MessageRef getMessageRefBase(Class<TYPE> typeClass)
    {
      MessageRef base = Bid4WinObjectType.messageRefBaseMap.get(typeClass);
      if(base == null)
      {
        return MessageRef.UNKNOWN;
      }
      return base;
    }
    /**
     *
     * TODO A COMMENTER
     * @param <TYPE> TODO A COMMENTER
     * @param typeClass TODO A COMMENTER
     * @param partialCodes TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    public final static <TYPE extends Bid4WinObjectType<TYPE>>
           MessageRef getMessageRef(Class<TYPE> typeClass, String ... partialCodes)
    {
      return Bid4WinObjectType.getMessageRefBase(typeClass).getMessageRef(partialCodes);
    }
    /**
     * Cette m�thode permet de r�cup�rer la collection compl�te de n'importe quel
     * type d'objet d�j� cr��s. Attention, la collection sera prot�g�e contre toute
     * modification
     * @param <TYPE> D�finition du type des types d'objet � r�cup�rer
     * @param typeClass Classe des types d'objet � r�cup�rer
     * @return La collection compl�te de n'importe quel type d'objet d�j� cr��s
     */
    public final static <TYPE extends Bid4WinObjectType<TYPE>>
           Bid4WinCollection<TYPE> getTypes(Class<TYPE> typeClass)
    {
      Bid4WinCollection<TYPE> types = Bid4WinObjectType.container.getTypes(typeClass);
      if(types.isEmpty())
      {
        try
        {
          // Pour s'assurer de l'initialisation de la classe
          Class.forName(typeClass.getName());
        }
        catch(ClassNotFoundException ex)
        {
          ex.printStackTrace();
          return null;
        }
        types = Bid4WinObjectType.container.getTypes(typeClass);
      }
      return types;
    }
    /**
     * Cette m�thode permet de r�cup�rer la collection compl�te des parents primitifs
     * de n'importe quel type d'objet d�j� cr��s
     * @param <TYPE> D�finition du type des parents primitifs de types d'objet �
     * r�cup�rer
     * @param typeClass Classe des parents primitifs de types d'objet � r�cup�rer
     * @return La collection compl�te des parents primitifs de n'importe quel type
     * d'objet d�j� cr��s
     */
    public final static <TYPE extends Bid4WinObjectType<TYPE>>
           Bid4WinCollection<TYPE> getPrimiviteTypes(Class<TYPE> typeClass)
    {
      Bid4WinCollection<TYPE> set = Bid4WinObjectType.getTypes(typeClass);
      Bid4WinCollection<TYPE> result = new Bid4WinCollection<TYPE>(set.size());
      for(TYPE type : set)
      {
        if(!type.hasParent())
        {
          result.add(type);
        }
      }
      return result;
    }
    /**
     * Cette m�thode permet d'ajouter n'importe quel type d'objet. Le premier type
     * ajout� d'une classe sera consid�r� comme le type par d�faut et d�finira aussi
     * la base des messages qui leur seront associ�s
     * @param <TYPE> D�finition du type de type d'objet � ajouter
     * @param typeClass Classe du type d'objet � ajouter
     * @param type Type d'objet � ajouter
     * @throws ModelArgumentException Si un type d'objet de m�me classe et de m�me
     * code a d�j� �t� ajout�
     * @throws ProtectionException Si une des maps ou conteneur est prot�g�
     */
    private synchronized static <TYPE extends Bid4WinObjectType<TYPE>>
            void putType(Class<TYPE> typeClass, TYPE type)
            throws ModelArgumentException, ProtectionException
    {
      Bid4WinObjectType.container.put(typeClass, type);
      if(Bid4WinObjectType.getDefaultCode(typeClass) == null)
      {
        Bid4WinObjectType.defaultCodeMap.put(typeClass, type.getCode());
        Bid4WinObjectType.messageRefBaseMap.put(typeClass, type.getMessageRefBase());
      }
    }

    /** Code du type d'objet */
    private String code = UtilString.EMPTY;
    /** Libell� du type d'objet */
    private String wording = UtilString.EMPTY;
    /** Parent du type d'objet */
    private CLASS parent = null;
    /** Map de types d'objet ayant le type courant comme parent */
    private Bid4WinMap<String, CLASS> subtypeMap = new Bid4WinMap<String, CLASS>();

    /**
     * Constructeur d'un type d'objet. Tout type d'objet cr�� sera ajout� � au
     * conteneur de type d'objet sauf si un type d'objet de m�me classe et de m�me
     * code est d�j� pr�sent
     * @param code Code du type d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de m�me classe a
     * d�j� �t� cr�� avec le code en argument
     */
    protected Bid4WinObjectType(String code) throws RuntimeInstantiationException
    {
      this(code, null, null);
    }
    /**
     * Constructeur d'un type d'objet. Tout type d'objet cr�� sera ajout� � au
     * conteneur de type d'objet sauf si un type d'objet de m�me classe et de m�me
     * code est d�j� pr�sent
     * @param code Code du type d'objet
     * @param wording Libell� du type d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de m�me classe a
     * d�j� �t� cr�� avec le code en argument
     */
    protected Bid4WinObjectType(String code, String wording) throws RuntimeInstantiationException
    {
      this(code, wording, null);
    }
    /**
     * Constructeur d'un type d'objet. Tout type d'objet cr�� sera ajout� � au
     * conteneur de type d'objet sauf si un type d'objet de m�me classe et de m�me
     * code est d�j� pr�sent
     * @param code Code du type d'objet
     * @param parent Parent du type d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de m�me classe a
     * d�j� �t� cr�� avec le code en argument
     */
    protected Bid4WinObjectType(String code, CLASS parent) throws RuntimeInstantiationException
    {
      this(code, null, parent);
    }
    /**
     * Constructeur d'un type d'objet. Tout type d'objet cr�� sera ajout� au conteneur
     * de type d'objet sauf si un type d'objet de m�me classe et de m�me code est
     * d�j� pr�sent
     * @param code Code du type d'objet
     * @param wording Libell� du type d'objet
     * @param parent Parent du type d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de m�me classe a
     * d�j� �t� cr�� avec le code en argument
     */
    @SuppressWarnings("unchecked")
    protected Bid4WinObjectType(String code, String wording, CLASS parent)
              throws RuntimeInstantiationException
    {
      super(null);
      this.protect(PROTECTION);
      this.getSubtypeMap().protect(PROTECTION);
      this.setCode(code);
      this.setWording(wording);
      this.setParent(parent);
      // D�marre la protection
      ObjectProtector.startProtection(Bid4WinObjectType.PROTECTION_ID);
      try
      {
        // On est en mode prot�g�, on peut modifier le r�f�rencement
        Bid4WinObjectType.putType((Class<CLASS>)this.getClass(), (CLASS)this);
      }
      catch(ModelArgumentException ex)
      {
        throw new RuntimeInstantiationException(ex);
      }
      finally
      {
        ObjectProtector.stopProtection(Bid4WinObjectType.PROTECTION_ID);
      }
      if(parent != null)
      {
        parent.addSubtype((CLASS)this);
      }
    }

    /**
     * D�fini le code de hachage du type d'objet gr�ce au code de hachage de sa
     * classe et de son code
     * @return {@inheritDoc}
     * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
     */
    @Override
    protected int hashCodeInternal()
    {
      return this.getClass().hashCode() + this.getCode().hashCode();
    }
    /**
     * Permet de red�finir l'�galit� entre deux types d'objet par l'�galit� de
     * leur code
     * @param toBeCompared {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
     */
    @Override
    protected boolean equalsInternal(CLASS toBeCompared)
    {
      return this.getCode().equals(toBeCompared.getCode());
    }
    /**
     * Cette fonction permet d'effectuer le rendu du type d'objet en cha�ne de caract�res
     * @return {@inheritDoc}
     * @see com.bid4win.commons.core.Bid4WinObject#render()
     */
    @Override
    public StringBuffer render()
    {
      return new StringBuffer("CODE=" + this.getCode());
    }

    /**
     * Getter du code du type d'objet
     * @return Le code du type d'objet
     */
    public String getCode()
    {
      return this.code;
    }
    /**
     * Getter du libell� du type d'objet
     * @return Le libell� du type d'objet
     */
    public String getWording()
    {
      return this.wording;
    }
    /**
     * Cette m�thode permet de savoir si le type d'objet a un parent
     * @return True si le type d'objet a un parent, false sinon
     */
    public boolean hasParent()
    {
      return this.getParent() != null;
    }
    /**
     * Getter du parent du type d'objet
     * @return Le parent du type d'objet
     */
    public CLASS getParent()
    {
      return this.parent;
    }
    /**
     * Getter du parent primitif du type d'objet. Pour cela, remonte la cha�ne de
     * parent en parent jusqu'au premier. Si le type n'a pas de parent, la m�thode
     * retournera le type lui m�me
     * @return Le parent primitif du type d'objet
     */
    @SuppressWarnings("unchecked")
    public CLASS getPrimitive()
    {
      if(this.getParent() != null)
      {
        return this.getParent().getPrimitive();
      }
      return (CLASS)this;
    }
    /**
     * Cette m�thode parcours le graph de parents pour voir si le type courant
     * appartient au parent en param�tre en commen�ant par lui m�me
     * @param parent Type d'objet auquel on doit tester l'appartenance du type
     * d'objet courant
     * @return True si le type d'objet courant appartient au parent en argument,
     * false sinon
     */
    public boolean belongsTo(CLASS parent)
    {
      if(this.equals(parent))
      {
        return true;
      }
      return this.getParent() != null && this.getParent().belongsTo(parent);
    }
    /**
     * Cette m�thode permet de savoir si le type d'objets courant est r�f�renc�
     * par d'autres comme �tant leur parent
     * @return True si le type d'objet courante est r�f�renc� par d'autres comme
     * �tant leur parent
     */
    public boolean hasSubtype()
    {
      return this.getSubtypeMap().size() != 0;
    }
    /**
     * Cette m�thode permet de r�cup�rer r�cursivement le sous-type d�fini par
     * les codes en argument
     * @param codes Codes des diff�rents niveaux de sous-types
     * @return Le sous-type d�fini par les codes en argument
     */
    @SuppressWarnings("unchecked")
    public CLASS getSubtype(String ... codes)
    {
      CLASS subtype = (CLASS)this;
      for(String code : codes)
      {
        subtype = subtype.getSubtypeMap().get(code);
        if(subtype == null)
        {
          return null;
        }
      }
      return subtype;
    }
    /**
     * Getter des types d'objet ayant le type courant comme parent. Attention, la
     * collection sera prot�g�e contre toute modification
     * @return Les types d'objet ayant le type courant comme parent
     */
    public Bid4WinCollection<CLASS> getSubtypes()
    {
      return this.getSubtypeMap().values();
    }
    /**
     * Getter de tous les types d'objet ayant le type courant comme parent m�me
     * indirectement
     * @return Tous les types d'objet ayant le type courant comme parent m�me indirectement
     */
    public Bid4WinCollection<CLASS> getRecursiveSubtypes()
    {
      Bid4WinCollection<CLASS> result = new Bid4WinCollection<CLASS>(this.getSubtypes());
      for(CLASS type : this.getSubtypeMap().values())
      {
        result.addAll(type.getRecursiveSubtypes());
      }
      return result;
    }
    /**
     * Getter de la map de types d'objet ayant le type courant comme parent
     * @return La map de types d'objet ayant le type courant comme parent
     */
    private Bid4WinMap<String, CLASS> getSubtypeMap()
    {
      return this.subtypeMap;
    }

    /**
     * Setter interne du code du type d'objet
     * @param code Code du type d'objet
     */
    private void setCode(String code)
    {
      this.code = UtilString.trimNotNull(code);
    }
    /**
     * Setter interne du libell� du type d'objet
     * @param wording Libell� du type d'objet
     */
    private void setWording(String wording)
    {
      this.wording = UtilString.trimNotNull(wording);
    }
    /**
     * Setter interne du parent du type d'objet
     * @param parent Parent du type d'objet
     */
    private void setParent(CLASS parent) throws ProtectionException
    {
      this.parent = parent;
    }
    /**
     * Cette m�thode permet d'ajouter un type d'objet ayant le type courant comme
     * parent
     * @param subtype Le types d'objet ayant le type courant comme parent
     * @throws ProtectionException Si la m�thode est appel�e en dehors de la hi�rarchie
     * de classe
     */
    protected void addSubtype(CLASS subtype)
    {
      this.checkHierarchy(Bid4WinObjectType.class);
      ObjectProtector.startProtection(Bid4WinObjectType.PROTECTION_ID);
      try
      {
        this.getSubtypeMap().put(subtype.getCode(), subtype);
      }
      finally
      {
        ObjectProtector.stopProtection(Bid4WinObjectType.PROTECTION_ID);
      }
    }
  }

  //*************************************************************************//
  //************** D�finition des groupes de types d'objet ******************//
  //*************************************************************************//
  /**
   * Cette classe d�fini un groupe de types d'objet comparable � une �num�ration
   * ayant la notion d'appartenance � un groupe de types sup�rieur<BR>
   * <BR>
   * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinObjectTypeGroup<CLASS extends Bid4WinObjectTypeGroup<CLASS>>
         extends Bid4WinObjectType<CLASS>
  {
    /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
     * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
     * avec les versions pr�c�dentes */
    private static final long serialVersionUID = -1846752824585903794L;
    /** Map de parents du groupe de types d'objet */
    private Bid4WinMap<String, CLASS> parentMap = new Bid4WinMap<String, CLASS>();

    /**
     * Constructeur d'un groupe de types d'objet. Tout groupe de types d'objet
     * cr�� sera ajout� � au conteneur de types d'objet sauf si un groupe de types
     * d'objet de m�me classe et de m�me code est d�j� pr�sent
     * @param code Code du groupe de types d'objet
     * @param parents Parents du groupe de types d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de m�me classe a
     * d�j� �t� cr�� avec le code en argument
     */
    protected Bid4WinObjectTypeGroup(String code, CLASS ... parents) throws RuntimeInstantiationException
    {
      super(code);
      for(CLASS myClass : parents)
      {
        this.addParent(myClass);
      }
      // On prot�ge la map contre toute modification ult�rieure
      this.getParentMap().protect(this.getProtection());
    }

    /**
     * Red�fini cette m�thode pour permettre de savoir si le groupe de types d'
     * objet a au moins un parent
     * @return True si le groupe de types d'objet a au moins un parent, false sinon
     */
    @Override
    public boolean hasParent()
    {
      return this.getParentMap().size() != 0;
    }
    /**
     * Retournera forc�ment une RuntimeArgumentException
     * @return {@inheritDoc}
     * @throws RuntimeArgumentException Car la m�thode est d�pr�ci�e
     * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#getParent()
     * @deprecated Cette m�thode est d�pr�ci�e car un groupe de types d'objet peut
     * avoir plusieurs parents
     */
    @Override
    @Deprecated
    public CLASS getParent() throws RuntimeArgumentException
    {
      throw new RuntimeArgumentException("Cannot get unique parent from a type group");
    }
    /**
     * Getter des parents du groupe de types d'objet. Attention, la collection sera
     * prot�g�e contre toute modification
     * @return Le set de parents du groupe de types d'objet
     */
    public Bid4WinCollection<CLASS> getParentSet()
    {
      return this.getParentMap().values();
    }
    /**
     * Getter de la map de parents du groupe de types d'objet
     * @return La map de parents du groupe de types d'objet
     */
    private Bid4WinMap<String, CLASS> getParentMap()
    {
      return this.parentMap;
    }

    /**
     * Retournera forc�ment une RuntimeArgumentException
     * @return {@inheritDoc}
     * @throws RuntimeArgumentException Car la m�thode est d�pr�ci�e
     * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#getPrimitive()
     * @deprecated Cette m�thode est d�pr�ci�e car un groupe de types d'objet peut
     * avoir plusieurs parents primitifs
     */
    @Override
    @Deprecated
    public CLASS getPrimitive() throws RuntimeArgumentException
    {
      throw new RuntimeArgumentException("Cannot get unique primitive from a type group");
    }
    /**
     * Getter du set de parents primitifs du groupe de types d'objet. Pour cela,
     * remonte la cha�ne de parents en parents jusqu'aux premiers. Si le type n'
     * a pas de parent, la m�thode retournera le type lui m�me
     * @return Le set de parents primitifs du groupe de types d'objet
     */
    @SuppressWarnings("unchecked")
    public Bid4WinSet<CLASS> getPrimitiveSet()
    {
      if(this.getParentMap().size() == 0)
      {
        return new Bid4WinSet<CLASS>((CLASS)this);
      }
      Bid4WinSet<CLASS> primitiveSet = new Bid4WinSet<CLASS>();
      for(CLASS parent : this.getParentMap().values())
      {
        primitiveSet.addAll(parent.getPrimitiveSet());
      }
      return primitiveSet;
    }

    /**
     * Cette m�thode parcours le graph de parents pour voir si le type courant
     * appartient au parent en param�tre en commen�ant par lui m�me
     * @param parent {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#belongsTo(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
     */
    @Override
    public boolean belongsTo(CLASS parent)
    {
      if(this.equals(parent))
      {
        return true;
      }
      for(CLASS myClass : this.getParentMap().values())
      {
        if(myClass.belongsTo(parent))
        {
          return true;
        }
      }
      return false;
    }

    /**
     * Ajoute un parent au groupe de types d'objet
     * @param parent Parent � ajouter au groupe de types d'objet
     */
    @SuppressWarnings("unchecked")
    private void addParent(CLASS parent)
    {
      if(!this.getParentMap().containsKey(parent.getCode()))
      {
        this.getParentMap().put(parent.getCode(), parent);
        parent.addSubtype((CLASS)this);
      }
    }
  }

  //*************************************************************************//
  //*********  D�finition de la classe de comparaison donnant acc�s  ********//
  //********* aux m�thodes internes des objets sp�cifiques au projet ********//
  //*************************************************************************//
  /**
   * Cette classe donne acc�s aux m�thodes internes de comparaison des objets
   * sp�cifiques au projet<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class InternalObjectComparator
  {
    /**
     * Cette m�thode permet de tester l'�galit� des contenus entre les deux objets
     * en param�tre sans avoir � v�rifier autre chose (nullit�, classe) car ces
     * tests doivent �tre effectu�s par les m�thodes appelantes. Elle se basera
     * sur le retour de l'appel � la m�thode equalsInternal(CLASS) du premier objet
     * � comparer
     * @param <TYPE> D�finition du type des objets � comparer
     * @param object1 Premier objet � comparer
     * @param object2 Deuxi�me objet � comparer
     * @return True si le contenu des deux objets en param�tre est consid�r� �gal,
     * false sinon
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <TYPE extends Bid4WinObject> boolean equalsInternal(TYPE object1, TYPE object2)
    {
      return object1.equalsInternal(object2);
    }
  }
}

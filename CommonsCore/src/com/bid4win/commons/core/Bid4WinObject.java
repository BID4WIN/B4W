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
 * Cette classe est la classe de base de tout objet du projet. Elle défini les
 * méthodes et comportement nécessaires ou utiles.<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
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
   * Constructeur de base avec précision de la potentielle protection
   * @param protection Potentielle protection de l'objet
   */
  public Bid4WinObject(ObjectProtection protection)
  {
    super(protection);
  }

  /**
   * Cette méthode est redéfini afin d'utiliser la méthode hashCodeInternal() et
   * de forcer à son implémentation
   * @return {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode()
  {
    return this.hashCodeInternal();
  }
  /**
   * Cette méthode permet de retourner le code de hachage défini par défaut par
   * la classe Object
   * @return Le code de hachage défini par défaut par la classe Object
   */
  public final int hashCodeDefault()
  {
    return super.hashCode();
  }
  /**
   * Cette méthode est à implémenter afin de retourner un code de hachage qui vérifie
   * le contrat d'équivalence entre l'égalité de deux objets et l'égalité de leur
   * code de hachage
   * @return La valeur du code de hachage de l'objet
   */
  protected abstract int hashCodeInternal();

  /**
   * Cette méthode est redéfinie afin de définir l'égalité de l'object courant
   * avec celui en paramètre. Cette fonction vérifiera la nullité de l'objet en
   * paramètre, ainsi que l'égalité de leur classe avant d'appeler equalsInternal()
   * (tout cela au travers du comparateur par défaut Bid4WinObjectComparator) pour
   * valider l'égalité des deux objets
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
    // Délègue la comparaison de l'objet courant avec celui en paramètre au comparateur
    // par défaut
    return Bid4WinObjectComparator.getInstanceObject().equals(this, (Bid4WinObject<?>)toBeCompared);
  }
  /**
   * Cette méthode est à implémenter afin de tester l'égalité des contenus entre
   * l'objet courant et celui en paramètre sans avoir à vérifier autre chose (
   * nullité, classe) car ces tests doivent être effectués par les méthodes appelantes
   * @param toBeCompared Objet avec lequel on doit tester l'égalité des contenus
   * @return True si le contenu de l'objet en paramètre est considéré égal à celui
   * de l'objet courant, false sinon
   */
  protected abstract boolean equalsInternal(CLASS toBeCompared);

  /**
   * Cette méthode est redéfinie afin d'effectuer le rendu de l'objet en chaîne
   * de caractères. Elle se basera sur le retour de la méthode render()
   * @return {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public final String toString()
  {
    return this.render().toString();
  }
  /**
   * Cette méthode est à implémenter afin d'effectuer le rendu de l'objet en chaîne de caractères.
   * @return Le rendu de l'objet en chaîne de caractères
   */
  public abstract StringBuffer render();

  /**
   * Cette méthode permet de définir la base des références de messages liées à
   * l'objet courant
   * @return La base des références de messages liées à l'objet courant
   */
  protected MessageRef getMessageRefBase()
  {
    return MessageRef.UNKNOWN;
  }
  /**
   * Cette méthode permet de récupérer une référence de message à partir de la base
   * définie pour l'objet courant et des sous-codes en arguments
   * @param partialCodes Sous-codes permettant de parcourir la base des références
   * de messages liées à l'objet courant jusqu'à la sous-référence choisie
   * @return La référence de message trouvée à partir de la base définie pour l'
   * objet courant et des sous-codes en arguments
   */
  public final MessageRef getMessageRef(String ... partialCodes)
  {
    return this.getMessageRefBase().getSubMessageRef(partialCodes);
  }

  /**
   * Cette méthode permet d'ajouter l'objet en argument à la collection donnée de
   * l'objet courant
   * @param collection Collection de l'objet courant à laquelle ajouter l'objet
   * en argument
   * @param toBeAdded Objet à ajouter à la collection en argument
   * @param base Base des messages liés à l'objet en argument
   * @throws ProtectionException Si la collection en argument est protégée
   * @throws UserException Si l'objet en argument est nul ou déjà référencé par
   * la collection
   */
  protected <TYPE> void add(Bid4WinCollectionAbstract<TYPE, ?, ?> collection,
                            TYPE toBeAdded, MessageRef base)
            throws ProtectionException, UserException
  {
    // Vérifie que l'objet à ajouter n'est pas nul
    UtilObject.checkNotNull("toBeAdded", toBeAdded,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Vérifie que l'objet à ajouter n'est pas déjà contenu dans la collection
    UtilBoolean.checkFalse("contained", collection.contains(toBeAdded),
                           base.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Ajoute l'objet en argument à la collection
    collection.add(toBeAdded);
  }
  /**
   * Cette méthode permet de retirer l'objet en argument de la collection donnée
   * de l'objet courant
   * @param collection Collection de l'objet courant de laquelle retirer l'objet
   * en argument
   * @param toBeRemoved Objet à retirer de la collection en argument
   * @param base Base des messages liés à l'objet en argument
   * @throws ProtectionException Si la collection en argument est protégée
   * @throws UserException Si l'objet en argument est nul ou pas référencé par la
   * collection
   */
  protected <TYPE> void remove(Bid4WinCollectionAbstract<TYPE, ?, ?> collection,
                               TYPE toBeRemoved, MessageRef base)
            throws ProtectionException, UserException
  {
    // Vérifie que l'objet à retirer n'est pas nul
    UtilObject.checkNotNull("toBeRemoved", toBeRemoved,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Vérifie le retrait de l'objet de la collection et le retourne
    UtilBoolean.checkTrue("contained", collection.remove(toBeRemoved),
                          base.getMessageRef(MessageRef.SUFFIX_UNDEFINED_ERROR));
  }
  /**
   * Cette méthode permet d'ajouter le coule en argument à la map donnée de l'
   * objet courant
   * @param map Map de l'objet courant à laquelle ajouter l'objet en argument
   * @param key Clé du couple à ajouter à la map en argument
   * @param value Valeur du couple à ajouter à la map en argument
   * @param base Base des messages liés au couple en argument
   * @throws ProtectionException Si la map en argument est protégée
   * @throws UserException Si le couple en argument est nul ou déjà référencé par
   * la map
   */
  protected <KEY, VALUE> void add(Bid4WinMap<KEY, VALUE> map, KEY key,
                                  VALUE value, MessageRef base)
            throws ProtectionException, UserException
  {
    // Vérifie que le couple à ajouter n'est pas nul
    UtilObject.checkNotNull("key", key,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilObject.checkNotNull("value", value,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Vérifie que la clé à ajouter n'est pas déjà contenue dans la map
    UtilBoolean.checkFalse("contained", map.containsKey(key),
                           base.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Ajoute le couple en argument à la map
    map.put(key, value);
  }
  /**
   * Cette méthode permet de retirer le coule en argument de la map donnée de l'
   * objet courant
   * @param map Map de l'objet courant de laquelle retirer l'objet en argument
   * @param key Clé du couple à retirer de la map en argument
   * @param value Valeur du couple à retirer de la map en argument
   * @param base Base des messages liés au couple en argument
   * @throws ProtectionException Si la map en argument est protégée
   * @throws UserException Si le couple en argument est nul ou pas référencé par
   * la map
   */
  protected <KEY, VALUE> void remove(Bid4WinMap<KEY, VALUE> map, KEY key,
                                     VALUE value, MessageRef base)
            throws ProtectionException, UserException
  {
    // Vérifie que le couple à retirer n'est pas nul
    UtilObject.checkNotNull("key", key,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilObject.checkNotNull("value", value,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Retire le couple de la map
    VALUE removed = map.remove(key);
    // Vérifie le retrait de l'objet de la collection et le retourne
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
  //******************* Définition des types d'objet ************************//
  //*************************************************************************//

  /**
   * Cette classe défini un type d'objet comparable à une énumération ayant la
   * notion d'appartenance à un type supérieur. La déclaration du premier type
   * d'une classe fera de lui le type par défaut<BR>
   * <BR>
   * @param <CLASS> Doit définir la classe réellement instanciée<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class Bid4WinObjectType<CLASS extends Bid4WinObjectType<CLASS>>
         extends Bid4WinObject<CLASS> implements Serializable
  {
    /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
     * valeur doit être modifiée si une évolution de la classe la rend incompatible
     * avec les versions précédentes */
    private static final long serialVersionUID = 3178766485328107089L;
    /** Identifiant de protection des maps et conteneur */
    private static final String PROTECTION_ID = ObjectProtector.startProtection();
    /** Object de protection des maps et conteneur */
    private static final ObjectProtection PROTECTION = ObjectProtector.getProtection();
    static
    {
      ObjectProtector.stopProtection(Bid4WinObjectType.PROTECTION_ID);
    }
    /** Conteneur des types d'objets par défaut */
    private static Bid4WinMap<Class<? extends Bid4WinObjectType<?>>, String>
        defaultCodeMap = new Bid4WinMap<Class<? extends Bid4WinObjectType<?>>, String>(PROTECTION);
    /** Conteneur des bases de message liées au différents types d'objet */
    private static Bid4WinMap<Class<? extends Bid4WinObjectType<?>>, MessageRef>
        messageRefBaseMap = new Bid4WinMap<Class<? extends Bid4WinObjectType<?>>, MessageRef>(PROTECTION);
    /** Conteneur de tous les types d'objet déjà créés */
    private static Bid4WinObjectTypeContainer container = new Bid4WinObjectTypeContainer(PROTECTION);

    /**
     * Cette méthode permet de récupérer n'importe quel type d'objet déjà créé
     * @param <TYPE> Définition du type de type d'objet à récupérer
     * @param typeClass Classe du type d'objet à récupérer
     * @param code Code du type d'objet à récupérer
     * @return Le type d'objet dont la classe et le code sont précisés en argument
     * ou null si ce type d'objet n'a jamais existé
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
     * Cette méthode permet de récupérer n'importe quel type d'objet déjà créé
     * @param <TYPE> Définition du type de type d'objet à récupérer
     * @param typeClass Classe du type d'objet à récupérer
     * @param code Code du type d'objet à récupérer
     * @return Le type d'objet dont la classe et le code sont précisés en argument
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
     * Getter du type défini par défaut pour la classe en argument
     * @param <TYPE> Définition du type de type d'objet à récupérer
     * @param typeClass Classe du type d'objet à récupérer
     * @return Le type défini par défaut pour la classe en argument
     */
    public final static <TYPE extends Bid4WinObjectType<TYPE>>
           TYPE getDefaultType(Class<TYPE> typeClass)
    {
      return Bid4WinObjectType.getTypeInternal(typeClass,
                                               Bid4WinObjectType.getDefaultCode(typeClass));
    }
    /**
     * Getter du code du type défini par défaut pour la classe en argument
     * @param <TYPE> Définition du type de type d'objet du code à récupérer
     * @param typeClass Classe du type d'objet du code à récupérer
     * @return Le code du type défini par défaut pour la classe en argument
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
     * Cette méthode permet de récupérer la collection complète de n'importe quel
     * type d'objet déjà créés. Attention, la collection sera protégée contre toute
     * modification
     * @param <TYPE> Définition du type des types d'objet à récupérer
     * @param typeClass Classe des types d'objet à récupérer
     * @return La collection complète de n'importe quel type d'objet déjà créés
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
     * Cette méthode permet de récupérer la collection complète des parents primitifs
     * de n'importe quel type d'objet déjà créés
     * @param <TYPE> Définition du type des parents primitifs de types d'objet à
     * récupérer
     * @param typeClass Classe des parents primitifs de types d'objet à récupérer
     * @return La collection complète des parents primitifs de n'importe quel type
     * d'objet déjà créés
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
     * Cette méthode permet d'ajouter n'importe quel type d'objet. Le premier type
     * ajouté d'une classe sera considéré comme le type par défaut et définira aussi
     * la base des messages qui leur seront associés
     * @param <TYPE> Définition du type de type d'objet à ajouter
     * @param typeClass Classe du type d'objet à ajouter
     * @param type Type d'objet à ajouter
     * @throws ModelArgumentException Si un type d'objet de même classe et de même
     * code a déjà été ajouté
     * @throws ProtectionException Si une des maps ou conteneur est protégé
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
    /** Libellé du type d'objet */
    private String wording = UtilString.EMPTY;
    /** Parent du type d'objet */
    private CLASS parent = null;
    /** Map de types d'objet ayant le type courant comme parent */
    private Bid4WinMap<String, CLASS> subtypeMap = new Bid4WinMap<String, CLASS>();

    /**
     * Constructeur d'un type d'objet. Tout type d'objet créé sera ajouté à au
     * conteneur de type d'objet sauf si un type d'objet de même classe et de même
     * code est déjà présent
     * @param code Code du type d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de même classe a
     * déjà été créé avec le code en argument
     */
    protected Bid4WinObjectType(String code) throws RuntimeInstantiationException
    {
      this(code, null, null);
    }
    /**
     * Constructeur d'un type d'objet. Tout type d'objet créé sera ajouté à au
     * conteneur de type d'objet sauf si un type d'objet de même classe et de même
     * code est déjà présent
     * @param code Code du type d'objet
     * @param wording Libellé du type d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de même classe a
     * déjà été créé avec le code en argument
     */
    protected Bid4WinObjectType(String code, String wording) throws RuntimeInstantiationException
    {
      this(code, wording, null);
    }
    /**
     * Constructeur d'un type d'objet. Tout type d'objet créé sera ajouté à au
     * conteneur de type d'objet sauf si un type d'objet de même classe et de même
     * code est déjà présent
     * @param code Code du type d'objet
     * @param parent Parent du type d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de même classe a
     * déjà été créé avec le code en argument
     */
    protected Bid4WinObjectType(String code, CLASS parent) throws RuntimeInstantiationException
    {
      this(code, null, parent);
    }
    /**
     * Constructeur d'un type d'objet. Tout type d'objet créé sera ajouté au conteneur
     * de type d'objet sauf si un type d'objet de même classe et de même code est
     * déjà présent
     * @param code Code du type d'objet
     * @param wording Libellé du type d'objet
     * @param parent Parent du type d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de même classe a
     * déjà été créé avec le code en argument
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
      // Démarre la protection
      ObjectProtector.startProtection(Bid4WinObjectType.PROTECTION_ID);
      try
      {
        // On est en mode protégé, on peut modifier le référencement
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
     * Défini le code de hachage du type d'objet grâce au code de hachage de sa
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
     * Permet de redéfinir l'égalité entre deux types d'objet par l'égalité de
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
     * Cette fonction permet d'effectuer le rendu du type d'objet en chaîne de caractères
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
     * Getter du libellé du type d'objet
     * @return Le libellé du type d'objet
     */
    public String getWording()
    {
      return this.wording;
    }
    /**
     * Cette méthode permet de savoir si le type d'objet a un parent
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
     * Getter du parent primitif du type d'objet. Pour cela, remonte la chaîne de
     * parent en parent jusqu'au premier. Si le type n'a pas de parent, la méthode
     * retournera le type lui même
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
     * Cette méthode parcours le graph de parents pour voir si le type courant
     * appartient au parent en paramètre en commençant par lui même
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
     * Cette méthode permet de savoir si le type d'objets courant est référencé
     * par d'autres comme étant leur parent
     * @return True si le type d'objet courante est référencé par d'autres comme
     * étant leur parent
     */
    public boolean hasSubtype()
    {
      return this.getSubtypeMap().size() != 0;
    }
    /**
     * Cette méthode permet de récupérer récursivement le sous-type défini par
     * les codes en argument
     * @param codes Codes des différents niveaux de sous-types
     * @return Le sous-type défini par les codes en argument
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
     * collection sera protégée contre toute modification
     * @return Les types d'objet ayant le type courant comme parent
     */
    public Bid4WinCollection<CLASS> getSubtypes()
    {
      return this.getSubtypeMap().values();
    }
    /**
     * Getter de tous les types d'objet ayant le type courant comme parent même
     * indirectement
     * @return Tous les types d'objet ayant le type courant comme parent même indirectement
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
     * Setter interne du libellé du type d'objet
     * @param wording Libellé du type d'objet
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
     * Cette méthode permet d'ajouter un type d'objet ayant le type courant comme
     * parent
     * @param subtype Le types d'objet ayant le type courant comme parent
     * @throws ProtectionException Si la méthode est appelée en dehors de la hiérarchie
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
  //************** Définition des groupes de types d'objet ******************//
  //*************************************************************************//
  /**
   * Cette classe défini un groupe de types d'objet comparable à une énumération
   * ayant la notion d'appartenance à un groupe de types supérieur<BR>
   * <BR>
   * @param <CLASS> Doit définir la classe réellement instanciée<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class Bid4WinObjectTypeGroup<CLASS extends Bid4WinObjectTypeGroup<CLASS>>
         extends Bid4WinObjectType<CLASS>
  {
    /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
     * valeur doit être modifiée si une évolution de la classe la rend incompatible
     * avec les versions précédentes */
    private static final long serialVersionUID = -1846752824585903794L;
    /** Map de parents du groupe de types d'objet */
    private Bid4WinMap<String, CLASS> parentMap = new Bid4WinMap<String, CLASS>();

    /**
     * Constructeur d'un groupe de types d'objet. Tout groupe de types d'objet
     * créé sera ajouté à au conteneur de types d'objet sauf si un groupe de types
     * d'objet de même classe et de même code est déjà présent
     * @param code Code du groupe de types d'objet
     * @param parents Parents du groupe de types d'objet
     * @throws RuntimeInstantiationException Si un type d'objet de même classe a
     * déjà été créé avec le code en argument
     */
    protected Bid4WinObjectTypeGroup(String code, CLASS ... parents) throws RuntimeInstantiationException
    {
      super(code);
      for(CLASS myClass : parents)
      {
        this.addParent(myClass);
      }
      // On protège la map contre toute modification ultérieure
      this.getParentMap().protect(this.getProtection());
    }

    /**
     * Redéfini cette méthode pour permettre de savoir si le groupe de types d'
     * objet a au moins un parent
     * @return True si le groupe de types d'objet a au moins un parent, false sinon
     */
    @Override
    public boolean hasParent()
    {
      return this.getParentMap().size() != 0;
    }
    /**
     * Retournera forcément une RuntimeArgumentException
     * @return {@inheritDoc}
     * @throws RuntimeArgumentException Car la méthode est dépréciée
     * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#getParent()
     * @deprecated Cette méthode est dépréciée car un groupe de types d'objet peut
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
     * protégée contre toute modification
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
     * Retournera forcément une RuntimeArgumentException
     * @return {@inheritDoc}
     * @throws RuntimeArgumentException Car la méthode est dépréciée
     * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#getPrimitive()
     * @deprecated Cette méthode est dépréciée car un groupe de types d'objet peut
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
     * remonte la chaîne de parents en parents jusqu'aux premiers. Si le type n'
     * a pas de parent, la méthode retournera le type lui même
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
     * Cette méthode parcours le graph de parents pour voir si le type courant
     * appartient au parent en paramètre en commençant par lui même
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
     * @param parent Parent à ajouter au groupe de types d'objet
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
  //*********  Définition de la classe de comparaison donnant accès  ********//
  //********* aux méthodes internes des objets spécifiques au projet ********//
  //*************************************************************************//
  /**
   * Cette classe donne accès aux méthodes internes de comparaison des objets
   * spécifiques au projet<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class InternalObjectComparator
  {
    /**
     * Cette méthode permet de tester l'égalité des contenus entre les deux objets
     * en paramètre sans avoir à vérifier autre chose (nullité, classe) car ces
     * tests doivent être effectués par les méthodes appelantes. Elle se basera
     * sur le retour de l'appel à la méthode equalsInternal(CLASS) du premier objet
     * à comparer
     * @param <TYPE> Définition du type des objets à comparer
     * @param object1 Premier objet à comparer
     * @param object2 Deuxième objet à comparer
     * @return True si le contenu des deux objets en paramètre est considéré égal,
     * false sinon
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <TYPE extends Bid4WinObject> boolean equalsInternal(TYPE object1, TYPE object2)
    {
      return object1.equalsInternal(object2);
    }
  }
}

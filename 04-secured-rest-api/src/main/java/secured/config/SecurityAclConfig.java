package secured.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // prePostEnabled activate expression-based access control
public class SecurityAclConfig extends GlobalMethodSecurityConfiguration {

  private final DataSource dataSource;

  @Autowired
  public SecurityAclConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(permissionEvaluator());
    expressionHandler.setRoleHierarchy(roleHierarchy());
    return expressionHandler;
  }

  @Bean
  public AclPermissionEvaluator permissionEvaluator() {
    return new AclPermissionEvaluator(aclService());
  }

  @Bean
  public JdbcMutableAclService aclService() {
    return new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
  }

  @Bean
  public LookupStrategy lookupStrategy() {
    return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(),
        permissionGrantingStrategy());
  }

  @Bean
  public AclAuthorizationStrategy aclAuthorizationStrategy() {
    return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  @Bean
  public PermissionGrantingStrategy permissionGrantingStrategy() {
    return new DefaultPermissionGrantingStrategy(auditLogger());
  }

  @Bean
  public EhCacheBasedAclCache aclCache() {
    return new EhCacheBasedAclCache(
        aclEhCacheFactoryBean().getObject(),
        permissionGrantingStrategy(),
        aclAuthorizationStrategy()
    );
  }

  @Bean
  public EhCacheFactoryBean aclEhCacheFactoryBean() {
    EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
    ehCacheFactoryBean.setCacheManager(aclCacheManager().getObject());
    ehCacheFactoryBean.setCacheName("aclCache");
    return ehCacheFactoryBean;
  }

  @Bean
  public EhCacheManagerFactoryBean aclCacheManager() {
    return new EhCacheManagerFactoryBean();
  }

  @Bean
  public ConsoleAuditLogger auditLogger() {
    return new ConsoleAuditLogger();
  }

  // https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/authz-arch.html#authz-hierarchical-roles
  // https://docs.spring.io/spring-security/site/docs/4.2.x/apidocs/org/springframework/security/access/hierarchicalroles/RoleHierarchyImpl.html
  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
    return roleHierarchy;
  }

}

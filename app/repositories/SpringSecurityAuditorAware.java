package repositories;

import org.springframework.data.domain.AuditorAware;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 22:10
 */
public class SpringSecurityAuditorAware implements AuditorAware {
    @Override
    public Object getCurrentAuditor() {
        return null;
    }
}

package BIA.Business.Impact.Analysis.utils;

import BIA.Business.Impact.Analysis.Controller.LoginController;
import BIA.Business.Impact.Analysis.Exception.NotSufficientRightsException;
import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.Role;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class UserUtil {

    private static ObjectFactory<HttpSession> HTTP_SESSION_FACTORY;

    @Autowired
    public UserUtil(ObjectFactory<HttpSession> httpSessionFactory) {
        HTTP_SESSION_FACTORY = httpSessionFactory;
    }

    public static final String SESSION_ME = "ME";

    public static void checkUserRights(HttpServletRequest request, Role requiredRole) {
        HttpSession session = request.getSession();
        Employees me = (Employees) session.getAttribute(SESSION_ME);
        if (me.getRole() != requiredRole && me.getRole() != Role.ADMIN) {
            throw new NotSufficientRightsException();
        }
    }

    public static Employees getCurrentUser() {
        return (Employees) Optional
                .ofNullable(HTTP_SESSION_FACTORY
                        .getObject().getAttribute(SESSION_ME))
                .orElseThrow(() -> new RuntimeException("No user found in current session."));
    }
}

package BIA.Business.Impact.Analysis.Validator;

import BIA.Business.Impact.Analysis.Exception.NotSufficientRightsException;
import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Model.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RoleValidator {

    public static final String SESSION_ME = "ME";

    public static void checkUserRights(HttpServletRequest request, Role requiredRole) {
        HttpSession session = request.getSession();
        Employees me = (Employees) session.getAttribute(SESSION_ME);
        if (me.getRole() != requiredRole && me.getRole() != Role.ADMIN) {
            throw new NotSufficientRightsException();
        }
    }
}

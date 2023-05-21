package org.asguard.service.daoimpl;

import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.asguard.service.model.Employee;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeDaoImpl extends AbstractDAO<Employee> {

    public EmployeeDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public List<Employee> findAll() {
        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> rootEntry = cq.from(Employee.class);
        CriteriaQuery<Employee> all = cq.select(rootEntry);

        TypedQuery<Employee> allQuery = currentSession().createQuery(all);
        return allQuery.getResultList();
    }
    public Employee findById(Long id) {
        return super.get(id);
    }
    public Employee create(Employee employee) {
        return super.persist(employee);
    }
}

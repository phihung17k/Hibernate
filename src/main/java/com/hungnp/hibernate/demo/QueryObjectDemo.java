/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnp.hibernate.demo;

import com.hungnp.hibernate.entities.Department;
import com.hungnp.hibernate.entities.Employee;
import com.hungnp.hibernate.utils.HibernateUtils;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Win 10
 */
public class QueryObjectDemo {

    public static void getAllEmployee(Session session) {
        String hql = "select e "
                + "from " + Employee.class.getName() + " e "
                + " order by e.empName ";
        Query<Employee> query = session.createQuery(hql);
        List<Employee> employees = query.getResultList();
        for (Employee employee : employees) {
            System.out.println(employee.getEmpId() + "; " + employee.getEmpName());
        }
    }
    
    public static void getEmployeeInDepartment(Session session, String deptNo){
        String hql = "select e from "+Employee.class.getName()+" e "
                + "where e.department.deptNo = :deptNo ";
        Query<Employee> query = session.createQuery(hql);
        query.setParameter("deptNo", deptNo);
        List<Employee> ems= query.getResultList();
        for (Employee em : ems) {
            System.out.println(em.getEmpId() + "; " + em.getEmpName());
        }
    }
    
    public static void insertEmployee(Session session){
        Query query = null;
        String hql1 = "select d from Department d where d.deptId = :dept ";
        query = session.createQuery(hql1);
        query.setParameter("dept", 20);
        Department dep = (Department) query.getSingleResult();
        query = null;
        String hql2 = "select max(e.empId) from " +Employee.class.getName() +" e ";
        query = session.createQuery(hql2);
        long maxEm = (long) query.getSingleResult();
        Employee e = new Employee();
        e.setEmpId(maxEm+1);
        e.setEmpNo("E"+(maxEm+1));
        e.setEmpName("AAA");
        e.setJob("ABC");
        e.setHideDate(new Date());
        e.setSalary(1000.3f);
        e.setDepartment(dep);
        session.persist(e);
    }

    public static void main(String[] args) {
    	
        SessionFactory factory = HibernateUtils.getSessionFactory();

        Session session = factory.getCurrentSession();
        try {
            // T???t c??? c??c l???nh h??nh ?????ng v???i DB th??ng qua Hibernate
            // ?????u ph???i n???m trong 1 giao d???ch (Transaction)
            // B???t ?????u giao d???ch
            session.getTransaction().begin();
//            insertEmployee(session);
            getAllEmployee(session);
//            getEmployeeInDepartment(session, "D10");
            // Commit d??? li???u: x??a k???t n???i session cho 1 ????n v??? l??m vi???c
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Rollback trong tr?????ng h???p c?? l???i x???y ra.
            session.getTransaction().rollback();
        }
    }
}






































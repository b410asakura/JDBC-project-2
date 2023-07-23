package peaksoft;

import peaksoft.config.Config;
import peaksoft.model.Employee;
import peaksoft.model.Job;
import peaksoft.service.EmployeeService;
import peaksoft.service.JobService;
import peaksoft.service.impl.EmployeeServiceImpl;
import peaksoft.service.impl.JobServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        JobService jobService =  new JobServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

//        employeeService.createEmployeeTable();
//        employeeService.addEmployee(
//                new Employee("Tariel", "Saparalliev", 19, "vosan@gmail.com", 2)
//        );
//
//        Employee employee = new Employee("Salman", "Abduvahid", 16, "dremura@gmail.com", 1);
//        employeeService.updateEmployee(1l, employee);

//        System.out.println(employeeService.getAllEmployees());

//        System.out.println(employeeService.findByEmail("vosan@gmail.com"));

//        System.out.println(employeeService.getEmployeeByPosition("Mentor"));


//        employeeService.dropTable();
//        employeeService.cleanTable();

//        jobService.createJobTable();
//        jobService.addJob(new Job("Instructor", "Java", "Backend", 11));
//        System.out.println(jobService.getJobById(2L));
//        System.out.println(jobService.getJobById(1L));








        //---------------

//        System.out.println(jobService.getJobByEmployeeId(31L));

        System.out.println(jobService.sortByExperience("esCC"));


    }
}
